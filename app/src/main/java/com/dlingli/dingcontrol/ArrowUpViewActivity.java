package com.dlingli.dingcontrol;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import widget.ArrowDownView;
import widget.ArrowUpView;
import widget.ArrowUpViewV1;

public class ArrowUpViewActivity extends AppCompatActivity {
    ArrowUpView layout_arrowupview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_arrow_view);
        layout_arrowupview=(ArrowUpView)findViewById(R.id.layout_arrowupview);
        layout_arrowupview.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                layout_arrowupview.longClick();
                return true;
            }
        });
        layout_arrowupview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout_arrowupview.click();
            }
        });
        layout_arrowupview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_UP:
                        layout_arrowupview.cancleLongclick();
                        break;
                }
                return false;
            }
        });


    }
}
