package com.dlingli.dingcontrol;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import widget.HorizontalNumberPicker;
import widget.VerticalNumberPicker;

public class HorizontalNumberPickerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horizontal_number_picker);
        HorizontalNumberPicker horizontalNumberPicker=(HorizontalNumberPicker)findViewById(R.id.horizontal_number_picker);
//        VerticalNumberPicker horizontalNumberPicker=(VerticalNumberPicker)findViewById(R.id.horizontal_number_picker);
//        horizontalNumberPicker.getButtonMinusView().setBackgroundResource(R.drawable.seekbar_thumb);
//        horizontalNumberPicker.getButtonPlusView().setBackgroundResource(R.drawable.seekbar_thumb);
        horizontalNumberPicker.setShowLeadingZeros(true);
        horizontalNumberPicker.setValue(6);




    }
}
