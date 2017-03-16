package com.example.reol.opengles02.Model;

import com.example.reol.opengles02.Utils.BufferUtil;

import java.nio.FloatBuffer;

/**stl模型文件 model
 * Created by reol on 2017/3/16.
 */

public class STLModel {

    private int facetCount;//三角面数量
    private float[] verts;//顶点数组
    private float[] vnorms;//面 法向量数组
    private short[] remarks;//三角面属性

    private FloatBuffer vertsBuffer;//顶点转换为buffer
    private FloatBuffer vnormBuffer;

    //三个方向的极值
    public float maxX;
    public float maxY;
    public float maxZ;
    public float minX;
    public float minY;
    public float minZ;

    //获取模型中心点
    public Point3 getCenterPoint(){
        float cx = minX+(maxX-minX)/2;
        float cy = minY+(maxY-minY)/2;
        float cz = minZ+(maxZ-minZ)/2;

        return new Point3(cx,cy,cz);
    }

    //获取最大半径
    public float getMaxR(){
        float dx = maxX - minX;
        float dy = maxY - minY;
        float dz = maxZ - minZ;

        float maxR = dx;
        if (dy>dx && dy>dz) maxR = dy;
        if (dz>dx && dz>dy) maxR = dz;

        return maxR;
    }

    //同时设置Buffer

    public void setVerts(float[] verts){
        this.verts = verts;

    }

    public void setVnorms(float[] vnorms){
        this.vnorms = vnorms;

    }

    public FloatBuffer getVertsBuffer() {
        vertsBuffer = BufferUtil.float2buffer(verts);
        return vertsBuffer;
    }

    public FloatBuffer getVnormBuffer() {
        vnormBuffer = BufferUtil.float2buffer(vnorms);
        return vnormBuffer;
    }

    public int getFacetCount() {
        return facetCount;
    }

    public void setFacetCount(int facetCount) {
        this.facetCount = facetCount;
    }

    public float[] getVerts() {
        return verts;
    }

    public float[] getVnorms() {
        return vnorms;
    }

    public short[] getRemarks() {
        return remarks;
    }

    public void setRemarks(short[] remarks) {
        this.remarks = remarks;
    }
}
