package com.dlingli.dingcontrol;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import widget.SwitchLineView;

public class SwitchLineActivity extends AppCompatActivity {
    SwitchLineView switchLineView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switch_line);
        switchLineView=(SwitchLineView)findViewById(R.id.switch_line_layout);
        switchLineView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchLineView.click();
            }
        });
    }
}
