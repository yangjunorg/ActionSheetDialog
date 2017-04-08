package com.android.test.actionsheetdialog;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.actionsheetdialog.ActionSheetDialog;

public class MainActivity extends AppCompatActivity implements ActionSheetDialog.ActionSheetBuilder.ActionSheetItemClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button startActionSheet = (Button) findViewById(R.id.btn_start_action_sheet);
        startActionSheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActionSheetDialog dialog = (ActionSheetDialog) new ActionSheetDialog.ActionSheetBuilder(MainActivity.this)
                        .addActionSheetItem("确认", Color.parseColor("#f44336"), new ActionSheetDialog.ActionSheetBuilder.ActionSheetItemClickListener() {
                            @Override
                            public void onClick(int position) {
                                Toast.makeText(MainActivity.this, "onClick()", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setCancelable(true)
                        .create();
                dialog.show();
            }
        });
    }

    @Override
    public void onClick(int position) {

    }
}
