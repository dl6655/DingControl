package widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.dlingli.dingcontrol.R;


/**
 * Created by dingli on 16-10-11.
 */
public class CirclePressView extends SurfaceView implements SurfaceHolder.Callback,  Runnable {
    private SurfaceHolder mHolder;
    private Canvas mCanvas;
    private Boolean isRunning;
    //绘制线条的画笔
    private Paint circleRedPaint,circleWhitePaint;
    private float startCircleStrokeWidth,endCircleStrokeWidth;
    //定义宽度和中心宽度
    int mWidth;
    int mHeight;
    int centerWidth;
    int centerHeight;
    private volatile float mRedCircleStep=8,deSpeed=10;
    private volatile float mSpeedArcStep =20f,startArcAngle,endArcAngle,startRaduis,endRaduisRedCircle;


    private boolean isIncrement = false;

    private float maxValueRedCircleX;
    private float minValueRedCircleX ;
    /**
     * 用户绘制的线程
     */
    private Thread mThread;
    int incrementTime = 10;
    int decrementTime = 10;

    private RectF circleRectF;

    public enum ClickType {
        CLICK_LONG,
        CLICK_SHORT,
    }
    public enum DrawType {
        DRAW_ARC,
        DRAW_CIRCLE
    }

    private ClickType clickType;
    private DrawType drawType;

    public CirclePressView(Context context) {
        super(context);
    }

    public CirclePressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mHolder = getHolder();
        mHolder.addCallback(this);


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth=getMeasuredWidth();
        mHeight=getMeasuredHeight();

