package com.example.reol.opengles02;

import android.opengl.GLSurfaceView;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.reol.opengles02.Renders.STLRender;

public class StlDisplayAct extends AppCompatActivity {
    private GLSurfaceView glView;
    private STLRender render;

    private float rotateDegree = 0;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            rotate(rotateDegree);
        }
    };

    private void rotate(float rotateDegree) {
        render.rotate(rotateDegree);
        glView.invalidate();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        glView = new GLSurfaceView(this);
        render = new STLRender(this);
//        glView.setEGLContextClientVersion(2);
        glView.setRenderer(render);

        setContentView(glView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (glView != null){
            glView.onResume();
        }

        new Thread(){
            @Override
            public void run() {

                while (true){
                    try {
                        sleep(100);

                        rotateDegree += 5;//一直增加迟早会出事啊
                        handler.sendEmptyMessage(0x001);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (glView != null){
            glView.onPause();
        }
    }
}
