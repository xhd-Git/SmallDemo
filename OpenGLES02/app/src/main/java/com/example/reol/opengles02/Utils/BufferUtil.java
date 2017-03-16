package com.example.reol.opengles02.Utils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**buffer 转换
 * Created by reol on 2017/3/16.
 */

public class BufferUtil {

    public static FloatBuffer float2buffer(float[] floats){
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(floats.length * 4);
        byteBuffer.order(ByteOrder.nativeOrder());
        FloatBuffer floatBuffer = byteBuffer.asFloatBuffer();
        floatBuffer.put(floats);
        floatBuffer.position(0);

        return floatBuffer;
    }

    public static int bytes4ToInt(byte[] bytes,int offset){
        int b3 = bytes[offset + 3] & 0xff;
        int b2 = bytes[offset + 2] & 0xff;
        int b1 = bytes[offset + 1] & 0xff;
        int b0 = bytes[offset + 0] & 0xff;

        return (b3<<24) | (b2<<16) | (b1<<8) | b0;
    }

    public static short byte2ToShort(byte[] bytes, int offset) {
        int b1 = bytes[offset + 1] & 0xff;
        int b0 = bytes[offset + 0] & 0xff;
        return (short) ((b1 << 8) | b0);
    }

    public static float byte4ToFloat(byte[] bytes, int offset) {

        return Float.intBitsToFloat(bytes4ToInt(bytes, offset));
    }
}
