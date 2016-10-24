package com.dlingli.dingcontrol;

import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import widget.ArcViewLeft;
import widget.CirclePointView;

public class ArcViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acr_view);
        final CirclePointView pointView=(CirclePointView)findViewById(R.id.circle_arc_bg);
        ArcViewLeft pointView1=(ArcViewLeft)findViewById(R.id.circle_arc_bg1);
        pointView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAnimator animator = ObjectAnimator.ofFloat(pointView, "pointRadius",50, 100);
                animator.setDuration(1000);
                animator.start();
            }
        });


    }
}
