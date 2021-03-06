package com.nile.kmooc.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;

import com.google.inject.Inject;
import com.nile.kmooc.base.BaseFragment;
import com.nile.kmooc.core.IEdxEnvironment;
import com.nile.kmooc.logger.Logger;
import com.nile.kmooc.view.dialog.IDialogCallback;
import com.nile.kmooc.view.dialog.NetworkCheckDialogFragment;

import com.nile.kmooc.module.analytics.ISegment;
import com.nile.kmooc.module.prefs.PrefManager;

public class SettingsFragment extends BaseFragment {

    public static final String TAG = SettingsFragment.class.getCanonicalName();

    private final Logger logger = new Logger(SettingsFragment.class);

    @Inject
    protected IEdxEnvironment environment;

    @Inject
    ExtensionRegistry extensionRegistry;

    private Switch wifiSwitch;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        environment.getSegment().trackScreenView(ISegment.Screens.SETTINGS);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View layout = inflater.inflate(com.nile.kmooc.R.layout.fragment_settings, container, false);
        wifiSwitch = (Switch) layout.findViewById(com.nile.kmooc.R.id.wifi_setting);
        updateWifiSwitch();
        final LinearLayout settingsLayout = (LinearLayout) layout.findViewById(com.nile.kmooc.R.id.settings_layout);
        for (SettingsExtension extension : extensionRegistry.forType(SettingsExtension.class)) {
            extension.onCreateSettingsView(settingsLayout);
        }
        return layout;
    }

    private void updateWifiSwitch() {
        final PrefManager wifiPrefManager = new PrefManager(
                getActivity().getBaseContext(), PrefManager.Pref.WIFI);

        wifiSwitch.setOnCheckedChangeListener(null);
        wifiSwitch.setChecked(wifiPrefManager.getBoolean(PrefManager.Key.DOWNLOAD_ONLY_ON_WIFI, false));
        wifiSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    wifiPrefManager.put(PrefManager.Key.DOWNLOAD_ONLY_ON_WIFI, false);
                    wifiPrefManager.put(PrefManager.Key.DOWNLOAD_OFF_WIFI_SHOW_DIALOG_FLAG, false);
                } else {
                    showWifiDialog();
                }
            }
        });
    }

    protected void showWifiDialog() {
        final NetworkCheckDialogFragment newFragment = NetworkCheckDialogFragment.newInstance(getString(com.nile.kmooc.R.string.wifi_dialog_title_help),
                getString(com.nile.kmooc.R.string.wifi_dialog_message_help),
                new IDialogCallback() {
                    @Override
                    public void onPositiveClicked() {
                        try {
                            PrefManager wifiPrefManager = new PrefManager
                                    (getActivity().getBaseContext(), PrefManager.Pref.WIFI);
                            wifiPrefManager.put(PrefManager.Key.DOWNLOAD_ONLY_ON_WIFI, true);
                            updateWifiSwitch();
                        } catch (Exception ex) {
                            logger.error(ex);
                        }
                    }

                    @Override
                    public void onNegativeClicked() {
                        try {
                            PrefManager wifiPrefManager = new PrefManager(
                                    getActivity().getBaseContext(), PrefManager.Pref.WIFI);
                            wifiPrefManager.put(PrefManager.Key.DOWNLOAD_ONLY_ON_WIFI, false);
                            wifiPrefManager.put(PrefManager.Key.DOWNLOAD_OFF_WIFI_SHOW_DIALOG_FLAG, false);

                            updateWifiSwitch();
                        } catch (Exception ex) {
                            logger.error(ex);
                        }
                    }
                });

        newFragment.setCancelable(false);
        newFragment.show(getActivity().getSupportFragmentManager(), "dialog");
    }
}
