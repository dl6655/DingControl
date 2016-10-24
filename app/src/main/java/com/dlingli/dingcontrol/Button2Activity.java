package com.dlingli.dingcontrol;

import android.graphics.drawable.ClipDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class Button2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button2);
        final View button1=findViewById(R.id.primary_button);

        View button1_mask=findViewById(R.id.primary_button_mask);
//
//       final Animation in=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.push_up_in);
//        final Animation out=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.push_bottom_out);
        button1_mask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 if(button1.getVisibility()==View.GONE){
                     Animation in=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.push_up_in);
                     button1.setAnimation(in);
                     button1.setVisibility(View.VISIBLE);
                 }
                 else{
                     Animation out=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.push_bottom_out);
                     button1.setAnimation(out);
                     button1.setVisibility(View.GONE);
                 }

            }
        });


//        final ClipDrawable clipDrawable=(ClipDrawable)button1.getBackground();


//        final Handler handler = new Handler() {
//            public void handleMessage(Message msg) {
//                if (msg.what == 0x1233) {
//                    clipDrawable.setLevel(clipDrawable.getLevel() + 500);
//                }
//            }
//        };



        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplication(),"显示切换动画",Toast.LENGTH_LONG).show();



//                final Timer timer=new Timer();
//                timer.schedule(new TimerTask() {
//                    @Override
//                    public void run() {
//                        Message msg = new Message();
//                        msg.what = 0x1233;
//                        handler.sendMessage(msg);
//                        if (clipDrawable.getLevel() >= 10000 || isFinishing()) {
//                            timer.cancel();
//                        }
//                    }
//                },0,10);




            }
        });


    }
}
