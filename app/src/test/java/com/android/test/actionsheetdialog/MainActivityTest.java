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

package com.android.test.actionsheetdialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.widget.Button;

import org.apache.tools.ant.Main;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowDialog;

import static org.junit.Assert.*;

/**
 * Created by jonny.peng on 2017/4/12.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 23)
public class MainActivityTest {
    MainActivity mMainActivity;
//    @Before
//    public void setUp() throws Exception {
//        mMainActivity = Robolectric.setupActivity(MainActivity.class);
//        assertNotNull(mMainActivity);
//    }

    @Test
    public void onCreate() throws Exception {
        ActivityController<MainActivity> activityController = Robolectric.buildActivity(MainActivity.class).create().start();
        Activity activity = activityController.get();
        mMainActivity = Robolectric.setupActivity(MainActivity.class);
        Button startActionSheet = (Button) mMainActivity.findViewById(R.id.btn_start_action_sheet);
        assertEquals("start", startActionSheet.getText());
        startActionSheet.performClick();
        Dialog dialog = ShadowDialog.getLatestDialog();
        assertNotNull(dialog);

    }

//    @Test
//    public void onClick() throws Exception {
//
//    }

}