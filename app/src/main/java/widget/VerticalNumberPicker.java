package widget;

import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dlingli.dingcontrol.R;

/**
 * Created by dlingli on 16/10/5.
 */
public class VerticalNumberPicker extends LinearLayout {
    private final static int MIN_UPDATE_INTERVAL = 50;

    private int value;
    private int maxValue;
    private int minValue;
    private int stepSize;
    private boolean showLeadingZeros;



    private ArrowDownView buttonMinus;
    private ArrowUpView buttonPlus;
    private TextView textValue;

    private boolean autoIncrement;
    private boolean autoDecrement;

    private int updateInterval;

    private Handler updateIntervalHandler;

    private HorizontalNumberPickerListener listener;


    public VerticalNumberPicker(Context context) {
        super(context);
    }

    public VerticalNumberPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public VerticalNumberPicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    public void init(Context context, AttributeSet attrs){

        if (isInEditMode()) {
            return;
        }

        LayoutInflater layoutInflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layoutInflater.inflate(R.layout.vertical_number_picker, this);

//
//        TypedArray typedArray =
//                context.obtainStyledAttributes(attrs, R.styleable.HorizontalNumberPicker);
        Resources res = getResources();

//        String buttonPlusText = typedArray.getString(R.styleable.HorizontalNumberPicker_plusButtonText);
        initButtonPlus(res.getString(R.string.defaultButtonPlus));


//        String buttonMinusText =
//                typedArray.getString(R.styleable.HorizontalNumberPicker_minusButtonText);
        initButtonMinus(res.getString(R.string.defaultButtonMinus));


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


    public interface HorizontalNumberPickerListener {
        void onHorizontalNumberPickerChanged(VerticalNumberPicker horizontalNumberPicker, int value);
    }
    private void initTextValue() {
        textValue = (TextView) findViewById(R.id.text_value);
    }

    private void initButtonPlus(String text) {
        buttonPlus = (ArrowUpView) findViewById(R.id.button_plus);
        buttonPlus.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                increment();
                buttonPlus.click();
            }
        });

        buttonPlus.setOnLongClickListener(new OnLongClickListener() {
            public boolean onLongClick(View v) {
                buttonPlus.longClick();
                autoIncrement = true;
                updateIntervalHandler.post(new repeat());
                return true;
            }
        });

        buttonPlus.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP && autoIncrement) {
                    autoIncrement = false;
                }
                if(event.getAction() == MotionEvent.ACTION_UP ){
                    buttonPlus.cancleLongclick();
                }
                return false;
            }
        });
    }

    private void initButtonMinus(String text) {
        buttonMinus = (ArrowDownView) findViewById(R.id.button_minus);

        buttonMinus.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                decrement();
                buttonMinus.click();

            }
        });

        buttonMinus.setOnLongClickListener(new OnLongClickListener() {
            public boolean onLongClick(View v) {
                buttonMinus.longClick();
                autoDecrement = true;
                updateIntervalHandler.post(new repeat());
                return true;
            }
        });

        buttonMinus.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP && autoDecrement) {
                    autoDecrement = false;
                }
                if(event.getAction() == MotionEvent.ACTION_UP ){
                    buttonMinus.cancleLongclick();
                }
                return false;
            }
        });
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

    public void setShowLeadingZeros(boolean showLeadingZeros) {
        this.showLeadingZeros = showLeadingZeros;

        String formatter = "%0" + String.valueOf(maxValue).length() + "d";
        textValue.setText(showLeadingZeros ? String.format(formatter, value) : String.valueOf(value));
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