        centerWidth=mWidth/2;
        centerHeight=mHeight/2;


    }

    public CirclePressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    private void initValues(){
        startCircleStrokeWidth = 3;
        endCircleStrokeWidth=3;

        maxValueRedCircleX = centerWidth*0.5f;
        minValueRedCircleX = 0;

        startRaduis=centerWidth/4;
        endRaduisRedCircle=startRaduis;

        startArcAngle=0;
        endArcAngle=0;

        circleRectF = new RectF(centerWidth - startRaduis*0.7f,
                centerHeight-startRaduis*0.7f,
                centerWidth+startRaduis*0.7f,
                centerHeight+startRaduis*0.7f);
    }
    private void init(){
        //white paint
        circleWhitePaint = new Paint();
        circleWhitePaint.setAntiAlias(true);
        circleWhitePaint.setColor(getResources().getColor(android.R.color.white));
        circleWhitePaint.setStyle(Paint.Style.FILL);
        circleWhitePaint.setStrokeWidth(startCircleStrokeWidth);

        //red paint
        circleRedPaint = new Paint();
        circleRedPaint.setAntiAlias(true);
        circleRedPaint.setColor(getResources().getColor(R.color.red));
        circleRedPaint.setStyle(Paint.Style.STROKE);
        circleRedPaint.setStrokeWidth(startCircleStrokeWidth);


        mThread = new Thread(this);
        isRunning = true;
    }
    private void initValuesChange(){

        endCircleStrokeWidth = 3;
        endRaduisRedCircle=startRaduis;

        endArcAngle=0;

    }
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
           initValues();
           init();
           initValuesChange();
           drawWhiteCircleBg();

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isRunning = false;
    }

    @Override
    public void run() {
        while (isRunning) {
                drawArcCircleBg();
            try {
                if (isIncrement) {
                    Thread.sleep(incrementTime);
                } else {
                    Thread.sleep(decrementTime);
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }


    public void drawArcCircleBg() {
        try {
               mCanvas = mHolder.lockCanvas();
            if (mCanvas != null) {
                mCanvas.drawColor(Color.BLACK);

                if (clickType.equals(ClickType.CLICK_LONG)) {
                    if(drawType==DrawType.DRAW_ARC){
                        setAngleValues();
                    }else if(drawType==DrawType.DRAW_CIRCLE){
                        setRadiusValues();
                    }

                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (mCanvas != null) {
                mHolder.unlockCanvasAndPost(mCanvas);
            }
        }


    }
    public void drawWhiteCircleBg() {

        try {
               mCanvas = mHolder.lockCanvas();
            if (mCanvas != null) {
                mCanvas.drawColor(Color.BLACK);
                drawWhiteCircleFill();

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (mCanvas != null) {
                mHolder.unlockCanvasAndPost(mCanvas);
            }
        }


    }


    public void drawWhiteCircleFill() {
        initValuesChange();
        circleWhitePaint.setStrokeWidth(startCircleStrokeWidth);
        mCanvas.drawCircle(centerWidth, centerHeight, startRaduis * 0.7f, circleWhitePaint);
    }


    public void drawRedArc() {
        circleRedPaint.setStrokeWidth(5);
        mCanvas.drawArc(circleRectF, startArcAngle, endArcAngle, false, circleRedPaint);
    }
    public void drawRedCircleFill() {

        circleRedPaint.setStrokeWidth(endCircleStrokeWidth);
        mCanvas.drawCircle(centerWidth, centerHeight, endRaduisRedCircle, circleRedPaint);

    }

    private void setAngleValues() {
        drawRedArc();
        if (isIncrement) {
            incrementAngle();
        } else {
            decrementAngle();
        }
        if (endArcAngle > 360) {
            drawType=DrawType.DRAW_CIRCLE;
        } else if (endArcAngle < 0) {
            isIncrement = true;
            isRunning = false;
        }

    }
    private void setRadiusValues() {
//        Log.i("AAAD", "setRadiusValues endRaduisRedCircle=" + endRaduisRedCircle + ",startRaduis="+startRaduis+
//                ",startCircleStrokeWidth=" + startCircleStrokeWidth +
//                ",endCircleStrokeWidth="+endCircleStrokeWidth+  ",maxValueRedCircleX=" + maxValueRedCircleX
//       );

        if(isIncrement){
            if(endCircleStrokeWidth+1.6*mRedCircleStep>maxValueRedCircleX){
                endCircleStrokeWidth=maxValueRedCircleX;
            }

        }else{
            if(endCircleStrokeWidth-1.6*mRedCircleStep<minValueRedCircleX){
                endRaduisRedCircle=0;
            }

        }
        drawRedCircleFill();
        if (isIncrement) {
            incrementRadius();
        } else {
            decrementRadius();
        }
        if (endCircleStrokeWidth > maxValueRedCircleX) {
            isIncrement = false;
            isRunning = false;
        } else if (endCircleStrokeWidth < minValueRedCircleX) {
            isIncrement = true;
            isRunning = false;
            drawWhiteCircleFill();
        }

    }
    private void incrementRadius() {
        endRaduisRedCircle+=mRedCircleStep;
        endCircleStrokeWidth += 1.6*mRedCircleStep;

    }
    private void decrementRadius() {
        endRaduisRedCircle-=mRedCircleStep;
        endCircleStrokeWidth -=1.6*mRedCircleStep;
    }

    private void incrementAngle() {
        endArcAngle+=mSpeedArcStep;

    }

    private void decrementAngle() {
        endArcAngle-=mSpeedArcStep;
    }


    public void click() {
//Log.i("AAAD","click");
//        invalidate();
//        clickType = ClickType.CLICK_SHORT;
//        drawCircleBg();
//        startGreenToRedThread();
    }

    public void clickLong() {
        clickType = ClickType.CLICK_LONG;
        drawType=DrawType.DRAW_ARC;
        isRunning = true;
        isIncrement = true;
        initValuesChange();

        startUIThread();
    }

    public void cancleLongclick() {
        if (clickType == ClickType.CLICK_SHORT) {
            return;
        }
        clickType = ClickType.CLICK_LONG;
        drawType=DrawType.DRAW_CIRCLE;
        isRunning = true;
        isIncrement = false;
        startUIThread();
    }


    private void startUIThread() {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                mThread.run();
            }
        });
    }
}
