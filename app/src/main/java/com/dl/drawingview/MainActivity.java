package com.dl.drawingview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import imdemo.dl.com.drawingview.R;

public class MainActivity extends AppCompatActivity {

    private DrawingFragment drawingFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getFragmentManager().beginTransaction()
                .replace(R.id.drawingFragmentLayout, getDrawingFragment(), "drawingFragment")
                .commit();
    }


    public DrawingFragment getDrawingFragment() {
        if (drawingFragment == null) {
            drawingFragment = (DrawingFragment) getFragmentManager().findFragmentByTag("drawingFragment");
            if (drawingFragment == null) {
                drawingFragment = new DrawingFragment();
            }
        }
        return drawingFragment;
    }
}
