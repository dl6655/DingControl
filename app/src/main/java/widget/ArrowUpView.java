package widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.dlingli.dingcontrol.R;


/**
 * Created by dingli on 16-10-11.
 */
public class ArrowUpView extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    private SurfaceHolder mHolder;
    private Canvas mCanvas;
    private float startX = 0, startY = 0, endRedCoarseRightX = 0, endRedCoarseRightY = 0, endRedCoarseLeftX = 30, endOutY = 30, endFineX = 0, endFineY = 0, endLeftFineX = 30;
    private Boolean isIncrementRedCoarseArrow,
            isIncrementWhiteFineArrow,isRunning,isIncrement;
    //绘制线条的画笔
    private Paint lineRedPaint,lineRedCoarsePaint, lineWhitePaint,  circleWhitePaint;
    private float inLineWidth = 6;
    private float lineRedCoarseWidth;
    //定义宽度和中心宽度
    private int mWidth;
    private int mHeight;
    private int centerWidth;
    private int centerHeight;
    private float startCircleStrokeWidth = 3;
    private volatile float  currentRadius = 0;
    private volatile float mWhiteFineSpeed = 20,mRedCoarseSpeed=10,mRedCoarseCancleSpeed=30,mRedFineSpeed=50;

    private float minValueX = (float) 50;
    private float lineLong;

    private float maxValueX = 200;
    /**
     * 用户绘制的线程
     */
    private Thread mThreadWhiteFineArrow;
    private int incrementTime = 10;
    private int decrementTime = 10;

    public enum ClickType {
        CLICK_LONG,
        CLICK_SHORT
    }

    private ClickType clickType;

    public ArrowUpView(Context context) {
        super(context);
    }

    public ArrowUpView(Context context, AttributeSet attrs) {
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

    public ArrowUpView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init() {
        //初始化白色细画笔
        lineWhitePaint = new Paint();
        lineWhitePaint.setAntiAlias(true);
        lineWhitePaint.setColor(getResources().getColor(android.R.color.white));
        lineWhitePaint.setStyle(Paint.Style.STROKE);
        lineWhitePaint.setStrokeWidth(inLineWidth);
        //初始白色圆画笔
        circleWhitePaint = new Paint();
        circleWhitePaint.setAntiAlias(true);
        circleWhitePaint.setColor(getResources().getColor(android.R.color.white));
        circleWhitePaint.setStyle(Paint.Style.FILL);
        circleWhitePaint.setStrokeWidth(startCircleStrokeWidth);
        //初始化红色细画笔
        lineRedPaint = new Paint();
        lineRedPaint.setAntiAlias(true);
        lineRedPaint.setColor(getResources().getColor(R.color.red));
        lineRedPaint.setStyle(Paint.Style.STROKE);
        lineRedPaint.setStrokeWidth(inLineWidth);

        //初始化红色粗画笔
        lineRedCoarsePaint = new Paint();
        lineRedCoarsePaint.setAntiAlias(true);
        lineRedCoarsePaint.setColor(getResources().getColor(R.color.red));
        lineRedCoarsePaint.setStyle(Paint.Style.STROKE);
        lineRedCoarsePaint.setStrokeWidth(lineRedCoarseWidth);

        mThreadWhiteFineArrow = new Thread(this);
        clickType = ClickType.CLICK_LONG;
    }
    private void initCurrentValues() {

        lineRedCoarseWidth = centerWidth/8;
        lineLong = centerWidth * 0.3f;


        currentRadius = centerWidth / 5 * 0.7f;



        startX = centerWidth;
        startY =  centerHeight*3;


        endFineX = startX-1.1f ;
        endFineY = startY-startY * 0.7f+lineRedCoarseWidth / 5f;
        endLeftFineX =  startX+1.1f;

        endRedCoarseRightX =startX - lineRedCoarseWidth / 3.5f;
        endRedCoarseRightY = startY-startY * 0.7f;
        endRedCoarseLeftX = startX+lineRedCoarseWidth/3.5f;

        maxValueX = startX + lineLong * 2.6f;
        minValueX = startX;

    }
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        init();
        initCurrentValues();
        drawWhiteCircleBg();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isRunning=false;
    }

    @Override
    public void run() {
        while (isRunning) {
            drawRedCoarseArrowBg();
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

    private void drawRedCoarseArrowBg() {
        try {
            mCanvas = mHolder.lockCanvas();
            if (mCanvas != null) {
                mCanvas.drawColor(Color.BLACK);
                if (clickType == ClickType.CLICK_LONG) {

                    if (isIncrement) {
                        if(isIncrementWhiteFineArrow){
                            setWhiteFineValues();
                            setCoarseValues();
                        }else{
                            drawWhiteFineArrow();
                            setCoarseValues();
                        }

                    }
                    else {
                        if(isIncrementRedCoarseArrow){
                            setWhiteFineValues();
                        }else{
                            setWhiteFineValues();
                            setCoarseValues();
                        }

                    }
                }else if(clickType == ClickType.CLICK_SHORT){
                       setRedFineValues();
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




    public void drawWhiteFineArrow() {
        if (startX < endFineX) {
            mCanvas.drawLine(startX-1.1f , startY-startY * 0.7f+lineRedCoarseWidth / 5f, endFineX, endFineY, lineWhitePaint);
            mCanvas.drawLine(startX+1.1f, startY- startY * 0.7f+lineRedCoarseWidth / 5f, endLeftFineX, endFineY, lineWhitePaint);
        }
    }

    public void drawWhiteCircle() {
        mCanvas.drawCircle(startX, startY - startY * 0.7f, currentRadius * 0.7f, circleWhitePaint);
    }
    public void drawRedFineArrow() {

        if (startX < endFineX) {
            mCanvas.drawLine(startX -1.1f, startY-startY * 0.7f+lineRedCoarseWidth / 5f, endFineX, endFineY, lineRedPaint);
            mCanvas.drawLine(startX+1.1f, startY-startY * 0.7f+lineRedCoarseWidth / 5f, endLeftFineX, endFineY, lineRedPaint);
        }

    }
    public void drawRedCoarseArrow() {
        if (startX < endRedCoarseRightX) {
            lineRedCoarsePaint.setStrokeWidth(lineRedCoarseWidth);
            mCanvas.drawLine(startX  - lineRedCoarseWidth / 3.5f, startY - startY * 0.7f, endRedCoarseRightX, endRedCoarseRightY, lineRedCoarsePaint); //right line
            mCanvas.drawLine(startX+ lineRedCoarseWidth / 3.5f, startY  - startY * 0.7f, endRedCoarseLeftX, endRedCoarseRightY, lineRedCoarsePaint);
        }

    }


    private void setCoarseValues() {
        if(isIncrementRedCoarseArrow){
            if(endRedCoarseRightX+mRedCoarseSpeed * 1.35f>maxValueX){
                endRedCoarseRightX=endFineX;
                endRedCoarseRightY=endFineY;
                endRedCoarseLeftX=endLeftFineX;
            }
        }
        
        drawRedCoarseArrow();
        if (isIncrementRedCoarseArrow) {
            incrementCoarseArrow();
        } else {
            decrementCoarseArrow();
        }
        if (endRedCoarseRightX > maxValueX) {
            isIncrementRedCoarseArrow = false;
            isRunning= false;
            isIncrement=false;
        } else if (endRedCoarseRightX < minValueX) {
            isIncrementRedCoarseArrow = true;
        }

    }

    private void setWhiteFineValues() {
//        Log.i("AAAD", "setFineValues endFineX=" + endFineX+ ",maxValueX=" + maxValueX +",endFineY="+endFineY+ ",startX=" + startX +",startY="+startY+
//                ",minValueX=" + minValueX );
        if (endFineX - mWhiteFineSpeed * 1.35f < minValueX) {
            endFineX = minValueX;
        }
        drawWhiteFineArrow();
        if (isIncrementWhiteFineArrow) {
            incrementWhiteFineArrow();
        } else {
            decrementWhiteFineArrow();
        }
        if (endFineX > maxValueX) {
            isIncrementWhiteFineArrow = false;
        } else if (endFineX < minValueX) {
            drawWhiteCircle();
            isIncrementWhiteFineArrow = true;
            isRunning = false;
        }

    }
    private void setRedFineValues() {
        if (endFineX - mRedFineSpeed * 1.35f < minValueX) {
            endFineX = minValueX;
        }
        drawRedFineArrow();
        if (isIncrement) {
            incrementRedFineArrow();
        } else {
            decrementRedFineArrow();
        }
        if (endFineX > maxValueX) {
            isIncrement = false;
        } else if (endFineX < minValueX) {
            drawWhiteCircle();
            isRunning = false;
        }

    }

    private void incrementWhiteFineArrow() {
        endFineX += mWhiteFineSpeed * 1.35f;
        endFineY += mWhiteFineSpeed;
        endLeftFineX -= mWhiteFineSpeed * 1.35f;
    }
    private void decrementWhiteFineArrow() {
        endFineX -= mWhiteFineSpeed * 1.35f;
        endFineY -= mWhiteFineSpeed;
        endLeftFineX += mWhiteFineSpeed * 1.35f;
    }
    private void incrementRedFineArrow() {
        endFineX += mRedFineSpeed * 1.35f;
        endFineY += mRedFineSpeed;
        endLeftFineX -= mRedFineSpeed * 1.35f;
    }
    private void decrementRedFineArrow() {
        endFineX -= mRedFineSpeed * 1.35f;
        endFineY -= mRedFineSpeed;
        endLeftFineX += mRedFineSpeed * 1.35f;
    }
    private void incrementCoarseArrow() {
        endRedCoarseRightX += mRedCoarseSpeed * 1.35f;
        endRedCoarseRightY += mRedCoarseSpeed;
        endRedCoarseLeftX -= mRedCoarseSpeed * 1.35f;


    }
    private void decrementCoarseArrow() {
        endRedCoarseRightX -= mRedCoarseCancleSpeed * 1.35f;
        endRedCoarseRightY -= mRedCoarseCancleSpeed;
        endRedCoarseLeftX += mRedCoarseCancleSpeed * 1.35f;
    }


    public void drawWhiteCircleBg() {

        try {
            mCanvas = mHolder.lockCanvas();
            if (mCanvas != null) {
                mCanvas.drawColor(Color.BLACK);
              drawWhiteCircle();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (mCanvas != null) {
                mHolder.unlockCanvasAndPost(mCanvas);
            }
        }


    }


    public void click() {


        clickType = ClickType.CLICK_SHORT;
        isRunning = true;
        isIncrement=true;


        initCurrentValues();

        startUIThreadWhiteFineArrow();
    }

    public void longClick() {

        clickType = ClickType.CLICK_LONG;
        isRunning = true;
        isIncrement=true;
        isIncrementRedCoarseArrow=true;
        isIncrementWhiteFineArrow = true;

        initCurrentValues();
        startUIThreadWhiteFineArrow();
    }

    public void cancleLongclick() {
        if (clickType == ClickType.CLICK_SHORT) {
            return;
        }
        isRunning = true;
        isIncrement=false;
        isIncrementWhiteFineArrow = false;
        isIncrementRedCoarseArrow = false;
        startUIThreadWhiteFineArrow();
    }

    private void startUIThreadWhiteFineArrow() {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                mThreadWhiteFineArrow.run();
            }
        });
    }
}
