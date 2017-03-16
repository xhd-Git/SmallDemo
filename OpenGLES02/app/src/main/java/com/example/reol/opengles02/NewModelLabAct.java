package com.example.reol.opengles02;

import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import com.example.reol.opengles02.Model.Triangle;
import com.example.reol.opengles02.Renders.TriangleRender;
import com.example.reol.opengles02.Utils.Constant;
import com.example.reol.opengles02.Utils.ShaderUtil;

import java.nio.FloatBuffer;


public class NewModelLabAct extends AppCompatActivity {

    GLSurfaceView glSurfaceView;

    private FloatBuffer vertexInfo;
    private String vertexShader;
    private String fragShader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        initView();
        setUpData();
    }

    private void initView() {
        glSurfaceView = new GLSurfaceView(getApplicationContext());
        setContentView(glSurfaceView);
    }

    private void setUpData() {
//        float[] floats = new float[]{
//                0.5f,0.5f,0,
//                0.5f,-0.5f,0,
//                -0.5f,-0.5f,0,
//                -0.5f,0.5f,0
//        };

//        vertexInfo = ByteBuffer.allocate(floats.length*4)
//                .order(ByteOrder.nativeOrder())
//                .asFloatBuffer()
//                .put(floats);

        vertexShader = ShaderUtil.loadShaderCodeFromAssets(getApplicationContext(), Constant.VERTEX_SHADER_1);
        fragShader = ShaderUtil.loadShaderCodeFromAssets(getApplicationContext(), Constant.FRAG_SHADER_1);
        glSurfaceView.setEGLContextClientVersion(2);
        glSurfaceView.setRenderer(new TriangleRender(vertexShader,fragShader));
        glSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }

    @Override
    protected void onPause() {
        super.onPause();
        glSurfaceView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        glSurfaceView.onResume();
    }
}
