package com.dl.drawingview;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import com.dl.drawingview.lib.draw.helper.unitconversion.DimenConverter;

import imdemo.dl.com.drawingview.R;

public class ThicknessAdjustController extends BasePopupController {
    private final ThicknessAdjustController self = this;

    private ThicknessDelegate thicknessDelegate;

    private SeekBar seekBar;

    public ThicknessAdjustController(Context context) {
        super(context);

        self.rootView = View.inflate(context, R.layout.thickness_adjust_controller, null);
        self.rootView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        self.preferWidth = DimenConverter.dpToPixel(250);
        self.preferHeight = DimenConverter.dpToPixel(50);

        self.setPopupBackgroundColor(Color.BLACK);

        self.seekBar = (SeekBar) self.rootView.findViewById(R.id.seekBar);
        self.seekBar.setMax(50);

        self.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (self.getThicknessDelegate() != null) {
                    self.getThicknessDelegate().thicknessDidChangeFromThicknessAdjustController(self, seekBar.getProgress() + 1);
                }
            }
        });
    }


    public void setMax(int max) {
        seekBar.setMax(max);
    }

    public ThicknessDelegate getThicknessDelegate() {
        return thicknessDelegate;
    }

    public void setThicknessDelegate(ThicknessDelegate thicknessDelegate) {
        this.thicknessDelegate = thicknessDelegate;
    }

    public void setThickness(int thickness) {
        self.seekBar.setProgress(thickness - 1);
    }

    public interface ThicknessDelegate {
        void thicknessDidChangeFromThicknessAdjustController(ThicknessAdjustController controller, int thickness);
    }

}