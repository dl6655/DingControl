package widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dlingli.dingcontrol.R;

/**
 * Created by dlingli on 16/10/5.
 */
public class HorizontalNumberPicker extends LinearLayout {
    private final static int MIN_UPDATE_INTERVAL = 50;

    private int value;
    private int maxValue;
    private int minValue;
    private int stepSize;
    private boolean showLeadingZeros;



    private CirclePressView buttonMinus;
    private CirclePressView buttonPlus;
    private TextView textValue;

    private boolean autoIncrement;
    private boolean autoDecrement;

    private int updateInterval;

    private Handler updateIntervalHandler;

    private HorizontalNumberPickerListener listener;


    public HorizontalNumberPicker(Context context) {
        super(context);
    }

    public HorizontalNumberPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public HorizontalNumberPicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    public void init(Context context, AttributeSet attrs){

        if (isInEditMode()) {
            return;
        }

        LayoutInflater layoutInflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layoutInflater.inflate(R.layout.horizontal_number_picker, this);

//
//        TypedArray typedArray =
//                context.obtainStyledAttributes(attrs, R.styleable.HorizontalNumberPicker);
        Resources res = getResources();

//        String buttonPlusText = typedArray.getString(R.styleable.HorizontalNumberPicker_plusButtonText);
        initButtonPlus(
                res.getString(R.string.defaultButtonPlus));

//        String buttonMinusText =
//                typedArray.getString(R.styleable.HorizontalNumberPicker_minusButtonText);
        initButtonMinus(
                res.getString(R.string.defaultButtonMinus));

        minValue = 0;
        maxValue = 999;

        updateInterval = 100;
        stepSize =1;
        showLeadingZeros = false;

        initTextValue();
        value = 0;

        this.setValue();

        autoIncrement = false;
        autoDecrement = false;

        updateIntervalHandler = new Handler();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(getResources().getColor(android.R.color.black));
    }

    public interface HorizontalNumberPickerListener {
        void onHorizontalNumberPickerChanged(HorizontalNumberPicker horizontalNumberPicker, int value);
    }
    private void initTextValue() {
        textValue = (TextView) findViewById(R.id.text_value);
    }

    private void initButtonPlus(String text) {
        buttonPlus = (CirclePressView) findViewById(R.id.button_plus);

        buttonPlus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                increment();
                buttonPlus.click();
            }
        });

        buttonPlus.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View v) {

//                scaleMaxButton(0);

                autoIncrement = true;
                updateIntervalHandler.post(new repeat());
                buttonPlus.clickLong();
                return false;
            }
        });

        buttonPlus.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP && autoIncrement) {
                    autoIncrement = false;
                }
                if(event.getAction() == MotionEvent.ACTION_UP){
                    buttonPlus.cancleLongclick();
                }
                return false;
            }
        });
    }

    private void initButtonMinus(String text) {
        buttonMinus = (CirclePressView) findViewById(R.id.button_minus);

        buttonMinus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                decrement();
                buttonMinus.click();
            }
        });

        buttonMinus.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View v) {

                buttonMinus.clickLong();
                autoDecrement = true;
                updateIntervalHandler.post(new repeat());
                return false;
            }
        });

        buttonMinus.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP && autoDecrement) {
                    autoDecrement = false;
                }
                if(event.getAction() == MotionEvent.ACTION_UP){
                    buttonMinus.cancleLongclick();
                }
                return false;
            }
        });
    }
    public void scaleMaxButtonPlus(){
        Animation animation = AnimationUtils.loadAnimation(
                getContext(), R.anim.scale_max);
        buttonPlus.startAnimation(animation);
    }
    public void scaleMaxButton(int buttonType){

        AnimationSet animationSet = new AnimationSet(true);
        ScaleAnimation scaleAnimation = new ScaleAnimation(
                1.0f, 1.5f,1.0f,1.5f,
                Animation.RELATIVE_TO_SELF,0.5f,
                Animation.RELATIVE_TO_SELF,0.5f);
        scaleAnimation.setDuration(200);

        animationSet.addAnimation(scaleAnimation);
        animationSet.setFillAfter(true);
        switch (buttonType){
            case 0:
                buttonPlus.startAnimation(animationSet);
                break;
            case 1:
                buttonMinus.startAnimation(animationSet);
                break;
        }

    }
    public void scaleMinButton(int buttonType){

        AnimationSet animationSet = new AnimationSet(true);
        ScaleAnimation scaleAnimation = new ScaleAnimation(
                1.5f, 1.0f,1.5f,1.0f,
                Animation.RELATIVE_TO_SELF,0.5f,
                Animation.RELATIVE_TO_SELF,0.5f);
        scaleAnimation.setDuration(10);
        animationSet.setFillAfter(true);
        animationSet.addAnimation(scaleAnimation);
        switch (buttonType){
            case 0:
                buttonPlus.startAnimation(animationSet);
                break;
            case 1:
                buttonMinus.startAnimation(animationSet);
                break;
        }


    }
    public CirclePressView getButtonMinusView() {
        return buttonMinus;
    }

    public CirclePressView getButtonPlusView() {
        return buttonPlus;
    }

    public TextView getTextValueView() {
        return textValue;
    }

    public void increment() {
        if (value < maxValue) {
            this.setValue(value + stepSize);
        }
    }

    public void decrement() {
        if (value > minValue) {
            this.setValue(value - stepSize);
        }
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        if (value > maxValue) {
            value = maxValue;
        }
        if (value < minValue) {
            value = minValue;
        }

        this.value = value;
        this.setValue();
    }

    private void setValue() {
        String formatter = "%0" + String.valueOf(maxValue).length() + "d";
        textValue.setText(showLeadingZeros ? String.format(formatter, value) : String.valueOf(value));
        if (listener != null) {
            listener.onHorizontalNumberPickerChanged(this, value);
        }
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
        if (maxValue < value) {
            value = maxValue;
            this.setValue();
        }
    }

    public int getMinValue() {
        return minValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
        if (minValue > value) {
            value = minValue;
            this.setValue();
        }
    }

    public int getStepSize() {
        return stepSize;
    }

    public void setStepSize(int stepSize) {
        this.stepSize = stepSize;
    }

    public void setShowLeadingZeros(boolean showLeadingZeros) {
        this.showLeadingZeros = showLeadingZeros;

        String formatter = "%0" + String.valueOf(maxValue).length() + "d";
        textValue.setText(showLeadingZeros ? String.format(formatter, value) : String.valueOf(value));
    }

    public long getOnLongPressUpdateInterval() {
        return updateInterval;
    }

    public void setOnLongPressUpdateInterval(int intervalMillis) {
        if (intervalMillis < MIN_UPDATE_INTERVAL) {
            intervalMillis = MIN_UPDATE_INTERVAL;
        }
        this.updateInterval = intervalMillis;
    }

    public void setListener(HorizontalNumberPickerListener listener) {
        this.listener = listener;
    }

    private class repeat implements Runnable {
        public void run() {
            if (autoIncrement) {
                increment();
                updateIntervalHandler.postDelayed(new repeat(), updateInterval);
            } else if (autoDecrement) {
                decrement();
                updateIntervalHandler.postDelayed(new repeat(), updateInterval);
            }
        }
    }
}

