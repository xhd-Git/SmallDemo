package com.example.reol.mycustomviews.ChangeSystemView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.Button;

/**效果：模仿Android5.0水波纹扩散 点击效果
 * Created by reol on 16-9-6.
 */
public class RippleButton extends Button {
    private static final int INVALIDATE_DURATION = 20; 
    private static int DIFFUSE_GAP = 50;

    private int viewWidth;
    private int viewHeight;
    private float posX;
    private float posY;
    private float maxRadius;
    private float radius = 0;

    private boolean flagDown = false;
    private long downTime = 0;
    private int TAP_TIMEOUT;


    private Paint ripplePaint;


    public RippleButton(Context context) {
        super(context);
        initPaint();
        TAP_TIMEOUT = ViewConfiguration.getLongPressTimeout();
    }


    public RippleButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
        TAP_TIMEOUT = ViewConfiguration.getLongPressTimeout();
    }

    public RippleButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
        TAP_TIMEOUT = ViewConfiguration.getLongPressTimeout();
    }

    private void initPaint() {
        ripplePaint = new Paint();
        ripplePaint.setColor(Color.BLACK);
        ripplePaint.setAlpha(50);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.viewWidth = w;
        this.viewHeight = h;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if (downTime == 0){
                    downTime = SystemClock.elapsedRealtime();
                }
                posX = event.getX();
                posY = event.getY();
                flagDown = true;
                DIFFUSE_GAP = 20;

                getMaxRadius();
                postInvalidateDelayed(INVALIDATE_DURATION);

                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if(SystemClock.elapsedRealtime() - downTime < TAP_TIMEOUT){
                    DIFFUSE_GAP = 50;
                    postInvalidate();
                }else{
                    clearData();
                }
                break;
        }
        return super.onTouchEvent(event);
    }



    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (!flagDown){
            return;
        }

        canvas.save();
        canvas.clipRect(0,0,viewWidth,viewHeight);
        canvas.drawCircle(posX,posY,radius,ripplePaint);
        canvas.restore();

        if (radius < maxRadius){
            radius+=DIFFUSE_GAP;
            postInvalidateDelayed(INVALIDATE_DURATION);
        }else{
            clearData();
        }
    }

    private void clearData() {
        flagDown = false;
        downTime = 0;
        radius = 0;
        maxRadius = 0;
        postInvalidate();
    }

    private void getMaxRadius() {
        float x,y;
        x = posX;
        y = posY;
        if (posX<viewWidth/2){
            x = viewWidth - posX;
        }
        if (posY<viewHeight/2){
            y = viewHeight -posY;
        }
        maxRadius = (float) (Math.sqrt(x*x+y*y)+30);
    }
}
