package com.dlingli.dingcontrol;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import widget.CirclePressView;

public class CirclePressActivity extends AppCompatActivity {
    private CirclePressView circlePressView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_press);
        circlePressView=(CirclePressView)findViewById(R.id.layout_circle_press);
        circlePressView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                circlePressView.click();
            }
        });
        circlePressView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                circlePressView.clickLong();
                return true;
            }
        });
        circlePressView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()){
                    case MotionEvent.ACTION_UP:
                          circlePressView.cancleLongclick();
                        break;
                }
                return false;
            }
        });
    }
}
