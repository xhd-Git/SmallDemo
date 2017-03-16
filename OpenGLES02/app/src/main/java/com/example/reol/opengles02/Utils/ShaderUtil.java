package com.example.reol.opengles02.Utils;

import android.content.Context;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**Shader相关处理工具
 * Created by reol on 2017/3/15.
 */

public class ShaderUtil {

    public static String loadShaderCodeFromAssets(Context context, String fileName){
        String result = null;
        try {
            InputStream in = context.getResources().getAssets().open(fileName);
            int ch;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            while ((ch = in.read()) != -1){
                baos.write(ch);
            }
            byte[] bytes = baos.toByteArray();
            result = new String(bytes,"utf-8");
            baos.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


}
