package com.example.reol.opengles02.Model;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**测试图形 2维 三角形
 * Created by reol on 2017/3/15.
 */

public class Triangle {
    private float[] vertex;
    public static float[] TRIANGLE_DEFAULT = {   // in counterclockwise order
            0.0f, 0.5f, 0.0f, // top
            -0.5f, -0.5f, 0.0f, // bottom left
            0.5f, -0.5f, 0.0f,  // bottom right
    };

    public Triangle() {
    }

    public Triangle(float[] vertex) {
        this.vertex = vertex;
    }

    public Triangle(float x1, float x2, float x3, float y1, float y2, float y3, float z1,
                    float z2, float z3){
        this.vertex = new float[]{x1,x2,x3, y1,y2,y3, z1,z2,z3};
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
