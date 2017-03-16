package com.example.reol.opengles02.Model;



import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**正方体
 * Created by reol on 2017/3/15.
 */

public class Cube {
    private float[] vertex;

    public Cube() {
        FloatPoint point1 = new FloatPoint(0.5f,0.5f,0.5f);
        FloatPoint point2 = new FloatPoint(0.5f,0.5f,-0.5f);
        FloatPoint point3 = new FloatPoint(-0.5f,0.5f,-0.5f);
        FloatPoint point4 = new FloatPoint(-0.5f,0.5f,0.5f);
        FloatPoint point5 = new FloatPoint(-0.5f,-0.5f,0.5f);
        FloatPoint point6 = new FloatPoint(0.5f,-0.5f,0.5f);
        FloatPoint point7 = new FloatPoint(0.5f,-0.5f,-0.5f);
        FloatPoint point8 = new FloatPoint(-0.5f,-0.5f,-0.5f);

        FloatPoint[] points = new FloatPoint[]{
                point1,point2,point4,point3,
                point8,point4,point5,
                point1,point6,
                point2,point7,
                point3,point8,
                point7,point5,point6
        };

        vertex = FloatPoint.toFloatArray(points);
    }

    public FloatBuffer getVertexBuffer(){
        FloatBuffer buffer;
        buffer = ByteBuffer.allocateDirect(vertex.length * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(vertex);
        buffer.position(0);
        return buffer;
    }
}
