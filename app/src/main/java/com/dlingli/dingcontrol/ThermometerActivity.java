package com.dlingli.dingcontrol;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import widget.Thermometer;

public class ThermometerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thermometer);
        Thermometer thermometer=(Thermometer)findViewById(R.id.thermometer_view);
        thermometer.setTargetTemperature(41);
    }



}
