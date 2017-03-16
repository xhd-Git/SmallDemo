package com.example.reol.opengles02.Utils;

import android.content.Context;

import com.example.reol.opengles02.Model.STLModel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**读取 stl 模型文件中的顶点位置信息
 * Created by reol on 2017/3/16.
 */

public class STLReader {
    private StlLoadListener stlLoadListener;

    public STLModel parseBinStlInSDcard(String path) throws IOException {
        File file = new File(path);
        InputStream is = new FileInputStream(file);

        return parseBinStl(is);
    }

    public STLModel parseBinStlInAssets(Context context, String fileName) throws IOException{
        InputStream is = context.getAssets().open(fileName);

        return parseBinStl(is);
    }

    private STLModel parseBinStl(InputStream is) throws IOException {


        STLModel model = new STLModel();
        is.skip(80);

        byte[] bytes = new byte[4];
        is.read(bytes);
        int facetCount = BufferUtil.bytes4ToInt(bytes,0);//三角形个数

        model.setFacetCount(facetCount);

        if (facetCount == 0){
            is.close();
            return model;
        }

        byte[] facetBytes = new byte[facetCount * 50];//每个三角形共计50bytes
        is.read(facetBytes);
        is.close();

        parseModel(model,facetBytes);

        return model;
    }

    private void parseModel(STLModel model, byte[] facetBytes) {
        int facetCount = model.getFacetCount();

        float[] verts = new float[facetCount * 3 * 3];

        float[] vnorms = new float[facetCount * 3 * 3];

        short[] remarks = new short[facetCount];

        int stlOffset = 0;//使用offset偏置挨个读取三角形信息


        // TODO: 02/02/2017 具体的verts，vnorms，remarks数值解析

        try {
            for (int i = 0; i < facetCount; i++) {
                if (stlLoadListener != null) {
                    stlLoadListener.onLoading(i, facetCount);
                }
                for (int j = 0; j < 4; j++) {
                    float x = BufferUtil.byte4ToFloat(facetBytes, stlOffset);
                    float y = BufferUtil.byte4ToFloat(facetBytes, stlOffset + 4);
                    float z = BufferUtil.byte4ToFloat(facetBytes, stlOffset + 8);
                    stlOffset += 12;

                    if (j == 0) {//法向量
                        vnorms[i * 9] = x;
                        vnorms[i * 9 + 1] = y;
                        vnorms[i * 9 + 2] = z;
                        vnorms[i * 9 + 3] = x;
                        vnorms[i * 9 + 4] = y;
                        vnorms[i * 9 + 5] = z;
                        vnorms[i * 9 + 6] = x;
                        vnorms[i * 9 + 7] = y;
                        vnorms[i * 9 + 8] = z;
                    } else {//三个顶点
                        verts[i * 9 + (j - 1) * 3] = x;
                        verts[i * 9 + (j - 1) * 3 + 1] = y;
                        verts[i * 9 + (j - 1) * 3 + 2] = z;

                        //记录模型中三个坐标轴方向的最大最小值
                        if (i == 0 && j == 1) {
                            model.minX = model.maxX = x;
                            model.minY = model.maxY = y;
                            model.minZ = model.maxZ = z;
                        } else {
                            model.minX = Math.min(model.minX, x);
                            model.minY = Math.min(model.minY, y);
                            model.minZ = Math.min(model.minZ, z);
                            model.maxX = Math.max(model.maxX, x);
                            model.maxY = Math.max(model.maxY, y);
                            model.maxZ = Math.max(model.maxZ, z);
                        }
                    }
                }
                short r = BufferUtil.byte2ToShort(facetBytes, stlOffset);
                stlOffset = stlOffset + 2;
                remarks[i] = r;
            }
        } catch (Exception e) {
            if (stlLoadListener != null) {
                stlLoadListener.onFailure(e);
            } else {
                e.printStackTrace();
            }
        }
        model.setVerts(verts);
        model.setVnorms(vnorms);
        model.setRemarks(remarks);
    }

    public static interface StlLoadListener{

        void onStart();
        void onLoading(int cur,int total);
        void onFinish();
        void onFailure(Exception e);

    }
}
