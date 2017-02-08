package com.nile.kmooc.util;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class ViewAnimationUtil {
    // Prohibit instantiation
    private ViewAnimationUtil() {
        throw new UnsupportedOperationException();
    }

    public static void showMessageBar(View messageView) {
        showMessageBar(messageView, false);
    }

    public static void showMessageBar(View messageView, boolean isPersistent) {
        messageView.setVisibility(View.VISIBLE);
        Context context = messageView.getContext();
        Animation animate;
        if (isPersistent) {
            animate = AnimationUtils.loadAnimation(context, com.nile.kmooc.R.anim.show_msg_bar);
        } else {
            animate = AnimationUtils.loadAnimation(context, com.nile.kmooc.R.anim.show_msg_bar_temp);
            animate.setAnimationListener(new HideListener(messageView));
        }
        messageView.startAnimation(animate);
    }


    public static void hideMessageBar(View messageView) {
        if (messageView.getVisibility() == View.VISIBLE) {
            Animation animate = AnimationUtils.loadAnimation(
                    messageView.getContext(), com.nile.kmooc.R.anim.hide_msg_bar);
            animate.setAnimationListener(new HideListener(messageView));
            messageView.startAnimation(animate);
        }
    }


    //To clear animation set previously
    public static void stopAnimation(View view) {
        view.clearAnimation();
    }

    private static class HideListener implements Animation.AnimationListener {
        private final View view;

        HideListener(View view) {
            this.view = view;
        }

        @Override
        public void onAnimationStart(Animation animation) {}

        @Override
        public void onAnimationEnd(Animation animation) {
            view.setVisibility(View.GONE);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {}
    }
}
