package com.dlingli.dingcontrol;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import widget.ArrowDownView;

public class MainActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String[] items = new String[]{
                 "ButtonPrimaryActivity"
                , "RotationSeekbarThumbActivity"
                , "SeekbarPointActivity"
                , "SeekArcActivity"
                , "HorizontalNumberPickerActivity"
                , "VerticalNumberPickerActivity"
                , "SwitchLineActivity"

        };


//        String[] items = new String[]{
//                 "ButtonPrimaryActivity"
//                , "SwitchLineActivity"
//                , "RotationSeekbarThumbActivity"
//                , "SeekbarPointActivity"
//                , "SeekArcActivity"
//                , "ArrowDownViewActivity"
//                , "ArrowUpViewActivity"
//                , "HorizontalNumberPickerActivity"
//                , "VerticalNumberPickerActivity"
//                , "SwitchActivity"
//                , "ArcViewActivity"
//
//        };
        setListAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, items));

    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent intent;
        switch (position) {
            case 0:
                intent = new Intent(getApplicationContext(), ButtonPrimaryActivity.class);
                startActivity(intent);
                break;
            case 1:
                intent = new Intent(getApplicationContext(), RotationSeekbarThumbActivity.class);
                startActivity(intent);
                break;
            case 2:
                intent = new Intent(getApplicationContext(), SeekbarPointActivity.class);
                startActivity(intent);
                break;
            case 3:
                intent = new Intent(getApplicationContext(), SeekArcActivity.class);
                startActivity(intent);
                break;
            case 4:
                intent = new Intent(getApplicationContext(), HorizontalNumberPickerActivity.class);
                startActivity(intent);
                break;
            case 5:
                intent = new Intent(getApplicationContext(), VerticalNumberPickerActivity.class);
                startActivity(intent);
                break;
            case 6:
                intent = new Intent(getApplicationContext(), SwitchLineActivity.class);
                startActivity(intent);
                break;


//        switch (position) {
//            case 0:
//                intent = new Intent(getApplicationContext(), ButtonPrimaryActivity.class);
//                startActivity(intent);
//                break;
//            case 1:
//                intent = new Intent(getApplicationContext(), SwitchLineActivity.class);
//                startActivity(intent);
//                break;
//            case 2:
//                intent = new Intent(getApplicationContext(), RotationSeekbarThumbActivity.class);
//                startActivity(intent);
//                break;
//            case 3:
//                intent = new Intent(getApplicationContext(), SeekbarPointActivity.class);
//                startActivity(intent);
//                break;
//            case 4:
//                intent = new Intent(getApplicationContext(), SeekArcActivity.class);
//                startActivity(intent);
//                break;
//            case 5:
//                intent = new Intent(getApplicationContext(), ArrowDownViewActivity.class);
//                startActivity(intent);
//                break;
//            case 6:
//                intent = new Intent(getApplicationContext(), ArrowUpViewActivity.class);
//                startActivity(intent);
//                break;
//            case 7:
//                intent = new Intent(getApplicationContext(), HorizontalNumberPickerActivity.class);
//                startActivity(intent);
//                break;
//            case 8:
//                intent = new Intent(getApplicationContext(), VerticalNumberPickerActivity.class);
//                startActivity(intent);
//                break;
//            case 9:
//                intent = new Intent(getApplicationContext(), SwitchActivity.class);
//                startActivity(intent);
//                break;
//            case 10:
//                intent = new Intent(getApplicationContext(), ArcViewActivity.class);
//                startActivity(intent);
//                break;

        }
    }

}
