package com.dl.drawingview;

import android.content.Context;
import android.view.View;

public abstract class BaseController {
    final BaseController self = this;

    protected Context context;

    protected View rootView;

    public BaseController(Context context) {
        self.context = context;
    }

    public Context getContext() {
        return context;
    }

    public View getRootView() {
        return rootView;
    }

}