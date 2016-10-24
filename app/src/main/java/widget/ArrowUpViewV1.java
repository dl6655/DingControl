package widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.dlingli.dingcontrol.R;


/**
 * Created by dingli on 16-10-11.
 */
public class ArrowUpViewV1 extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    private SurfaceHolder mHolder;
    private Canvas mCanvas;
    private int startX = 0, startY = 0, endX = 0, endY = 0, endOutX = 30, endOutY = 30;
    private Boolean isRunning;
    //绘制线条的画笔
    private Paint linePaint;
    private float inLineWidth=6;
    private float outLineWidth=30;
    private Paint outLinePaint;
    //定义一个盘快的范围
    private RectF mRange = new RectF();
    //定义温度计的宽度和中心宽度
    int mWith;
    int mHeight;
    int centerWidth;
    int centerHeight;
    //定义一下水银的宽度
    private volatile float mSpeed =10;

    private float minValueX = (float) 50;
    private int lineLong = 150;
    private boolean isIncrement = false;

    float maxValueX = 200;
    /**
     * 用户绘制的线程
     */
    private Thread mThread;
    int incrementTime = 100;
    int decrementTime = 10;

    public ArrowUpViewV1(Context context) {
        super(context);
    }

    public ArrowUpViewV1(Context context, AttributeSet attrs) {
        super(context, attrs);
        mHolder = getHolder();
        mHolder.addCallback(this);
    }

    @Override
    protected void onMeasure(int width, int height) {
        int height1 = height;
        super.onMeasure(width, height1);

        int widthMode = MeasureSpec.getMode(width);
        int widthSize = MeasureSpec.getSize(width);
        int heightMode = MeasureSpec.getMode(height);
        int heightSize = MeasureSpec.getSize(height);


        this.mWith = getMeasuredWidth()/2;
        this.mHeight = getMeasuredHeight()/2;

        width = widthSize;
        float scaleX = widthSize / 1080;
        float scaleY = heightSize / 1920;
//        scale = Math.max(scaleX, scaleY);
        //控件的高度
        //        height = 185;
//        height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 62, getResources().getDisplayMetrics());

        //这里先把中心设置在屏幕的中心
        this.centerWidth = mWith / 2;
        this.centerHeight = mHeight / 2;
//        setMeasuredDimension(mWith, mHeight);

        maxValueX = centerWidth + lineLong-20;
        minValueX = centerWidth;

        startX = centerWidth;
        startY=centerHeight;

        endX = startX;
        endOutX = startX;
        endY=startY;
        //设置水银的宽度,暂时设置为总宽度的十五分之一
//        outLineWidth = mWith / 19;



    }

    public ArrowUpViewV1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

//    @Override
//    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
//
//        Paint paint=new Paint();
//        paint.setColor(getResources().getColor(R.color.red));
//        paint.setAntiAlias(true);
//        paint.setStyle(Paint.Style.FILL);
//
//
//        RectF rectF=new RectF(20,25,25,30);
//        canvas.drawRect(rectF,paint);
//    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        //初始化画笔
        linePaint = new Paint();
        //去锯齿
        linePaint.setAntiAlias(true);
        linePaint.setColor(getResources().getColor(R.color.green_order));
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth(inLineWidth);
        //初始化画笔
        outLinePaint = new Paint();
        //去锯齿
        outLinePaint.setAntiAlias(true);
        outLinePaint.setColor(getResources().getColor(R.color.red));
        outLinePaint.setStyle(Paint.Style.STROKE);
        outLinePaint.setStrokeWidth(outLineWidth);

        //初始化温度计的范围
        mRange = new RectF(0, 0, mWith, mHeight);
        mThread = new Thread(this);
        mThread = new DrawThread();
        mThread.start();
        isRunning = true;

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
        //不断进行绘制
        while (isRunning) {
            long start = System.currentTimeMillis();
            draw();
            long end = System.currentTimeMillis();
//            if (end - start < everySecondTime) {
            //这里控制一下，一秒绘制二十次。也就是五十秒绘制一次
            try {
                if (isIncrement) {
                    Thread.sleep(incrementTime);
                } else {
                    Thread.sleep(decrementTime);
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//            }
        }
    }

    private void draw() {
        try {
            mCanvas = mHolder.lockCanvas();
            //这里要判断是不是为空，之因为在用户点击了home以后，可能已经执行到这里
            if (mCanvas != null) {
                //这里是开始绘制自己要的东西
                //先绘制背景,
                drawBgArrow();

                //绘制水银的高度还有，显示体温
                drawShowRectangle();
            }
        } catch (Exception e) {
            // e.printStackTrace();这里的异常不处理，
        } finally {
            if (mCanvas != null) {
                mHolder.unlockCanvasAndPost(mCanvas);
            }
        }
    }

    public void drawBg() {

        try {
            mCanvas = mHolder.lockCanvas();
            //这里要判断是不是为空，之因为在用户点击了home以后，可能已经执行到这里
            if (mCanvas != null) {
                mCanvas.drawColor(Color.WHITE);
                mCanvas.drawLine(startX, startY, 220, 220, linePaint);
            }
        } catch (Exception e) {
            // e.printStackTrace();这里的异常不处理，
        } finally {
            if (mCanvas != null) {
                mHolder.unlockCanvasAndPost(mCanvas);
            }
        }


    }

    public void drawBgArrow() {
        mCanvas.drawColor(Color.WHITE);
        mCanvas.drawLine(startX, startY, startX +lineLong, startY + lineLong, linePaint);
        mCanvas.drawLine(startX+2.1f, startY-2.1f, startX -lineLong, startY + lineLong, linePaint);

    }

    public void drawShowRectangle() {
        if (isIncrement) {
            increment();
        } else {
            decrement();
        }

        if (endX > maxValueX) {
            isIncrement = false;
            isRunning = false;
        } else if (endX < minValueX) {
            isIncrement = true;
            isRunning = false;
        }
        if(startX<endX){
            mCanvas.drawLine(startX, startY, endX, endY, outLinePaint);

            mCanvas.drawLine(startX+outLineWidth/2.857f, startY-outLineWidth/2.857f, endOutX, endY, outLinePaint);
        }

    }

    public void increment() {
        endX += mSpeed;
        endY += mSpeed;
        endOutX-=mSpeed;


    }

    public void decrement() {
        endX -= mSpeed;
        endY -= mSpeed;
        endOutX+=mSpeed;
    }


    class DrawThread extends Thread {
        @Override
        public void run() {
            //不断进行绘制
            while (isRunning) {
                long start = System.currentTimeMillis();
                draw();
                long end = System.currentTimeMillis();
                //这里控制一下，一秒绘制二十次。也就是五十秒绘制一次
                try {
                    Thread.sleep(decrementTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isRunning = true;
                isIncrement=true;
                mThread.run();

                break;
            case MotionEvent.ACTION_UP:
                isRunning = true;
                isIncrement=false;
                mThread.run();
                break;
        }


        return true;


    }


}
