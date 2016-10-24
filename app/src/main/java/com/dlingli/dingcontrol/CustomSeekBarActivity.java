package com.dlingli.dingcontrol;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.SeekBar;

public class CustomSeekBarActivity extends AppCompatActivity {
    SeekBar seekBar;
    ImageView rotation_bg;
    private float moveX,moveY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_seek_bar);
        seekBar = (SeekBar) findViewById(R.id.seek_bar_layout);
        rotation_bg = (ImageView) findViewById(R.id.rotation_bg);
        rotation_bg.setVisibility(View.INVISIBLE);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                perAverageAgle=0;
                scaleX=1.0f;
                scaleY=1.0f;
                seekBar.setThumb(getResources().getDrawable(R.drawable.ic_launcher));
                rotationImage(seekBar.getThumb());
//                seekBar.setThumb(rotationView(seekBar.getThumb()));
//                final SeekBar seekBar1=seekBar;
//                seekBar1.setThumb(null);
//                rotationScale();
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        seekBar1.setThumb(getResources().getDrawable(R.drawable.seekbar_thumb));
//                    }
//                },600);
//                seekBar.setThumb(rotationView(seekBar.getThumb()));
            }
        });


        seekBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                    case MotionEvent.ACTION_UP:
                        moveX=event.getX();
//                        ObjectAnimator animator = ObjectAnimator
//                                .ofFloat(rotation_bg, "translationX", 0,event.getX()-rotation_bg.getWidth()/2);
//                        animator.setDuration(0);
//                        animator.start();
                        Log.i("AAAD", "ACTION_UP seekBar");

//                        rotationView();
                        break;

                }
                return false;
            }
        });

    }

    public Drawable bitmapToDrawble(Bitmap bitmap, Context mcontext) {
        Drawable drawable = new BitmapDrawable(mcontext.getResources(), bitmap);
        return drawable;
    }

    public Bitmap drawableToBitamp(Drawable drawable) {
        BitmapDrawable bd = (BitmapDrawable) drawable;
        return bd.getBitmap();
    }

    private Bitmap big(Bitmap bitmap) {
        Matrix matrix = new Matrix();
        matrix.postScale(1.5f, 1.5f); //长和宽放大缩小的比例
        Bitmap resizeBmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizeBmp;
    }
    int perAverageAgle=0;
    float scaleX=1.0f,scaleY=1.0f;
    boolean isCycle=false;
    private Bitmap small(Bitmap bitmap) {
        Matrix matrix = new Matrix();
        if(perAverageAgle<=360){
            isCycle=true;
            perAverageAgle+=30;
            scaleX-=0.02;
            scaleY-=0.02;
            if(scaleX<0.1||scaleY<0.1){
                scaleX=0.1f;
                scaleY=0.1f;
            }
            matrix.postRotate(perAverageAgle);
            matrix.postScale(scaleX,scaleY); //长和宽放大缩小的比例
        }else{
            isCycle=false;
            matrix.postRotate(0);
            matrix.postScale(0.1f,0.1f); //长和宽放大缩小的比例
        }
            Bitmap resizeBmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            return resizeBmp;

    }

    public Drawable rotationView(Drawable drawable) {
        Bitmap bb = drawableToBitamp(drawable);
        bb= small(bb);
        return bitmapToDrawble(bb, getApplicationContext());

    }

    public void rotationImage(final Drawable drawable){
        seekBar.setThumb(rotationView(drawable));
        if(!isCycle){
            seekBar.setThumb(getResources().getDrawable(R.drawable.seekbar_thumb));
        }
        if(isCycle){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    rotationImage(drawable);
                }
            },10);
        }
    }

    public void rotationScale(){


        rotation_bg.setVisibility(View.VISIBLE);
        ObjectAnimator animatorAlpha = ObjectAnimator.ofFloat(rotation_bg, "alpha", 1.0f,0.0f);
        ObjectAnimator animatorScaleX = ObjectAnimator.ofFloat(rotation_bg, "scaleX", 1.0f,0.0f);
        ObjectAnimator animatorScaleY = ObjectAnimator.ofFloat(rotation_bg, "scaleY", 1.0f,0.0f);
        ObjectAnimator animatorRotation = ObjectAnimator.ofFloat(rotation_bg, "rotation", 0,360);
        ObjectAnimator animatorTranstlation = ObjectAnimator.ofFloat(rotation_bg, "translationX", 0,moveX-rotation_bg.getWidth()/2);
        AnimatorSet animatorSet=new AnimatorSet();
        animatorSet.setDuration(500);
        animatorSet.playTogether(animatorRotation,animatorScaleX,animatorScaleY);
        animatorSet.start();
    }

//    ObjectAnimator moveIn = ObjectAnimator.ofFloat(textview, "translationX", -500f, 0f);
//    ObjectAnimator rotate = ObjectAnimator.ofFloat(textview, "rotation", 0f, 360f);
//    ObjectAnimator fadeInOut = ObjectAnimator.ofFloat(textview, "alpha", 1f, 0f, 1f);
//    AnimatorSet animSet = new AnimatorSet();
//    animSet.play(rotate).with(fadeInOut).after(moveIn);
//    animSet.setDuration(5000);
//    animSet.start();
}
