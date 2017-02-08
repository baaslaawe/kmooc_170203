package com.nile.kmooc.view.login;

import android.support.annotation.NonNull;

import com.nile.kmooc.util.Config;
import com.nile.kmooc.util.NetworkUtil;
import com.nile.kmooc.view.ViewHoldingPresenter;

public class LoginPresenter extends ViewHoldingPresenter<LoginPresenter.LoginViewInterface> {

    final private Config config;
    final private NetworkUtil.ZeroRatedNetworkInfo networkInfo;

    public LoginPresenter(Config config, NetworkUtil.ZeroRatedNetworkInfo networkInfo) {
        this.config = config;
        this.networkInfo = networkInfo;
    }

    @Override
    public void attachView(@NonNull LoginViewInterface view) {
        super.attachView(view);

        if (networkInfo.isOnZeroRatedNetwork()) {
            view.setSocialLoginButtons(false, false);
        } else {
            view.setSocialLoginButtons(config.getGoogleConfig().isEnabled(), config.getFacebookConfig().isEnabled());
        }
    }

    public interface LoginViewInterface {

        void setSocialLoginButtons(boolean googleEnabled, boolean facebookEnabled);

    }
}
