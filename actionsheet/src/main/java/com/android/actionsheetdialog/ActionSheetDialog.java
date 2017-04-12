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
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.ArrayRes;
import android.support.annotation.StringRes;
import android.support.annotation.StyleRes;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pengjinghui on 2017/4/7.
 */

public class ActionSheetDialog extends AlertDialog {
    private static final String TAG = "ActionSheetDialog";
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
        private Context mContext;
        private String mTitle;
        private String mMessage;
        private String mNegativeText;
        private String mPositiveText;
        private boolean mCancelable;
        private List<ActionSheetItem> mActionSheetItems;
        private OnClickListener mNegativeClickListener;
        private OnClickListener mPositiveClickListener;
        private ActionSheetDialog mActionSheetDialog;

        //Attributes
        private int mTitleTextColor;
        private int mTitleTextSize;
        private int mTitleHeight;
        private int mMessageTextColor;
        private int mMessageTextSize;
        private int mMessageHeight;
        private int mItemTextColor;
        private int mItemTextSize;
        private int mItemHeight;
        private int mPositiveTextColor;
        private int mPositiveTextSize;
        private int mPositiveHeight;
        private int mCancelTextColor;
        private int mCancelTextSize;
        private int mCancelHeight;
        private int mCancelTopMagin;
        private Drawable mCancelBackground;
        private int mLayoutMargins;
        private int mSheetMargins;
        private Drawable mContentBackground;
        private static final int DEFAULT_VALUE = -1;



        public ActionSheetBuilder(Context context) {
            super(context);
            mContext = context;
            mActionSheetItems = new ArrayList<>();
            TypedArray defaultTypedArray = context.obtainStyledAttributes(R.style.ActionSheetDialogBase, R.styleable.ActionSheetDialog);
            if (null != defaultTypedArray) {
                initDefaultAttributes(defaultTypedArray);
                defaultTypedArray.recycle();
            }
        }

        public ActionSheetBuilder(Context context, int themeResId) {
            this(context);
            TypedArray typedArray = context.obtainStyledAttributes(themeResId, R.styleable.ActionSheetDialog);
            if (null != typedArray) {
                initAttributes(typedArray);
                typedArray.recycle();
            }

        }

        private void initAttributes(TypedArray typedArray) {
            if (null != typedArray) {
                mTitleTextColor = typedArray.getColor(R.styleable.ActionSheetDialog_titleTextColor, mTitleTextColor);
                mTitleTextSize = typedArray.getDimensionPixelSize(R.styleable.ActionSheetDialog_titleTextSize, mTitleTextSize);
                mTitleHeight = typedArray.getDimensionPixelSize(R.styleable.ActionSheetDialog_titleHeight, mTitleHeight);
                mMessageTextColor = typedArray.getColor(R.styleable.ActionSheetDialog_messageTextColor, mMessageTextColor);
                mMessageTextSize = typedArray.getDimensionPixelSize(R.styleable.ActionSheetDialog_messageTextSize, mMessageTextSize);
                mMessageHeight = typedArray.getDimensionPixelSize(R.styleable.ActionSheetDialog_messageHeight, mMessageHeight);
                mItemTextColor = typedArray.getColor(R.styleable.ActionSheetDialog_itemTextColor, mItemTextColor);
                mItemTextSize = typedArray.getDimensionPixelSize(R.styleable.ActionSheetDialog_itemTextSize, mItemTextSize);
                mItemHeight = typedArray.getDimensionPixelSize(R.styleable.ActionSheetDialog_itemHeight, mItemHeight);
                mPositiveTextColor = typedArray.getColor(R.styleable.ActionSheetDialog_positiveTextColor, mPositiveTextColor);
                mPositiveTextSize = typedArray.getDimensionPixelSize(R.styleable.ActionSheetDialog_positiveTextSize, mPositiveTextSize);
                mPositiveHeight = typedArray.getDimensionPixelSize(R.styleable.ActionSheetDialog_positiveHeight, mPositiveHeight);
                mCancelTextColor = typedArray.getColor(R.styleable.ActionSheetDialog_cancelTextColor, mCancelTextColor);
                mCancelTextSize = typedArray.getDimensionPixelSize(R.styleable.ActionSheetDialog_cancelTextSize, mCancelTextSize);
                mCancelHeight = typedArray.getDimensionPixelSize(R.styleable.ActionSheetDialog_cancelHeight, mCancelHeight);
                mCancelBackground = typedArray.getDrawable(R.styleable.ActionSheetDialog_cancelBackground);
                mCancelTopMagin = typedArray.getDimensionPixelSize(R.styleable.ActionSheetDialog_cancelTopMargins, mCancelTopMagin);
                mSheetMargins = typedArray.getDimensionPixelSize(R.styleable.ActionSheetDialog_sheetMargins, mSheetMargins);
                mContentBackground = typedArray.getDrawable(R.styleable.ActionSheetDialog_contentBackground);

            }
        }

