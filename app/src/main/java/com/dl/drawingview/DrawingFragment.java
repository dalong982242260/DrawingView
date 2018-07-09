package com.dl.drawingview;

import android.app.Fragment;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.dl.drawingview.lib.draw.DrawingView;
import com.dl.drawingview.lib.draw.brush.drawing.CenterCircleBrush;
import com.dl.drawingview.lib.draw.brush.drawing.CircleBrush;
import com.dl.drawingview.lib.draw.brush.drawing.DrawingBrush;
import com.dl.drawingview.lib.draw.brush.drawing.EllipseBrush;
import com.dl.drawingview.lib.draw.brush.drawing.IsoscelesTriangleBrush;
import com.dl.drawingview.lib.draw.brush.drawing.LineBrush;
import com.dl.drawingview.lib.draw.brush.drawing.PenBrush;
import com.dl.drawingview.lib.draw.brush.drawing.PolygonBrush;
import com.dl.drawingview.lib.draw.brush.drawing.RectangleBrush;
import com.dl.drawingview.lib.draw.brush.drawing.RhombusBrush;
import com.dl.drawingview.lib.draw.brush.drawing.RightAngledTriangleBrush;
import com.dl.drawingview.lib.draw.brush.drawing.RoundedRectangleBrush;
import com.dl.drawingview.lib.draw.brush.drawing.ShapeBrush;
import com.dl.drawingview.lib.draw.brush.text.TextBrush;
import com.dl.drawingview.lib.draw.model.DrawingStep;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import imdemo.dl.com.drawingview.R;

public class DrawingFragment extends Fragment {

    private DrawingView drawingView;
    private DrawingView drawingView2;

    private Button undoButton;
    private Button redoButton;
    private Button clearButton;

    private Button penButton;
    private Button shapeButton;
    private Button textButton;
    private Button backgroundColorButton;

    private Button thicknessButton;
    private Button eraserButton;
    private Button colorButton;
    private Button fillTypeButton;
    private Button edgeRoundedButton;
    private Button oneStrokeOneLayerButton;
    private Button deleteLayerButton;

    private ThicknessAdjustController thicknessAdjustController;

    private List<Button> singleSelectionButtons;

    private List<ShapeBrush> shapeBrushes = new ArrayList<>();
    private PenBrush penBrush;
    private TextBrush textBrush;
    private Button ratio;
    private ThicknessAdjustController radioAdjustController;

