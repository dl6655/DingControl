package widget;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.dlingli.dingcontrol.R;

/**
 * Created by dlingli on 16/10/6.
 */
public class SwitchView extends LinearLayout {

    private ImageView left_circle_bg, left_circle_above;
    private ImageView right_circle_bg, right_circle_above;
    private ImageView middle_left_point, middle_right_point, middle_circle_above;
    private int moveX, middle_circle_bg_Top;


    public SwitchView(Context context) {
        super(context);
    }

    public SwitchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public SwitchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void init(Context context, AttributeSet attrs) {
        LayoutInflater layoutInflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.layout_switch_view, this);
        left_circle_bg = (ImageView) findViewById(R.id.left_circle_bg);
        left_circle_above = (ImageView) findViewById(R.id.left_circle_above);
        right_circle_bg = (ImageView) findViewById(R.id.right_circle_bg);

        right_circle_bg.setVisibility(GONE);
        right_circle_above = (ImageView) findViewById(R.id.right_circle_above);
        middle_left_point = (ImageView) findViewById(R.id.middle_left_point);
        middle_right_point = (ImageView) findViewById(R.id.middle_right_point);
        middle_right_point.setVisibility(GONE);
        middle_left_point.setVisibility(GONE);

        middle_circle_above = (ImageView) findViewById(R.id.middle_green_point_above);
        middle_circle_bg_Top = middle_circle_above.getTop();
        moveX = right_circle_above.getLeft() - left_circle_above.getLeft() - left_circle_above.getWidth();

        float mo = right_circle_above.getX();