        private void initDefaultAttributes(TypedArray defaultTypedArray) {
            if (null != defaultTypedArray) {
                mTitleTextColor = defaultTypedArray.getColor(R.styleable.ActionSheetDialog_titleTextColor, DEFAULT_VALUE);
                mTitleTextSize = defaultTypedArray.getDimensionPixelSize(R.styleable.ActionSheetDialog_titleTextSize, DEFAULT_VALUE);
                mTitleHeight = defaultTypedArray.getDimensionPixelSize(R.styleable.ActionSheetDialog_titleHeight, DEFAULT_VALUE);
                mMessageTextColor = defaultTypedArray.getColor(R.styleable.ActionSheetDialog_messageTextColor, DEFAULT_VALUE);
                mMessageTextSize = defaultTypedArray.getDimensionPixelSize(R.styleable.ActionSheetDialog_messageTextSize, DEFAULT_VALUE);
                mMessageHeight = defaultTypedArray.getDimensionPixelSize(R.styleable.ActionSheetDialog_messageHeight, DEFAULT_VALUE);
                mItemTextColor = defaultTypedArray.getColor(R.styleable.ActionSheetDialog_itemTextColor, DEFAULT_VALUE);
                mItemTextSize = defaultTypedArray.getDimensionPixelSize(R.styleable.ActionSheetDialog_itemTextSize, DEFAULT_VALUE);
                mItemHeight = defaultTypedArray.getDimensionPixelSize(R.styleable.ActionSheetDialog_itemHeight, DEFAULT_VALUE);
                mPositiveTextColor = defaultTypedArray.getColor(R.styleable.ActionSheetDialog_positiveTextColor, DEFAULT_VALUE);
                mPositiveTextSize = defaultTypedArray.getDimensionPixelSize(R.styleable.ActionSheetDialog_positiveTextSize, DEFAULT_VALUE);
                mPositiveHeight = defaultTypedArray.getDimensionPixelSize(R.styleable.ActionSheetDialog_positiveHeight, DEFAULT_VALUE);
                mCancelTextColor = defaultTypedArray.getColor(R.styleable.ActionSheetDialog_cancelTextColor, DEFAULT_VALUE);
                mCancelTextSize = defaultTypedArray.getDimensionPixelSize(R.styleable.ActionSheetDialog_cancelTextSize, DEFAULT_VALUE);
                mCancelHeight = defaultTypedArray.getDimensionPixelSize(R.styleable.ActionSheetDialog_cancelHeight, DEFAULT_VALUE);
                mCancelBackground = defaultTypedArray.getDrawable(R.styleable.ActionSheetDialog_cancelBackground);
                mCancelTopMagin = defaultTypedArray.getDimensionPixelSize(R.styleable.ActionSheetDialog_cancelTopMargins, DEFAULT_VALUE);
                mSheetMargins = defaultTypedArray.getDimensionPixelSize(R.styleable.ActionSheetDialog_sheetMargins, DEFAULT_VALUE);
                mContentBackground = defaultTypedArray.getDrawable(R.styleable.ActionSheetDialog_contentBackground);

            }
        }


        @Override
        public ActionSheetBuilder setCancelable(boolean cancelable) {
            mCancelable = cancelable;
            return this;
        }

        @Override
        public ActionSheetBuilder setMessage(CharSequence message) {
            mMessage = (String) message;
            return this;
        }

        @Override
        public ActionSheetBuilder setMessage(@StringRes int messageId) {
            mMessage = mContext.getString(messageId);
            return this;
        }

        @Override
        public ActionSheetBuilder setTitle(CharSequence title) {
            mTitle = (String) title;
            return this;
        }

        @Override
        public ActionSheetBuilder setTitle(@StringRes int titleId) {
            mTitle = mContext.getString(titleId);
            return this;
        }

        @Override
        public ActionSheetBuilder setNegativeButton(CharSequence text, OnClickListener listener) {
            mNegativeText = (String) text;
            mNegativeClickListener = listener;
            mCancelable = true;
            return this;
        }

        @Override
        public ActionSheetBuilder setPositiveButton(CharSequence text, OnClickListener listener) {
            mPositiveText = (String) text;
            mPositiveClickListener = listener;
            return this;
        }

        @Override
        public ActionSheetBuilder setItems(CharSequence[] items, OnClickListener listener) {
            for (int i = 0; i < items.length; i++) {
                ActionSheetItem item = new ActionSheetItem((String) items[i], listener);
                mActionSheetItems.add(item);
            }
            return this;
        }

        @Override
        public ActionSheetBuilder setItems(@ArrayRes int itemsId, OnClickListener listener) {
            this.setItems(mContext.getResources().getStringArray(itemsId), listener);
            return this;
        }

