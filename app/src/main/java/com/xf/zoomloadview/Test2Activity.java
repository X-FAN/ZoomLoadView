package com.xf.zoomloadview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.xf.zoomloadview.widget.ZoomLoadView;

public class Test2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);
        ZoomLoadView zoomLoadView = (ZoomLoadView) findViewById(R.id.zoom_load_view);
        zoomLoadView.setEndListener(new ZoomLoadView.EndListener() {
            @Override
            public void onEndListener() {
                Log.d("ZoomLoadView", "ani end");
            }
        });
    }
}
