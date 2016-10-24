package com.dlingli.dingcontrol;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import widget.ButtonPrimaryView;

public class ButtonPrimaryActivity extends AppCompatActivity {
    private ButtonPrimaryView button_primary_layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button_primary);
        button_primary_layout=(ButtonPrimaryView)findViewById(R.id.button_primary_layout);
        button_primary_layout.setButtonText("Button");
//        button_primary_layout.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                Log.i("AAAD","ButtonPrimaryActivity onTouch");
//                return false;
//            }
//        });
        button_primary_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button_primary_layout.click();
            }
        });
    }
}
