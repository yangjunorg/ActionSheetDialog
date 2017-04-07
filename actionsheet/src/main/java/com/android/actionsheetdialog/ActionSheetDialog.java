package com.android.actionsheetdialog;

import android.app.AlertDialog;
import android.content.Context;
import android.support.annotation.StringRes;
import android.support.annotation.StyleRes;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pengjinghui on 2017/4/7.
 */

public class ActionSheetDialog extends AlertDialog {
    protected ActionSheetDialog(Context context) {
        super(context);
    }

    protected ActionSheetDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    protected ActionSheetDialog(Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    public static class Builder extends AlertDialog.Builder {
        Context mContext;
        String mTitle;
        String mMessage;
        String mNegativeText;
        String mPositiveText;
        boolean mCancelable;
        List<ActionSheetItem> mActionSheetItems;
        OnClickListener mNegativeClickListener;
        OnClickListener mPositiveClickListener;

        public Builder(Context context) {
            super(context);
            mContext = context;
            mActionSheetItems = new ArrayList<>();
        }

        @Override
        public AlertDialog.Builder setCancelable(boolean cancelable) {
            mCancelable = cancelable;
            return this;
        }

        @Override
        public AlertDialog.Builder setMessage(CharSequence message) {
            mMessage = (String) message;
            return this;
        }

        @Override
        public AlertDialog.Builder setMessage(@StringRes int messageId) {
            mMessage = mContext.getString(messageId);
            return this;
        }

        @Override
        public AlertDialog.Builder setTitle(CharSequence title) {
            mTitle = (String) title;
            return this;
        }

        @Override
        public AlertDialog.Builder setTitle(@StringRes int titleId) {
            mTitle = mContext.getString(titleId);
            return this;
        }

        @Override
        public AlertDialog.Builder setNegativeButton(CharSequence text, OnClickListener listener) {
            mNegativeText = (String) text;
            mNegativeClickListener = listener;
            return this;
        }

        @Override
        public AlertDialog.Builder setPositiveButton(CharSequence text, OnClickListener listener) {
            mNegativeText = (String) text;
            mNegativeClickListener = listener;
            return this;
        }

        public AlertDialog.Builder addActionSheetItem(CharSequence text, int textColor, ActionSheetItemClickListener listener) {
            if (null != text && !TextUtils.isEmpty(text)) {
                ActionSheetItem actionSheetItem = new ActionSheetItem(textColor, (String) text, listener);
                mActionSheetItems.add(actionSheetItem);
            }
            return this;
        }

        @Override
        public AlertDialog create() {
            View rootView = LayoutInflater.from(mContext)
                    .inflate(R.layout.layout_action_sheet_dialog, null);
            ActionSheetDialog dialog = new ActionSheetDialog(mContext);
            dialog.setCancelable(mCancelable);
            if (mCancelable) {
                dialog.setCanceledOnTouchOutside(true);
            }
            return dialog;
        }

        static class ActionSheetItem {
            int textColor;
            String text;
            ActionSheetItemClickListener listener;
            public ActionSheetItem(int textColor, String text, ActionSheetItemClickListener listener) {
                this.textColor = textColor;
                this.text = text;
                this.listener = listener;
            }
        }

        public interface ActionSheetItemClickListener {
            void onClick(int position);
        }
    }
}
