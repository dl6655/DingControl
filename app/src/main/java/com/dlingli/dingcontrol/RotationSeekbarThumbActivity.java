package com.dlingli.dingcontrol;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.SeekBar;

import widget.RotationSeekbarThumb;

public class RotationSeekbarThumbActivity extends AppCompatActivity {
    RotationSeekbarThumb mSeekBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rotation_seekbar_thumb);
        mSeekBar = (RotationSeekbarThumb) findViewById(R.id.seek_bar_layout);
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mSeekBar.setThumbRotationAnimation(null);
            }
        });

    }
}
