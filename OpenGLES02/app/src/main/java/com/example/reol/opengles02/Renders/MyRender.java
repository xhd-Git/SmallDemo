package com.example.reol.opengles02.Renders;

import java.nio.FloatBuffer;

/**
 * Created by reol on 2017/3/15.
 */

public class MyRender extends CommonRender {

    private FloatBuffer mVertexBuffer;
    private String mVertexShader;
    private String mFragShader;

    private int mProgram;
    private int mPositionHandle;

    public MyRender(FloatBuffer vertexBuffer, String vertexShader, String fragShader) {
        super(vertexBuffer, vertexShader, fragShader);
    }


}
