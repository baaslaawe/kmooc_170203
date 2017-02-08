package com.nile.kmooc.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;

import com.google.inject.Inject;
import com.nile.kmooc.BuildConfig;
import com.nile.kmooc.authentication.AuthResponse;
import com.nile.kmooc.exception.LoginErrorMessage;
import com.nile.kmooc.exception.LoginException;
import com.nile.kmooc.model.api.ProfileModel;
import com.nile.kmooc.module.prefs.LoginPrefs;

import com.nile.kmooc.authentication.LoginTask;
import com.nile.kmooc.databinding.ActivityLoginBinding;
import com.nile.kmooc.exception.AuthException;
import com.nile.kmooc.module.analytics.ISegment;
import com.nile.kmooc.social.SocialFactory;
import com.nile.kmooc.social.SocialLoginDelegate;
import com.nile.kmooc.task.Task;
import com.nile.kmooc.util.Config;
import com.nile.kmooc.util.IntentFactory;
import com.nile.kmooc.util.NetworkUtil;
import com.nile.kmooc.util.ResourceUtil;
import com.nile.kmooc.view.dialog.ResetPasswordActivity;
import com.nile.kmooc.view.dialog.SimpleAlertDialog;
import com.nile.kmooc.view.login.LoginPresenter;

public class LoginActivity extends PresenterActivity<LoginPresenter, LoginPresenter.LoginViewInterface> implements SocialLoginDelegate.MobileLoginCallback {

    private SocialLoginDelegate socialLoginDelegate;
    private ActivityLoginBinding activityLoginBinding;

    @Inject
    LoginPrefs loginPrefs;

    @NonNull
    public static Intent newIntent() {
        return IntentFactory.newIntentForComponent(LoginActivity.class);
    }

    @NonNull
    @Override
    protected LoginPresenter createPresenter(@Nullable Bundle savedInstanceState) {
        return new LoginPresenter(
                environment.getConfig(),
                new NetworkUtil.ZeroRatedNetworkInfo(getApplicationContext(), environment.getConfig()));
    }

