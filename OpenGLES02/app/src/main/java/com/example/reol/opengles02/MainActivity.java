package com.example.reol.opengles02;

import android.content.res.Resources;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

import com.example.reol.opengles02.Model.Cube;
import com.example.reol.opengles02.Model.Triangle;
import com.example.reol.opengles02.Renders.CommonRender;
import com.example.reol.opengles02.Utils.Constant;
import com.example.reol.opengles02.Utils.ShaderUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.*;
import static android.opengl.GLU.*;
import static android.opengl.Matrix.*;


public class MainActivity extends AppCompatActivity {

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
//        Triangle triangle = new Triangle(Triangle.TRIANGLE_DEFAULT);
        Cube cube = new Cube();
//        vertexInfo = triangle.getVertexBuffer();
        vertexInfo = cube.getVertexBuffer();
        vertexShader = ShaderUtil.loadShaderCodeFromAssets(getApplicationContext(), Constant.VERTEX_SHADER_1);
        fragShader = ShaderUtil.loadShaderCodeFromAssets(getApplicationContext(), Constant.FRAG_SHADER_1);
        glSurfaceView.setEGLContextClientVersion(2);
        glSurfaceView.setRenderer(new CommonRender(vertexInfo,vertexShader,fragShader));
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
