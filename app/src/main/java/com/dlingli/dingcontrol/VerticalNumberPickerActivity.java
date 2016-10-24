package com.dlingli.dingcontrol;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import widget.HorizontalNumberPicker;
import widget.VerticalNumberPicker;

public class VerticalNumberPickerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vertical_number_picker);
        VerticalNumberPicker horizontalNumberPicker=(VerticalNumberPicker)findViewById(R.id.vertical_number_picker);
        horizontalNumberPicker.setShowLeadingZeros(true);
        horizontalNumberPicker.setValue(6);
    }
}