        right_circle_above.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(right_circle_bg.getVisibility()==GONE){
                scaleMinButton(0);
                translateAnimation(1);
                scaleMaxButton(1);
                right_circle_bg.setVisibility(VISIBLE);
                }
            }
        });
        left_circle_above.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (right_circle_bg.getVisibility() == VISIBLE) {
                    scaleMinButton(1);
                    translateAnimation(0);
                    scaleMaxButton(0);
                    right_circle_bg.setVisibility(GONE);
                }
            }
        });


    }

    public void scaleMaxButton(int buttonType) {

//        AnimationSet animationSet = new AnimationSet(true);
//        ScaleAnimation scaleAnimation = new ScaleAnimation(
//                0.0f, 1.0f, 0.0f, 1.0f,
//                Animation.RELATIVE_TO_SELF, 0.5f,
//                Animation.RELATIVE_TO_SELF, 0.5f);
//        scaleAnimation.setDuration(500);
//
//        animationSet.addAnimation(scaleAnimation);
//        animationSet.setFillAfter(true);

//        ObjectAnimator objectAnimator=ObjectAnimator.ofFloat(left_circle_bg,"scale",0.0f,1.0f)
//                    .setDuration(500);
//        objectAnimator.start();

        switch (buttonType) {
            case 0:
                PropertyValuesHolder pvhX=PropertyValuesHolder.ofFloat("scaleX",0.0f,1.0f);
                PropertyValuesHolder pvhY=PropertyValuesHolder.ofFloat("scaleY",0.0f,1.0f);
                ObjectAnimator.ofPropertyValuesHolder(left_circle_bg,pvhX,pvhY).setDuration(500).start();


//                objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                    @Override
//                    public void onAnimationUpdate(ValueAnimator animation) {
//                        float sVal=(Float)animation.getAnimatedValue();
//                        left_circle_bg.setScaleX(sVal);
//                        left_circle_bg.setScaleY(sVal);
//                    }
//                });

//                left_circle_bg.startAnimation(animationSet);
                break;
            case 1:

                PropertyValuesHolder pvhrX=PropertyValuesHolder.ofFloat("scaleX",0.0f,1.0f);
                PropertyValuesHolder pvhrY=PropertyValuesHolder.ofFloat("scaleY",0.0f,1.0f);
                ObjectAnimator.ofPropertyValuesHolder(right_circle_bg,pvhrX,pvhrY).setDuration(500).start();
//                objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                    @Override
//                    public void onAnimationUpdate(ValueAnimator animation) {
//                        float sVal=(Float)animation.getAnimatedValue();
//                        right_circle_bg.setScaleX(sVal);
//                        right_circle_bg.setScaleY(sVal);
//                    }
//                });
//                right_circle_bg.startAnimation(animationSet);
                break;
        }

    }

    public void translateAnimation(int buttonType) {
        AnimationSet animationSet = new AnimationSet(true);
        TranslateAnimation translateAnimation = null;
        if (buttonType == 0) {
            ObjectAnimator.ofFloat(middle_left_point,"translationX",-200)
                     .setDuration(500)
                     .start();

//            translateAnimation = new TranslateAnimation(0,
//                    -2000, 0, 0);
//            translateAnimation.setInterpolator(new AccelerateInterpolator());
//            translateAnimation.setDuration(500);
//            animationSet.addAnimation(translateAnimation);
//            animationSet.setFillAfter(true);
//            middle_right_point.startAnimation(animationSet);
        } else if (buttonType == 1) {
            middle_left_point.setVisibility(VISIBLE);
            ObjectAnimator.ofFloat(middle_left_point,"translationX",200)
                    .setDuration(500)
                    .start();

//            middle_left_point.setVisibility(VISIBLE);
//            translateAnimation = new TranslateAnimation(0,
//                   2000, 0, 0);
//            translateAnimation.setInterpolator(new AccelerateInterpolator());
//            translateAnimation.setDuration(500);
//            animationSet.addAnimation(translateAnimation);
//            animationSet.setFillAfter(true);
//            middle_left_point.startAnimation(animationSet);
        }


    }

    public void scaleMinButton(int buttonType) {
//        AnimationSet animationSet = new AnimationSet(true);
//        ScaleAnimation scaleAnimation = new ScaleAnimation(
//                1.0f, 0.0f, 1.0f, 0.0f,
//                Animation.RELATIVE_TO_SELF, 0.5f,
//                Animation.RELATIVE_TO_SELF, 0.5f);
//        scaleAnimation.setDuration(500);
//        animationSet.setFillAfter(true);
//        animationSet.addAnimation(scaleAnimation);
//        ObjectAnimator objectAnimator=ObjectAnimator.ofFloat(left_circle_bg,"scale",1.0f,0.0f)
//                .setDuration(500);
//        objectAnimator.start();
        switch (buttonType) {
            case 0:

                PropertyValuesHolder pvhX=PropertyValuesHolder.ofFloat("scaleX",1.0f,0.0f);
                PropertyValuesHolder pvhY=PropertyValuesHolder.ofFloat("scaleY",1.0f,0.0f);
                ObjectAnimator.ofPropertyValuesHolder(left_circle_bg,pvhX,pvhY).setDuration(500).start();
//                objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                    @Override
//                    public void onAnimationUpdate(ValueAnimator animation) {
//                        float sVal=(Float)animation.getAnimatedValue();
//                        left_circle_bg.setScaleX(sVal);
//                        left_circle_bg.setScaleY(sVal);
//                    }
//                });
//                left_circle_bg.startAnimation(animationSet);
                break;
            case 1:
                PropertyValuesHolder pvhrX=PropertyValuesHolder.ofFloat("scaleX",1.0f,0.0f);
                PropertyValuesHolder pvhrY=PropertyValuesHolder.ofFloat("scaleY",1.0f,0.0f);
                ObjectAnimator.ofPropertyValuesHolder(right_circle_bg,pvhrX,pvhrY).setDuration(500).start();
//                objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                    @Override
//                    public void onAnimationUpdate(ValueAnimator animation) {
//                        float sVal=(Float)animation.getAnimatedValue();
//                        right_circle_bg.setScaleX(sVal);
//                        right_circle_bg.setScaleY(sVal);
//                    }
//                });
//                right_circle_bg.startAnimation(animationSet);
                break;
        }
    }

}