    public DrawingFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.drawing_fragment, container, false);

        drawingView = (DrawingView) rootView.findViewById(R.id.drawingView);
        drawingView2 = (DrawingView) rootView.findViewById(R.id.drawingView2);
        drawingView2.setDisableTouchDraw(true);//设置不能画

        drawingView2.drawNextOverStep(TestDataUtils.getDrawStepData2());
        drawingView.setUndoRedoStateDelegate(new DrawingView.UndoRedoStateDelegate() {
            @Override
            public void onUndoRedoStateChange(int state, DrawingView drawingView, boolean canUndo, boolean canRedo) {
                undoButton.setEnabled(canUndo);
                redoButton.setEnabled(canRedo);

                switch (state) {
                    case DrawingView.ACTION_REDO:
                        drawingView2.redo();
                        break;
                    case DrawingView.ACTION_UNDO:
                        drawingView2.undo();
                        break;
                }

            }

        });

        drawingView.setInterceptTouchDelegate(new DrawingView.InterceptTouchDelegate() {
            @Override
            public void requireInterceptTouchEvent(DrawingView drawingView, boolean isIntercept) {

            }
        });
        drawingView.setDrawingStepDelegate(new DrawingView.DrawingStepDelegate() {
            @Override
            public void onDrawingStepBegin(DrawingView drawingView, DrawingStep step) {
                drawingView2.drawNextStep(step);

                Log.e("123123","onDrawingStepBegin="+step.toJson().toString());
            }

            @Override
            public void onDrawingStepChange(DrawingView drawingView, DrawingStep step) {
                drawingView2.drawNextStep(step);

                Log.e("123123","onDrawingStepChange="+step.toJson().toString());
            }

            @Override
            public void onDrawingStepEnd(DrawingView drawingView, DrawingStep step) {
                Log.e("123123","onDrawingStepEnd="+step.toJson().toString());
            }

            @Override
            public void onDrawingStepCancel(DrawingView drawingView, DrawingStep step) {
                Log.e("123123","onDrawingStepCancel="+step.toJson().toString());
            }
        });

        drawingView.setBackgroundDatasource(new DrawingView.BackgroundDatasource() {
            @Override
            public Drawable gainBackground(DrawingView drawingView, String identifier) {
                return null;
            }
        });

        undoButton = (Button) rootView.findViewById(R.id.undoButton);
        undoButton.setEnabled(false);
        undoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawingView.undo();
            }
        });

        redoButton = (Button) rootView.findViewById(R.id.redoButton);
        redoButton.setEnabled(false);
        redoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawingView.redo();
            }
        });

        clearButton = (Button) rootView.findViewById(R.id.clearButton);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawingView.clear();
                drawingView2.clear();
            }
        });

        penBrush = PenBrush.defaultBrush();
        drawingView.setBrush(penBrush);
        penButton = (Button) rootView.findViewById(R.id.penButton);
        penButton.setSelected(true);
        penButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectButton(singleSelectionButtons, penButton);
                drawingView.setBrush(penBrush);
            }
        });

        shapeBrushes.add(PolygonBrush.defaultBrush());
        shapeBrushes.add(LineBrush.defaultBrush());
        shapeBrushes.add(RectangleBrush.defaultBrush());
        shapeBrushes.add(RoundedRectangleBrush.defaultBrush());
        shapeBrushes.add(CircleBrush.defaultBrush());
        shapeBrushes.add(EllipseBrush.defaultBrush());
        shapeBrushes.add(RightAngledTriangleBrush.defaultBrush());
        shapeBrushes.add(IsoscelesTriangleBrush.defaultBrush());
        shapeBrushes.add(RhombusBrush.defaultBrush());
        shapeBrushes.add(CenterCircleBrush.defaultBrush());
        shapeButton = (Button) rootView.findViewById(R.id.shapeButton);
        shapeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawingBrush brush = null;

                if (!v.isSelected()) {
                    if (v.getTag() == null) {
                        v.setTag(1);
                    }
                    selectButton(singleSelectionButtons, (Button) v);
                } else {
                    int index = (int) v.getTag() + 1;
                    index = index % shapeBrushes.size();
                    v.setTag(index);
                }

                brush = shapeBrushes.get((Integer) v.getTag());
                drawingView.setBrush(brush);
                String name = brush.getClass().getSimpleName();
                name = name.substring(0, name.length() - 5);
                ((Button) v).setText(name);
            }
        });

        textBrush = TextBrush.defaultBrush().setTypefaceStyle(Typeface.ITALIC);
        textButton = (Button) rootView.findViewById(R.id.textButton);
        textButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectButton(singleSelectionButtons, textButton);
                drawingView.setBrush(textBrush);
            }
        });

        backgroundColorButton = (Button) rootView.findViewById(R.id.backgroundColorButton);
        backgroundColorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random random = new Random();
                int color = Color.argb(Math.abs(random.nextInt()) % 256, Math.abs(random.nextInt()) % 256, Math.abs(random.nextInt()) % 256, Math.abs(random.nextInt()) % 256);
                ((Button) v).setTextColor(color);
                drawingView.setBackgroundColor(0, color);
            }
        });

        thicknessButton = (Button) rootView.findViewById(R.id.thicknessButton);
        thicknessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getThicknessAdjustController().setThickness((int) penBrush.getSize());
                getThicknessAdjustController().popupFromView(v, BasePopupController.PopupDirection.Left, true, 0, 0);
            }
        });

        eraserButton = (Button) rootView.findViewById(R.id.eraserButton);
        eraserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setSelected(!v.isSelected());
                penBrush.setIsEraser(v.isSelected());
                for (DrawingBrush brush : shapeBrushes) {
                    brush.setIsEraser(v.isSelected());
                }
            }
        });

        colorButton = (Button) rootView.findViewById(R.id.colorButton);
        colorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random random = new Random();
                int color = Color.argb(Math.abs(random.nextInt()) % 256, Math.abs(random.nextInt()) % 256, Math.abs(random.nextInt()) % 256, Math.abs(random.nextInt()) % 256);
                ((Button) v).setTextColor(color);
                penBrush.setColor(color);
                textBrush.setColor(color);
                for (DrawingBrush brush : shapeBrushes) {
                    brush.setColor(color);
                }
            }
        });

        fillTypeButton = (Button) rootView.findViewById(R.id.fillTypeButton);
        fillTypeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setSelected(!v.isSelected());
                ShapeBrush.FillType fillType = v.isSelected() ? ShapeBrush.FillType.Solid : ShapeBrush.FillType.Hollow;
                fillTypeButton.setText(fillType.name());
                for (ShapeBrush brush : shapeBrushes) {
                    brush.setFillType(fillType);
                }
            }
        });

        edgeRoundedButton = (Button) rootView.findViewById(R.id.edgeRoundedButton);
        edgeRoundedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setSelected(!v.isSelected());
                for (ShapeBrush brush : shapeBrushes) {
                    brush.setEdgeRounded(v.isSelected());
                }
            }
        });

        oneStrokeOneLayerButton = (Button) rootView.findViewById(R.id.oneStrokeOneLayerButton);
        oneStrokeOneLayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setSelected(!v.isSelected());
                penBrush.setOneStrokeToLayer(v.isSelected());
                textBrush.setOneStrokeToLayer(v.isSelected());
                for (DrawingBrush brush : shapeBrushes) {
                    brush.setOneStrokeToLayer(v.isSelected());
                }
            }
        });

        deleteLayerButton = (Button) rootView.findViewById(R.id.deleteLayerButton);
        deleteLayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) drawingView.getLayoutParams();
