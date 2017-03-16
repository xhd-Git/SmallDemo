package com.example.reol.opengles02.Renders;

import android.opengl.GLSurfaceView;

import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.*;
import static android.opengl.GLU.*;
import static android.opengl.Matrix.*;

/**尽可能普通的通用Render
 * Created by reol on 2017/3/15.
 */

public class TriangleRender implements GLSurfaceView.Renderer {

    private FloatBuffer mVertexBuffer;
    private String mVertexShader;
    private String mFragShader;

    private int mProgram;
    private int mPositionHandle;
    private int mColorHandle;

    public TriangleRender(String vertexShader, String fragShader) {
        mVertexShader = vertexShader;
        mFragShader = fragShader;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {

    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        mProgram = glCreateProgram();
        int vertexShader = loadShader(GL_VERTEX_SHADER, mVertexShader);
        int fragmentShader = loadShader(GL_FRAGMENT_SHADER, mFragShader);
        glAttachShader(mProgram,vertexShader);
        glAttachShader(mProgram,fragmentShader);
        glLinkProgram(mProgram);

        mPositionHandle = glGetAttribLocation(mProgram,"vPosition");
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        glClearColor(0.0f,0.0f,0.0f,1.0f);
        glClear(GL_DEPTH_BUFFER_BIT | GL_COLOR_BUFFER_BIT);
        glUseProgram(mProgram);

        glEnableVertexAttribArray(mPositionHandle);
        glVertexAttribPointer(mPositionHandle,3,GL_FLOAT,false,12,mVertexBuffer);

        glDrawArrays(GL_TRIANGLE_STRIP,0,3);

        glDisableVertexAttribArray(mPositionHandle);
    }

    private int loadShader(int type, String shaderCode) {
        int shader = glCreateShader(type);
        glShaderSource(shader, shaderCode);
        glCompileShader(shader);
        return shader;
    }
}