        @Override
        public ActionSheetDialog create() {
            mActionSheetDialog = new ActionSheetDialog(mContext);
            Window window = mActionSheetDialog.getWindow();
            window.setGravity(Gravity.BOTTOM);
            WindowManager.LayoutParams params = window.getAttributes();
            params.y = dpToPx(8.0f);
            params.x = 0;
            WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);

            int width = windowManager.getDefaultDisplay().getWidth();
            Log.d(TAG, "create: width: " + width + " paddings: " + dpToPx(8.0f));
            params.width = width - 2 * dpToPx(8.0f);
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
        LinearLayout mSheetItemContainer;
        TextView mCancelView;
        TextView mPositiveView;
        SheetItemOnClickListener mSheetItemOnClickListener = new SheetItemOnClickListener();

        private void initViews() {
            View rootView = LayoutInflater.from(mContext)
                    .inflate(R.layout.layout_action_sheet_dialog, null);
            mTitleView = (TextView) rootView.findViewById(R.id.tv_title);
            mMessageView = (TextView) rootView.findViewById(R.id.tv_message);
            mSheetItemContainer = (LinearLayout) rootView.findViewById(R.id.scrollView_sheet_list);
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
                //mPositiveView.setPadding(0, dpToPx(5.0f), 0, dpToPx(5.0f));
                mPositiveView.setText(mPositiveText);
                mPositiveView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mPositiveTextSize);
                mPositiveView.setTextColor(mPositiveTextColor);
                mPositiveView.setTag(AlertDialog.BUTTON_POSITIVE);
                mPositiveView.setOnClickListener(mSheetItemOnClickListener);
                mPositiveView.setMinHeight(mPositiveHeight);
                mSheetItemContainer.addView(mPositiveView);
            }

        }

        private void handleCancel() {
            if (null != mCancelView) {
                if (mCancelable) {
                    mCancelView.setMinHeight(mCancelHeight);
                    mCancelView.setTextColor(mCancelTextColor);
                    mCancelView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mCancelTextSize);
                    if (null != mCancelBackground) {
                        mCancelView.setBackground(mCancelBackground);
                    }
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
                    params.setMargins(0, mCancelTopMagin, 0, 0);
                    mCancelView.setLayoutParams(params);
                    if (null != mNegativeText) {
                        mCancelView.setText(mNegativeText);
                    }
                    mCancelView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (null != mNegativeClickListener) {
                                mNegativeClickListener.onClick(mActionSheetDialog, AlertDialog.BUTTON_NEGATIVE);
                            }
                            mActionSheetDialog.dismiss();
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
                    //sheetItemView.setPadding(0, dpToPx(5.0f), 0, dpToPx(5.0f));
                    sheetItemView.setText(item.text);
                    sheetItemView.setMinHeight(mItemHeight);
                    sheetItemView.setTextSize(TypedValue.COMPLEX_UNIT_SP, mItemTextSize);
                    sheetItemView.setTextColor(mItemTextColor);
                    sheetItemView.setTag(i);
                    sheetItemView.setOnClickListener(mSheetItemOnClickListener);
                    mSheetItemContainer.addView(sheetItemView);
                }

            }
        }

        private void handleMessage() {
            if (null != mMessageView) {
                if (null == mMessage) {
                    mMessageView.setVisibility(View.GONE);
                } else {
                    mMessageView.setMinHeight(mMessageHeight);
                    mMessageView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mMessageTextSize);
                    mMessageView.setTextColor(mMessageTextColor);
                    mMessageView.setText(mMessage);
                }
            }
        }

        private void handleTitle() {
            if (null != mTitleView) {
                if (null == mTitle) {
                    mTitleView.setVisibility(View.GONE);
                } else {
                    mTitleView.setMinHeight(mTitleHeight);
                    mTitleView.setTextColor(mTitleTextColor);
                    mTitleView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTitleTextSize);
                    mTitleView.setText(mTitle);
                }
            }
        }
        public int dpToPx(float dp) {
            float scale = mContext.getResources().getDisplayMetrics().density;
            return (int) (dp * scale + 0.5f);
        }

        public int spToPx(float sp) {
            float scale = mContext.getResources().getDisplayMetrics().scaledDensity;
            return (int) (sp * scale + 0.5f);
        }


        static class ActionSheetItem {

            String text;
            OnClickListener listener;
            public ActionSheetItem(String text, OnClickListener listener) {
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
                Log.d(TAG, "onClick: tag = " + tag);
                if (BUTTON_POSITIVE == tag) {
                    if (null != mPositiveClickListener) {
                        mPositiveClickListener.onClick(mActionSheetDialog, BUTTON_POSITIVE);
                        mActionSheetDialog.dismiss();
                    }
                    mActionSheetDialog.dismiss();
                } else {
                    mActionSheetItems.get(tag).listener.onClick(mActionSheetDialog, tag);
                }
            }
        }
    }
}
