package com.example.reol.opengles02;

import android.content.res.Resources;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        glSurfaceView = new GLSurfaceView(getApplicationContext());

        glSurfaceView.setEGLContextClientVersion(2);
        glSurfaceView.setRenderer(new MyRenderer());
        glSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);

        setContentView(glSurfaceView);


    }

    class MyRenderer implements GLSurfaceView.Renderer {
//        private static final String VERTEX_SHADER = "attribute vec4 vPosition;\n"
//                + "void main() {\n"
//                + "  gl_Position = vPosition;\n"
//                + "}";
//        private static final String FRAGMENT_SHADER = "precision mediump float;\n"
//                + "void main() {\n"
//                + "  gl_FragColor = vec4(0.5,0,0,1);\n"
//                + "}";
        String VERTEX_SHADER = loadFromAssetsFile("vertex_shader.glsl",getResources());
        String FRAGMENT_SHADER = loadFromAssetsFile("fragment_shader.glsl",getResources());

        private final float[] VERTEX = {   // in counterclockwise order:
                0.0f, 0.5f, 0.0f, // top
                -0.5f, -0.5f, 0.0f, // bottom left
                0.5f, -0.5f, 0.0f,  // bottom right
        };

        private final FloatBuffer mVertexBuffer;

        private int mProgram;
        private int mPositionHandle;

        MyRenderer() {
            mVertexBuffer = ByteBuffer.allocateDirect(VERTEX.length * 4)
                    .order(ByteOrder.nativeOrder())
                    .asFloatBuffer()
                    .put(VERTEX);
            mVertexBuffer.position(0);
        }


        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {

        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
            mProgram = glCreateProgram();
            int vertexShader = loadShader(GL_VERTEX_SHADER,VERTEX_SHADER);
            int fragmentShader = loadShader(GL_FRAGMENT_SHADER,FRAGMENT_SHADER);
            glAttachShader(mProgram,vertexShader);
            glAttachShader(mProgram,fragmentShader);
            glLinkProgram(mProgram);

            mPositionHandle = glGetAttribLocation(mProgram,"vPosition");
        }

        @Override
        public void onDrawFrame(GL10 gl) {
            glClearColor(0.0f,1.0f,0.5f,1.0f);
            glClear(GL_DEPTH_BUFFER_BIT | GL_COLOR_BUFFER_BIT);
            glUseProgram(mProgram);

            glEnableVertexAttribArray(mPositionHandle);

            glVertexAttribPointer(mPositionHandle,3,GL_FLOAT,false,12,mVertexBuffer);
            glDrawArrays(GL_TRIANGLES,0,3);

            glDisableVertexAttribArray(mPositionHandle);
        }

        private int loadShader(int type, String shaderCode) {
            int shader = glCreateShader(type);
            glShaderSource(shader, shaderCode);
            glCompileShader(shader);
            return shader;
        }

    }


    public static String loadFromAssetsFile(String fname, Resources res){
        String result = null;

        try {
            InputStream in = res.getAssets().open(fname);
            int ch = 0;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            while ((ch = in.read()) != -1){
                baos.write(ch);
            }
            byte[] bytes = baos.toByteArray();

            result = new String(bytes,"utf-8");
//            result = result.replaceAll("\\r\\n","\n");

            baos.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
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