    @NonNull
    @Override
    protected LoginPresenter.LoginViewInterface createView(@Nullable Bundle savedInstanceState) {
        activityLoginBinding = DataBindingUtil.setContentView(this, com.nile.kmooc.R.layout.activity_login);

        hideSoftKeypad();
        socialLoginDelegate = new SocialLoginDelegate(this, savedInstanceState, this, environment.getConfig(), environment.getLoginPrefs());

        activityLoginBinding.socialAuth.facebookButton.getRoot().setOnClickListener(
                socialLoginDelegate.createSocialButtonClickHandler(
                        SocialFactory.SOCIAL_SOURCE_TYPE.TYPE_FACEBOOK));
        activityLoginBinding.socialAuth.googleButton.getRoot().setOnClickListener(
                socialLoginDelegate.createSocialButtonClickHandler(
                        SocialFactory.SOCIAL_SOURCE_TYPE.TYPE_GOOGLE));

        activityLoginBinding.loginButtonLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check for Validation˜
                callServerForLogin();
            }
        });

        activityLoginBinding.forgotPasswordTv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // Calling help dialog
                if (NetworkUtil.isConnected(LoginActivity.this)) {
                    showResetPasswordDialog();
                } else {
                    showNoNetworkDialog();
                }
            }
        });

        String platformName = environment.getConfig().getPlatformName();
        CharSequence licenseText = ResourceUtil.getFormattedString(getResources(), com.nile.kmooc.R.string.licensing_agreement, "platform_name", platformName);
        activityLoginBinding.endUserAgreementTv.setText(licenseText);
        activityLoginBinding.endUserAgreementTv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showEulaDialog();
            }
        });
        activityLoginBinding.endUserAgreementTv2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showEulaDialog2();
            }
        });

        environment.getSegment().trackScreenView(ISegment.Screens.LOGIN);

        // enable login buttons at launch
        tryToSetUIInteraction(true);

        Config config = environment.getConfig();
        setTitle(getString(com.nile.kmooc.R.string.login_title));

        String envDisplayName = config.getEnvironmentDisplayName();
        if (envDisplayName != null && envDisplayName.length() > 0) {
            activityLoginBinding.versionEnvTv.setVisibility(View.VISIBLE);
            String versionName = BuildConfig.VERSION_NAME;
            String text = String.format("%s %s %s",
                    getString(com.nile.kmooc.R.string.label_version), versionName, envDisplayName);
            activityLoginBinding.versionEnvTv.setText(text);
        }

        return new LoginPresenter.LoginViewInterface() {
            @Override
            public void setSocialLoginButtons(boolean googleEnabled, boolean facebookEnabled) {
                if (!facebookEnabled && !googleEnabled) {
                    activityLoginBinding.panelLoginSocial.setVisibility(View.GONE);
                } else if (!facebookEnabled) {
                    activityLoginBinding.socialAuth.facebookButton.getRoot().setVisibility(View.GONE);
                } else if (!googleEnabled) {
                    activityLoginBinding.socialAuth.googleButton.getRoot().setVisibility(View.GONE);
                }
            }
        };
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        socialLoginDelegate.onActivityDestroyed();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("username", activityLoginBinding.emailEt.getText().toString().trim());

        socialLoginDelegate.onActivitySaveInstanceState(outState);

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (activityLoginBinding.emailEt.getText().toString().length() == 0) {
            displayLastEmailId();
        }

        socialLoginDelegate.onActivityStarted();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            activityLoginBinding.emailEt.setText(savedInstanceState.getString("username"));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        tryToSetUIInteraction(true);
        socialLoginDelegate.onActivityResult(requestCode, resultCode, data);
    }

    private void displayLastEmailId() {
        activityLoginBinding.emailEt.setText(loginPrefs.getLastAuthenticatedEmail());
    }

    public void callServerForLogin() {
        if (!NetworkUtil.isConnected(this)) {
            showErrorDialog(getString(com.nile.kmooc.R.string.no_connectivity),
                    getString(com.nile.kmooc.R.string.network_not_connected));
            return;
        }

        final String emailStr = activityLoginBinding.emailEt.getText().toString().trim();
        final String passwordStr = activityLoginBinding.passwordEt.getText().toString().trim();

        if (activityLoginBinding.emailEt != null && emailStr.length() == 0) {
            showErrorDialog(getString(com.nile.kmooc.R.string.login_error),
                    getString(com.nile.kmooc.R.string.error_enter_email));
            activityLoginBinding.emailEt.requestFocus();
        } else if (activityLoginBinding.passwordEt != null && passwordStr.length() == 0) {
            showErrorDialog(getString(com.nile.kmooc.R.string.login_error),
                    getString(com.nile.kmooc.R.string.error_enter_password));
            activityLoginBinding.passwordEt.requestFocus();
        } else {
            activityLoginBinding.emailEt.setEnabled(false);
            activityLoginBinding.passwordEt.setEnabled(false);
            activityLoginBinding.forgotPasswordTv.setEnabled(false);
            activityLoginBinding.endUserAgreementTv.setEnabled(false);

            LoginTask logintask = new LoginTask(this, activityLoginBinding.emailEt.getText().toString().trim(),
                    activityLoginBinding.passwordEt.getText().toString()) {
                @Override
                public void onSuccess(@NonNull AuthResponse result) {
                    onUserLoginSuccess(result.profile);
                }

                @Override
                public void onException(Exception ex) {
                    if (ex instanceof AuthException) {
                        onUserLoginFailure(new LoginException(new LoginErrorMessage(
                                getString(com.nile.kmooc.R.string.login_error),
                                getString(com.nile.kmooc.R.string.login_failed))), null, null);
                    } else {
                        super.onException(ex);
                    }
                    tryToSetUIInteraction(true);
                }
            };
            tryToSetUIInteraction(false);
            logintask.setProgressDialog(activityLoginBinding.progress.progressIndicator);
            logintask.execute();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        socialLoginDelegate.onActivityStopped();
    }

    public String getEmail() {
        return activityLoginBinding.emailEt.getText().toString().trim();
    }

    private static final int RESET_PASSWORD_REQUEST_CODE = 0;

    private void showResetPasswordDialog() {
        startActivityForResult(ResetPasswordActivity.newIntent(getEmail()), RESET_PASSWORD_REQUEST_CODE);
    }

    public void showEulaDialog() {
        environment.getRouter().showWebViewDialog(this, getString(com.nile.kmooc.R.string.eula_file_link), getString(com.nile.kmooc.R.string.end_user_title));
    }
    public void showEulaDialog2() {
        environment.getRouter().showWebViewDialog(this, getString(com.nile.kmooc.R.string.eula_file_link2), "개인정보처리방침");
    }

    public void showNoNetworkDialog() {
        Bundle args = new Bundle();
        args.putString(SimpleAlertDialog.EXTRA_TITLE, getString(com.nile.kmooc.R.string.reset_no_network_title));
        args.putString(SimpleAlertDialog.EXTRA_MESSAGE, getString(com.nile.kmooc.R.string.reset_no_network_message));

        SimpleAlertDialog noNetworkFragment = SimpleAlertDialog.newInstance(args);
        noNetworkFragment.setStyle(DialogFragment.STYLE_NO_TITLE, 0);
        noNetworkFragment.show(getSupportFragmentManager(), "dialog");
    }

    // make sure that on the login activity, all errors show up as a dialog as opposed to a flying snackbar
    @Override
    public void showErrorDialog(String header, String message) {
        super.showErrorDialog(header, message);
    }

    @Override
    public boolean createOptionsMenu(Menu menu) {
        if (!environment.getConfig().isRegistrationEnabled()) {
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setHomeButtonEnabled(false);
                actionBar.setDisplayHomeAsUpEnabled(false);
                actionBar.setDisplayShowHomeEnabled(false);
            }
        }
        return false;
    }

    /**
     * Starts fetching profile of the user after login by Facebook or Google.
     *
     * @param accessToken
     * @param backend
     */
    public void onSocialLoginSuccess(String accessToken, String backend, Task task) {
        tryToSetUIInteraction(false);
        task.setProgressDialog(activityLoginBinding.progress.progressIndicator);
    }

    public void onUserLoginSuccess(ProfileModel profile) {
        setResult(RESULT_OK);
        finish();
        if (!environment.getConfig().isRegistrationEnabled()) {
            environment.getRouter().showMyCourses(this);
        }
    }

    public void onUserLoginFailure(Exception ex, String accessToken, String backend) {
        tryToSetUIInteraction(true);


        // handle if this is a LoginException
        if (ex != null && ex instanceof LoginException) {
            LoginErrorMessage error = (((LoginException) ex).getLoginErrorMessage());

            showErrorDialog(
                    error.getMessageLine1(),
                    (error.getMessageLine2() != null) ?
                            error.getMessageLine2() : getString(com.nile.kmooc.R.string.login_failed));
        } else {
            showErrorDialog(getString(com.nile.kmooc.R.string.login_error), getString(com.nile.kmooc.R.string.error_unknown));
            logger.error(ex);
        }
    }

    @Override
    public boolean tryToSetUIInteraction(boolean enable) {
        if (enable) {
            unblockTouch();
            activityLoginBinding.loginButtonLayout.setEnabled(enable);
            activityLoginBinding.loginBtnTv.setText(getString(com.nile.kmooc.R.string.login));
        } else {
            blockTouch();
            activityLoginBinding.loginButtonLayout.setEnabled(enable);
            activityLoginBinding.loginBtnTv.setText(getString(com.nile.kmooc.R.string.signing_in));
        }


        activityLoginBinding.socialAuth.facebookButton.getRoot().setClickable(enable);
        activityLoginBinding.socialAuth.googleButton.getRoot().setClickable(enable);

        activityLoginBinding.emailEt.setEnabled(enable);
        activityLoginBinding.passwordEt.setEnabled(enable);

        activityLoginBinding.forgotPasswordTv.setEnabled(enable);
        activityLoginBinding.endUserAgreementTv.setEnabled(enable);

        return true;
    }
}
