package com.nile.kmooc.util.images;

import android.content.Context;
import android.support.annotation.NonNull;

import com.nile.kmooc.http.HttpResponseStatusException;
import com.nile.kmooc.http.HttpStatus;
import com.nile.kmooc.logger.Logger;
import com.nile.kmooc.util.NetworkUtil;

import java.io.IOException;

public enum ErrorUtils {
    ;

    protected static final Logger logger = new Logger(ErrorUtils.class.getName());

    @NonNull
    public static String getErrorMessage(@NonNull Throwable ex, @NonNull Context context) {
        String errorMessage = null;
        if (ex instanceof IOException) {
            if (NetworkUtil.isConnected(context)) {
                errorMessage = context.getString(com.nile.kmooc.R.string.network_connected_error);
            } else {
                errorMessage = context.getString(com.nile.kmooc.R.string.reset_no_network_message);
            }
        } else if (ex instanceof HttpResponseStatusException) {
            final int status = ((HttpResponseStatusException) ex).getStatusCode();
            switch (status) {
                case HttpStatus.SERVICE_UNAVAILABLE:
                    errorMessage = context.getString(com.nile.kmooc.R.string.network_service_unavailable);
                    break;
                case HttpStatus.NOT_FOUND:
                case HttpStatus.INTERNAL_SERVER_ERROR:
                    errorMessage = context.getString(com.nile.kmooc.R.string.action_not_completed);
                    break;
            }
        }
        if (null == errorMessage) {
            logger.error(ex, true /* Submit crash report since this is an unknown type of error */);
            errorMessage = context.getString(com.nile.kmooc.R.string.error_unknown);
        }
        return errorMessage;
    }
}
