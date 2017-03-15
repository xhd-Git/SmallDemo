package com.example.reol.opengles02.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by reol on 2017/3/15.
 */

public class FloatPoint {
    public float x;
    public float y;
    public float z;

    public FloatPoint(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static float[] toFloatArray(FloatPoint[] points){
        float[] floats = new float[]{};
        int count = 0;
        for (int i=0; i<points.length; i++){
            floats[count++] = points[i].x;
            floats[count++] = points[i].y;
            floats[count++] = points[i].z;
        }
        return floats;
    }
}
