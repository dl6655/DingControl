package com.dlingli.dingcontrol;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import widget.ArrowDownView;

public class ArrowDownViewActivity extends AppCompatActivity {
    ArrowDownView layout_arrowdownview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arrow_down_view);
        layout_arrowdownview=(ArrowDownView)findViewById(R.id.layout_arrowdownview);
        layout_arrowdownview.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                layout_arrowdownview.longClick();
                return true;
            }
        });
        layout_arrowdownview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout_arrowdownview.click();
            }
        });
        layout_arrowdownview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_UP:
                               layout_arrowdownview.cancleLongclick();
                        break;
                }
                return false;
            }
        });
    }
}