//                if (small) {
//                    layoutParams.rightMargin = 0;
//                    layoutParams.bottomMargin = 0;
//                }
//                else {
//                    layoutParams.rightMargin = drawingView.getWidth() / 2;
//                    layoutParams.bottomMargin = drawingView.getHeight() / 2;
//                }
//                drawingView.setLayoutParams(layoutParams);
//                small = !small;
                boolean result = drawingView.deleteHandlingLayer();
                Log.e("123123", "result:" + result);
            }
        });

        singleSelectionButtons = new ArrayList<>();
        singleSelectionButtons.add(penButton);
        singleSelectionButtons.add(shapeButton);
        singleSelectionButtons.add(textButton);


        ratio = (Button) rootView.findViewById(R.id.ratio);
        ratio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getRadioAdjustController().setMax(100);
                getRadioAdjustController().setThickness((int) ((drawingView.getScaleX()-1)*100));
                getRadioAdjustController().popupFromView(v, BasePopupController.PopupDirection.Left, true, 0, 0);
            }
        });
        return rootView;
    }


    boolean small = false;

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /* #Accessors */
    public ThicknessAdjustController getThicknessAdjustController() {
        if (thicknessAdjustController == null) {
            thicknessAdjustController = new ThicknessAdjustController(getActivity());
            thicknessAdjustController.setThicknessDelegate(new ThicknessAdjustController.ThicknessDelegate() {
                @Override
                public void thicknessDidChangeFromThicknessAdjustController(ThicknessAdjustController controller, int thickness) {
                    penBrush.setSize(thickness);
                    textBrush.setSize(thickness);
                    for (DrawingBrush brush : shapeBrushes) {
                        brush.setSize(thickness);
                    }
                }
            });
        }
        return thicknessAdjustController;
    }

    /* #Accessors */
    public ThicknessAdjustController getRadioAdjustController() {
        if (radioAdjustController == null) {
            radioAdjustController = new ThicknessAdjustController(getActivity());
            radioAdjustController.setThicknessDelegate(new ThicknessAdjustController.ThicknessDelegate() {
                @Override
                public void thicknessDidChangeFromThicknessAdjustController(ThicknessAdjustController controller, int thickness) {
                    drawingView.setScale(1.0f + thickness/100f);
                }
            });
        }
        return radioAdjustController;
    }

    /* #Private Methods */
    private void selectButton(List<Button> buttons, Button button) {
        for (Button b : buttons) {
            b.setSelected(b == button);
        }
    }

}