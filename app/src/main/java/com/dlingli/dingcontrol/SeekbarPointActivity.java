package com.dlingli.dingcontrol;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

import widget.CustomSeekbarDot;

public class SeekbarPointActivity extends AppCompatActivity implements CustomSeekbarDot.ResponseOnTouch {
    private ArrayList<String> volume_sections = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seekbar_point);
       CustomSeekbarDot myCustomSeekBar=(CustomSeekbarDot)findViewById(R.id.CustomSeekbarPoin);

        volume_sections.add("1");
        volume_sections.add("2");
        volume_sections.add("3");
        volume_sections.add("4");
        volume_sections.add("5");
        volume_sections.add("6");
//        volume_sections.add("7");
//        volume_sections.add("8");
//        volume_sections.add("9");
//        volume_sections.add("10");
        myCustomSeekBar.initData(volume_sections);
        myCustomSeekBar.setProgress(0);
        myCustomSeekBar.setResponseOnTouch(this);

    }

    @Override
    public void onTouchResponse(int volume) {

    }
}
