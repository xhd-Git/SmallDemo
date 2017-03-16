package com.example.reol.opengles02.Renders;

import android.content.Context;
import android.opengl.GLSurfaceView;

import com.example.reol.opengles02.Model.Point3;
import com.example.reol.opengles02.Model.STLModel;
import com.example.reol.opengles02.Utils.BufferUtil;
import com.example.reol.opengles02.Utils.STLReader;

import java.io.IOException;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.*;
import static android.opengl.GLU.*;
import static android.opengl.Matrix.*;

/**stl 专用Render
 * Created by reol on 2017/3/16.
 */

public class STLRender implements GLSurfaceView.Renderer {
    private STLModel model;
    private Point3 mCenterPoint;
    private Point3 eye = new Point3(-3,0,0);
    private Point3 up  = new Point3(0,0,1);
    private Point3 center = new Point3(0,0,0);

    private float mDegree = 0;
    private float mScalef = 1;


    public STLRender(Context context) {
        try {
            model = new STLReader().parseBinStlInAssets(context,"stl_sample2.stl");

        } catch (IOException e) {
            e.printStackTrace();

        }

    }

    public void rotate(float degree){
        mDegree = degree;
    }

    @Override
    public void onDrawFrame(GL10 gl) {

        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

        gl.glLoadIdentity();

        gluLookAt(gl,eye.x,eye.y,eye.z,center.x,center.y,center.z,up.x,up.y,up.z);
        gl.glRotatef(mDegree,0,0,1);


        gl.glScalef(mScalef,mScalef,mScalef);
        gl.glTranslatef(-mCenterPoint.x,-mCenterPoint.y,-mCenterPoint.z);

        //--------------begin-------------------

        gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);

        gl.glNormalPointer(GL10.GL_FLOAT,0,model.getVnormBuffer());
        gl.glVertexPointer(3,GL10.GL_FLOAT,0,model.getVertsBuffer());
        gl.glDrawArrays(GL10.GL_TRIANGLES,0,model.getFacetCount()*3);

        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);

        //---------------end--------------------

    }


    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {

        gl.glViewport(0,0,width,height);
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();

        gluPerspective(gl,45.0f,(float) width/height,1f,100f);

        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();

    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {

        gl.glEnable(GL10.GL_DEPTH_TEST);
        gl.glClearDepthf(1.0f);
        gl.glDepthFunc(GL10.GL_LEQUAL);
        gl.glShadeModel(GL10.GL_SMOOTH);//初始化

        openLight(gl);
//        enableMaterial(gl);

        float r = model.getMaxR();//控制缩放

        mScalef = 1.0f/r;
        mCenterPoint = model.getCenterPoint();



    }

    private float[] materialAmb = {0.4f, 0.4f, 1.0f, 1.0f};
    private float[] materialDiff = {0.0f, 0.0f, 1.0f, 1.0f};
    private float[] materialSpec = {1.0f, 0.5f, 0.0f, 1.0f};

    private void enableMaterial(GL10 gl) {

        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK,GL10.GL_AMBIENT, BufferUtil.float2buffer(materialAmb));
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK,GL10.GL_DIFFUSE,BufferUtil.float2buffer(materialDiff));
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK,GL10.GL_SPECULAR,BufferUtil.float2buffer(materialSpec));
    }


    private float[] ambient = {0.3f, 0.3f, 0.3f, 1.0f};//环境光
    private float[] diffuse = {0.3f, 0.5f, 0.8f, 1.0f};//漫反射
    private float[] specular = {0f, 0.5f, 1.0f, 1.0f};//镜面反射（高光）
    private float[] lightPosition = {0.5f,0.5f,0.5f,0.0f};//光源位置

    private void openLight(GL10 gl) {

        gl.glEnable(GL10.GL_LIGHTING);
        gl.glEnable(GL10.GL_LIGHT0);
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_AMBIENT, ambient, 0);
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_DIFFUSE, diffuse, 0);
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_SPECULAR, specular, 0);
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, new float[] { 1.0f, 1.0f, 100f, 1f }, 0);


//        gl.glLightfv(GL10.GL_LIGHT1, GL10.GL_AMBIENT, BufferUtil.float2buffer(ambient));
//        gl.glLightfv(GL10.GL_LIGHT1, GL10.GL_DIFFUSE, BufferUtil.float2buffer(diffuse));
//        gl.glLightfv(GL10.GL_LIGHT1, GL10.GL_SPECULAR, BufferUtil.float2buffer(specular));
//        gl.glLightfv(GL10.GL_LIGHT1, GL10.GL_POSITION, BufferUtil.float2buffer(lightPosition));
//
//        gl.glEnable(GL10.GL_LIGHT1);

    }
}
