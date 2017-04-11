package com.android.test.actionsheetdialog;

import android.content.DialogInterface;
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
                ActionSheetDialog dialog = new ActionSheetDialog.ActionSheetBuilder(MainActivity.this)
                        .setItems(new CharSequence[]{"Item1", "Item2", "Item3"}, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setMessage("13222028712")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(MainActivity.this, "onClick() dialog", Toast.LENGTH_SHORT).show();
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
