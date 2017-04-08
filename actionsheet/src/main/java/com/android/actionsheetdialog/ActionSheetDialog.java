/*
 *
 * MIT License
 *
 * Copyright (c) 2017 JohnyPeng
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.android.actionsheetdialog;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.StringRes;
import android.support.annotation.StyleRes;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ScrollView;
import android.widget.TextView;

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

    public static class ActionSheetBuilder extends AlertDialog.Builder {
        Context mContext;
        String mTitle;
        String mMessage;
        String mNegativeText;
        String mPositiveText;
        boolean mCancelable;
        List<ActionSheetItem> mActionSheetItems;
        OnClickListener mNegativeClickListener;
        OnClickListener mPositiveClickListener;
        ActionSheetDialog mActionSheetDialog;

        public ActionSheetBuilder(Context context) {
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
            mCancelable = true;
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
                if (textColor <= 0)
                    textColor = Color.parseColor("#2196f3");
                ActionSheetItem actionSheetItem = new ActionSheetItem(textColor, (String) text, listener);
                mActionSheetItems.add(actionSheetItem);
            }
            return this;
        }

        @Override
        public AlertDialog create() {


            mActionSheetDialog = new ActionSheetDialog(mContext);
            Window window = mActionSheetDialog.getWindow();
            window.setGravity(Gravity.BOTTOM);
            WindowManager.LayoutParams params = window.getAttributes();
            params.y = 0;
            Drawable drawable = new ColorDrawable();
            drawable.setAlpha(0);
            window.setBackgroundDrawable(drawable);
            mActionSheetDialog.setCancelable(mCancelable);
            if (mCancelable) {
                mActionSheetDialog.setCanceledOnTouchOutside(true);
            }
            initViews();
            return mActionSheetDialog;
        }
        TextView mTitleView;
        TextView mMessageView;
        ScrollView mSheetItemContainer;
        TextView mCancelView;
        TextView mPositiveView;
        SheetItemOnClickListener mSheetItemOnClickListener = new SheetItemOnClickListener();

        private void initViews() {
            View rootView = LayoutInflater.from(mContext)
                    .inflate(R.layout.layout_action_sheet_dialog, null);
            mTitleView = (TextView) rootView.findViewById(R.id.tv_title);
            mMessageView = (TextView) rootView.findViewById(R.id.tv_message);
            mSheetItemContainer = (ScrollView) rootView.findViewById(R.id.scrollView_sheet_list);
            mCancelView = (TextView) rootView.findViewById(R.id.tv_cancel);
            handleTitle();
            handleMessage();
            handleContent();
            handleCancel();
            handlePositive();

            mActionSheetDialog.setView(rootView);
        }

        private void handlePositive() {
            if (null != mPositiveText) {
                mPositiveView = new TextView(mContext);
                mPositiveView.setGravity(Gravity.CENTER);
                ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                mPositiveView.setLayoutParams(params);
                mPositiveView.setPadding(0, dpToPx(5.0f), 0, dpToPx(5.0f));
                mPositiveView.setText(mPositiveText);
                mPositiveView.setTextSize(spToPx(5.0f));
                mPositiveView.setTextColor(Color.parseColor("#f44336"));
                mPositiveView.setTag(AlertDialog.BUTTON_POSITIVE);
                mPositiveView.setOnClickListener(mSheetItemOnClickListener);
            }

        }

        private void handleCancel() {
            if (null != mCancelView) {
                if (mCancelable) {
                    if (null != mNegativeText) {
                        mCancelView.setText(mNegativeText);
                    }
                    mCancelView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (null != mNegativeClickListener) {
                                mNegativeClickListener.onClick(mActionSheetDialog, AlertDialog.BUTTON_NEGATIVE);
                            }
                        }
                    });
                }
            }
        }

        private void handleContent() {
            if (null == mActionSheetItems || mActionSheetItems.isEmpty()) {
                mSheetItemContainer.setVisibility(View.GONE);
            } else {
                for (int i = 0; i < mActionSheetItems.size(); i++) {
                    ActionSheetItem item = mActionSheetItems.get(i);
                    TextView sheetItemView = new TextView(mContext);
                    sheetItemView.setGravity(Gravity.CENTER);
                    ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
                    sheetItemView.setLayoutParams(params);
                    sheetItemView.setPadding(0, dpToPx(5.0f), 0, dpToPx(5.0f));
                    sheetItemView.setText(item.text);
                    sheetItemView.setTextSize(spToPx(5.0f));
                    sheetItemView.setTextColor(item.textColor);
                    sheetItemView.setTag(i);
                    mSheetItemContainer.addView(sheetItemView);
                }

            }
        }

        private void handleMessage() {
            if (null != mMessageView) {
                if (null == mMessage) {
                    mMessageView.setVisibility(View.GONE);
                } else {
                    mMessageView.setText(mMessage);
                }
            }
        }

        private void handleTitle() {
            if (null != mTitleView) {
                if (null == mTitle) {
                    mTitleView.setVisibility(View.GONE);
                } else {
                    mTitleView.setText(mTitle);
                }
            }
        }
        public int dpToPx(float dp) {
            return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                    mContext.getResources().getDisplayMetrics());
        }

        public int spToPx(float sp) {
            return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp,
                    mContext.getResources().getDisplayMetrics());
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

        private class SheetItemOnClickListener implements View.OnClickListener {

            @Override
            public void onClick(View v) {
                int tag = (int) v.getTag();
                if (BUTTON_POSITIVE == tag) {
                    if (null != mPositiveClickListener) {
                        mPositiveClickListener.onClick(mActionSheetDialog, BUTTON_POSITIVE);
                    }
                    mActionSheetDialog.dismiss();
                } else {
                    mActionSheetItems.get(tag).listener.onClick(tag);
                }
            }
        }
    }
}
