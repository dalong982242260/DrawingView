package com.dl.drawingview;

import android.util.Log;

import com.dl.drawingview.lib.draw.brush.Brush;
import com.dl.drawingview.lib.draw.brush.drawing.PenBrush;
import com.dl.drawingview.lib.draw.helper.json.Json;
import com.dl.drawingview.lib.draw.model.DrawingLayer;
import com.dl.drawingview.lib.draw.model.DrawingPath;
import com.dl.drawingview.lib.draw.model.DrawingStep;

/**
 * @author zwl
 * @date on 2018/7/9
 */
public class TestDataUtils {
    public static String json = "{\"BR\":{\"OSTL\":false,\"C\":-16777216,\"E\":false,\"S\":15,\"VD_CN\":\"com.dl.drawingview.lib.draw.brush.drawing.PenBrush\"},\"C\":false,\"DL\":{\"BC\":-1,\"B\":261.82907,\"H\":0,\"LT\":1,\"L\":325.03723,\"R\":852.55383,\"RO\":-1,\"SC\":-1,\"T\":212.0915},\"DP\":{\"P\":[{\"M\":1531105140525,\"ID\":1,\"x\":332.55118,\"y\":219.19803},{\"M\":1531105140573,\"ID\":1,\"x\":339.27612,\"y\":224.12428},{\"M\":1531105140602,\"ID\":1,\"x\":361.47867,\"y\":235.16042},{\"M\":1531105140628,\"ID\":1,\"x\":374.14462,\"y\":238.89026},{\"M\":1531105140654,\"ID\":1,\"x\":409.59192,\"y\":246.45757},{\"M\":1531105140682,\"ID\":1,\"x\":443.91034,\"y\":250.42146},{\"M\":1531105140710,\"ID\":1,\"x\":465.38794,\"y\":251.17828},{\"M\":1531105140742,\"ID\":1,\"x\":511.84677,\"y\":248.77733},{\"M\":1531105140775,\"ID\":1,\"x\":568.58936,\"y\":242.44337},{\"M\":1531105140809,\"ID\":1,\"x\":629.9779,\"y\":238.51944},{\"M\":1531105140843,\"ID\":1,\"x\":689.8856,\"y\":239.5548},{\"M\":1531105140878,\"ID\":1,\"x\":746.2838,\"y\":244.43675},{\"M\":1531105140911,\"ID\":1,\"x\":792.3887,\"y\":247.44707},{\"M\":1531105140944,\"ID\":1,\"x\":826.013,\"y\":250.02713},{\"M\":1531105140979,\"ID\":1,\"x\":842.1925,\"y\":252.5108},{\"M\":1531105141005,\"ID\":1,\"x\":845.0399,\"y\":253.8433}]},\"DS\":{\"pointerState\":268435456},\"DVH\":538,\"DVW\":1184,\"R\":true,\"S\":1,\"SO\":true,\"ST\":1}";
    public static String json3 = "{\"P\":[{\"M\":1531105689510,\"ID\":1,\"x\":192.5998,\"y\":205.87292},{\"M\":1531105689582,\"ID\":1,\"x\":193.67377,\"y\":206.53918},{\"M\":1531105689609,\"ID\":1,\"x\":197.55215,\"y\":206.94412},{\"M\":1531105689637,\"ID\":1,\"x\":216.64653,\"y\":207.20543},{\"M\":1531105689666,\"ID\":1,\"x\":257.49875,\"y\":207.20543},{\"M\":1531105689695,\"ID\":1,\"x\":280.3975,\"y\":207.20543},{\"M\":1531105689723,\"ID\":1,\"x\":327.17307,\"y\":207.87169},{\"M\":1531105689753,\"ID\":1,\"x\":364.57764,\"y\":208.64334},{\"M\":1531105689785,\"ID\":1,\"x\":397.56583,\"y\":209.9013},{\"M\":1531105689817,\"ID\":1,\"x\":425.22675,\"y\":210.53671},{\"M\":1531105689848,\"ID\":1,\"x\":437.47534,\"y\":210.53671},{\"M\":1531105689879,\"ID\":1,\"x\":462.66342,\"y\":211.86922},{\"M\":1531105689913,\"ID\":1,\"x\":488.23218,\"y\":212.53548},{\"M\":1531105689947,\"ID\":1,\"x\":518.50055,\"y\":213.68755},{\"M\":1531105689980,\"ID\":1,\"x\":544.6481,\"y\":214.97195},{\"M\":1531105690014,\"ID\":1,\"x\":561.6707,\"y\":216.533},{\"M\":1531105690042,\"ID\":1,\"x\":567.1364,\"y\":216.533}]}";

    public static DrawingStep getDrawStepData() {
        DrawingStep drawingStep = new DrawingStep();
        drawingStep.setStepType(DrawingStep.StepType.DrawOnBase);//ST
        drawingStep.setDrawingViewWidth(0);//DVW
        drawingStep.setDrawingViewHeight(0);//DVH
        drawingStep.setDrawingState(new Brush.DrawingState(Brush.DrawingPointerState.TouchMoving));//DS
        drawingStep.setStepOver(false);//SO
        drawingStep.setStep(0);//S
        drawingStep.setRemote(false);//R
        PenBrush brush = new PenBrush();//BR
        brush.setColor(-1828288450);//C
        brush.setIsEraser(false);//E
        brush.setSize(15);//S
        brush.setOneStrokeToLayer(false);//OSTL
        drawingStep.setBrush(brush);
        DrawingPath drawingPath = (DrawingPath) new Json(DrawingPath.class).modelFromJsonString(json3);
        drawingStep.setDrawingPath(drawingPath);//DP

        DrawingLayer drawingLayer = new DrawingLayer();

        drawingLayer.setHierarchy(0);//H -层级
        drawingLayer.setLayerType(DrawingLayer.LayerType.BaseDrawing);//LT-图层类别
        drawingLayer.setBackgroundColor(-1);//bc-背景色
        drawingLayer.setLeft(184.87473f);//L-left坐标
        drawingLayer.setTop(198.53052f);//T-top坐标
        drawingLayer.setRight(574.86145f);//R-right坐标
        drawingLayer.setBottom(224.66054f);//B-bottom坐标
        drawingLayer.setScale(-1);//SC-图层缩放比例，xy同等
        drawingStep.setDrawingLayer(drawingLayer);


        Log.e("123123","drawingStep:"+drawingStep.toJson().toString());
        return drawingStep;
    }



    public static DrawingStep getDrawStepData2() {
        DrawingStep drawingStep = (DrawingStep) new Json(DrawingStep.class).modelFromJsonString(json);
        DrawingPath drawingPath = (DrawingPath) new Json(DrawingPath.class).modelFromJsonString(json3);
        drawingStep.setDrawingPath(drawingPath);//DP
        return drawingStep;
    }
}
