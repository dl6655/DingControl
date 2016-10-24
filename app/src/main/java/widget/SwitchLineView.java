package widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by dingli on 16-10-19.
 */
public class SwitchLineView extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    private SurfaceHolder mHolder;
    private Canvas mCanvas;
    private int mWidth, mHeight, centerWidth, centerHeight;
    private int incrementInterval, decrementInterval;
    private Paint mLineWhitePaint, mLineBluePaint, mLineBackgroundPaint;
    private boolean isRunning;
    private boolean isIncrement;
    /**
     * on or off
     */
    private boolean isOn;
    private float startX, startY, endX, endY;

    private float mCircleRadiusBlue, maxValueRaduisBlueX, minValueRaduisBlueX, mCircleBlueStep;
    private float startBlueLineX, startBlueLineY, endBlueLineX,
            endBlueLineY, maxValueLineBlueX, minValueLineBlueX,mLineBlueStep;
    private float mCircleRadius, mCircleCenterLeftX, mCircleCenterLeftY, mCircleCenterRightX, mCircleCenterRightY;
    private Thread mThread;
    private DrawType drawType;
    private ChangeOrientation changeOrientation;

    public enum DrawType {
        DRAW_LEFT_BLUE_CIRLE,
        DRAW_RIGHT_BLUE_CIRLE,
        DRAW_LEFT_BLUE_LINE,
        DRAW_RIGHT_BLUE_LINE
    }

    public enum ChangeOrientation {
        BIG_TO_SMALL,
        SMALL_TO_BIG,
        LEFT_TO_RIGHT,
        RIGHT_TO_LEFT
    }

    public boolean isOn() {
        return isOn;
    }

    public void setIsOn(boolean isOn) {
        this.isOn = isOn;
    }

    public SwitchLineView(Context context) {
        super(context);
    }

    public SwitchLineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mHolder = getHolder();
        mHolder.addCallback(this);
    }

    public SwitchLineView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();

        centerWidth = mWidth / 2;
        centerHeight = mHeight / 2;


    }

    private void init() {
        mLineWhitePaint = new Paint();
        mLineWhitePaint.setColor(Color.WHITE);
        mLineWhitePaint.setAntiAlias(true);
        mLineWhitePaint.setStrokeWidth(10);
        mLineWhitePaint.setStyle(Paint.Style.STROKE);

        mLineBackgroundPaint = new Paint();
        mLineBackgroundPaint.setColor(Color.BLACK);
        mLineBackgroundPaint.setAntiAlias(true);
        mLineBackgroundPaint.setStrokeWidth(10);
        mLineBackgroundPaint.setStyle(Paint.Style.FILL);

        mLineBluePaint = new Paint();
        mLineBluePaint.setColor(getResources().getColor(android.R.color.holo_blue_bright));
        mLineBluePaint.setAntiAlias(true);
        mLineBluePaint.setStrokeWidth(20);
        mLineBluePaint.setStyle(Paint.Style.FILL);

        mThread = new Thread(this);


        incrementInterval = 10;
        decrementInterval = 10;

        startX = centerWidth / 4;
        startY = centerHeight;

        endX = startX;
        endY = startY;


        //circle

        mCircleRadius = centerWidth / 4;


        mCircleCenterLeftX = startX * 3 - mCircleRadius;
        mCircleCenterLeftY = centerHeight;

        mCircleCenterRightX = startX * 5 + mCircleRadius;
        mCircleCenterRightY = centerHeight;


        initCircleBlue();
        initLineBlueMobile();
        selectDrawType();


    }
    private void selectDrawType() {
        if (!isOn()) {
            drawType = DrawType.DRAW_LEFT_BLUE_CIRLE;
        } else {
            drawType = DrawType.DRAW_RIGHT_BLUE_CIRLE;
        }
    }
    private void initCircleBlue() {
        //blue circle
        mCircleBlueStep = 10;
        mCircleRadiusBlue = mCircleRadius - 30;

        maxValueRaduisBlueX = mCircleRadiusBlue;
        minValueRaduisBlueX = 0;

    }

    private void initLineBlueMobile() {

        //blue line
        mLineBlueStep=30;

        startBlueLineX = startX * 3;
        startBlueLineY = centerHeight;


        endBlueLineX = startBlueLineX + 30;
        endBlueLineY = startBlueLineY;

        maxValueLineBlueX = startX * 5;
        minValueLineBlueX = startX * 3;
    }

    private void initCurrentValues() {
//        mCircleRadiusBlue=mCircleRadius-30;

//        startBlueLineX=startX*3;
//        startBlueLineY=centerHeight;
//
//        endBlueLineX=startBlueLineX;
//        endBlueLineY=startBlueLineY;
        isIncrement = false;
        isRunning = true;
        if (drawType == DrawType.DRAW_LEFT_BLUE_CIRLE) {

            startBlueLineX = startX * 3;
            endBlueLineX = startBlueLineX + 30;

        } else if (drawType == DrawType.DRAW_RIGHT_BLUE_CIRLE) {

            startBlueLineX = startX * 5;
            endBlueLineX = startBlueLineX - 30;

        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        init();
        drawBgFirst();

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public void run() {
        while (isRunning) {
            drawCircleBluehange();
            try {
                if (isIncrement) {
                    Thread.sleep(incrementInterval);
                } else {
                    Thread.sleep(decrementInterval);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }


    private void setValueCircleBlue() {
//        Log.i("AAAD","setValueLeftCircleBlue Radius="+mCircleRadiusBlue+",max="+maxValueRaduisBlueX+",min="+minValueRaduisBlueX);
        if (isIncrement) {
            if (mCircleRadiusBlue + mCircleBlueStep > maxValueRaduisBlueX) {
                mCircleRadiusBlue = maxValueRaduisBlueX;
            }
        } else {
            if (mCircleRadiusBlue - mCircleBlueStep < minValueRaduisBlueX) {
                mCircleRadiusBlue = minValueRaduisBlueX;
            }
        }


        drawLineBlueChange();
        if (isIncrement) {
            incrementRightCircleBlueValue();
        } else {
            decrementRightCircleBlueValue();
        }

        if (mCircleRadiusBlue > maxValueRaduisBlueX) {
            isRunning = false;
            if(drawType == DrawType.DRAW_LEFT_BLUE_CIRLE){
                setIsOn(false);
            }else if(drawType == DrawType.DRAW_RIGHT_BLUE_CIRLE){
                setIsOn(true);
            }
        } else if (mCircleRadiusBlue < minValueRaduisBlueX) {
            if(drawType == DrawType.DRAW_LEFT_BLUE_CIRLE){
                isIncrement = true;
            }else if(drawType == DrawType.DRAW_RIGHT_BLUE_CIRLE){
                isIncrement = false;
            }
            setValueLineBlue();
        }

    }

    private void setValueLineBlue() {
//        Log.i("AAAD","setValueLineBlueLToR endX="+endBlueLineX+",startX="+startBlueLineX+",max="+maxValueLineBlueX
//        +",step="+mCircleBlueStep);
        if(isIncrement){
            if (endBlueLineX + mCircleBlueStep > maxValueLineBlueX) {
                endBlueLineX = maxValueLineBlueX;

            }
        }else{
            if (endBlueLineX - mCircleBlueStep < minValueLineBlueX) {
                endBlueLineX = minValueLineBlueX;
            }
        }

        drawType = DrawType.DRAW_LEFT_BLUE_LINE;
        drawLineBlueChange();

        if (isIncrement) {
            incrementBlueLineValueLToR();
        } else {
            decrementBlueLineValueRToL();
        }

        if (endBlueLineX > maxValueLineBlueX) {
            isIncrement=true;
            drawType = DrawType.DRAW_RIGHT_BLUE_CIRLE;
            setValueCircleBlue();
        } else if (endBlueLineX < minValueLineBlueX) {
            isIncrement=true;
            drawType = DrawType.DRAW_LEFT_BLUE_CIRLE;
            setValueCircleBlue();
        }

    }

    private void incrementRightCircleBlueValue() {
        mCircleRadiusBlue += mCircleBlueStep;
    }

    private void decrementRightCircleBlueValue() {
        mCircleRadiusBlue -= mCircleBlueStep;
    }

    private void incrementBlueLineValueLToR() {
        startBlueLineX += mLineBlueStep+10;
        endBlueLineX += mLineBlueStep+10;
    }

    private void decrementBlueLineValueRToL() {
        startBlueLineX -= mLineBlueStep;
        endBlueLineX -= mLineBlueStep;
    }

    private void drawCircleBluehange() {
        try {
            mCanvas = mHolder.lockCanvas();
            if (mCanvas != null) {
                mCanvas.drawColor(Color.BLACK);

                if (drawType == DrawType.DRAW_LEFT_BLUE_LINE) {
                    setValueLineBlue();
                } else if (drawType == DrawType.DRAW_LEFT_BLUE_CIRLE) {
                    setValueCircleBlue();
                } else if (drawType == DrawType.DRAW_RIGHT_BLUE_CIRLE) {
                    setValueCircleBlue();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != mCanvas) {
                mHolder.unlockCanvasAndPost(mCanvas);
            }
        }
    }

    private void drawLineBlueChange() {
        drawLeftArc();
        drawRightArc();
        drawWhiteLine();
        if (drawType == DrawType.DRAW_LEFT_BLUE_CIRLE) {
            drawLeftCircleFillChange();
        } else if (drawType == DrawType.DRAW_LEFT_BLUE_LINE) {
            drawBlueLineLToRChange();
            setIsOn(false);
        } else if (drawType == DrawType.DRAW_RIGHT_BLUE_CIRLE) {
            drawRightCircleFillChange();
            setIsOn(true);
        }


        drawLeftCircleLine();
        drawRightCircleLine();
    }

    private void drawBgFirst() {
        try {
            mCanvas = mHolder.lockCanvas();
            if (mCanvas != null) {
                mCanvas.drawColor(Color.BLACK);
                drawLeftArc();
                drawRightArc();

                drawWhiteLine();
                if (drawType == DrawType.DRAW_LEFT_BLUE_CIRLE) {
                    drawLeftCircleFill();
                } else if (drawType == DrawType.DRAW_RIGHT_BLUE_CIRLE) {
                    drawRightCircleFill();
                }


                drawLeftCircleLine();
                drawRightCircleLine();


            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != mCanvas) {
                mHolder.unlockCanvasAndPost(mCanvas);
            }
        }
    }

    private void drawLeftCircleFillChange() {
        mCanvas.drawCircle(mCircleCenterLeftX, mCircleCenterLeftY, mCircleRadiusBlue, mLineBluePaint);
    }

    private void drawRightCircleFillChange() {
        mCanvas.drawCircle(mCircleCenterRightX, mCircleCenterRightY, mCircleRadiusBlue, mLineBluePaint);
    }

    private void drawLeftCircleFill() {
        mCanvas.drawCircle(mCircleCenterLeftX, mCircleCenterLeftY, mCircleRadius - 30, mLineBluePaint);
    }

    private void drawRightCircleFill() {
        mCanvas.drawCircle(mCircleCenterRightX, mCircleCenterRightY, mCircleRadius - 30, mLineBluePaint);
    }

    private void drawWhiteLine() {
        mCanvas.drawLine(startX * 3, startY, startX * 5, startY, mLineWhitePaint);
    }

    private void drawBlueLineLToRChange() {
        mCanvas.drawLine(startBlueLineX, startY, endBlueLineX, endBlueLineY, mLineBluePaint);
    }

    private void drawBlueLineLToR() {
        mCanvas.drawLine(startX * 3, startY, endBlueLineX + 30, startY, mLineBluePaint);
    }

    private void drawLeftCircleLine() {
        mCanvas.drawCircle(startX * 3 - mCircleRadius, startY, mCircleRadius, mLineWhitePaint);

    }

    private void drawRightCircleLine() {
        mCanvas.drawCircle(startX * 5 + mCircleRadius, startY, mCircleRadius, mLineWhitePaint);

    }

    private void drawLeftArc() {
        mCanvas.drawCircle(startX * 3 - mCircleRadius, startY, mCircleRadius + mCircleRadius / 2, mLineWhitePaint);

        RectF leftRectF = new RectF(startX * 3 - mCircleRadius,
                startY - mCircleRadius - mCircleRadius / 2 - 20, startX * 3 + mCircleRadius / 2 + 20, startY + mCircleRadius + mCircleRadius / 2 + 20);

        mCanvas.drawRect(leftRectF, mLineBackgroundPaint);

    }

    private void drawRightArc() {
        mCanvas.drawCircle(startX * 5 + mCircleRadius, startY, mCircleRadius + mCircleRadius / 2, mLineWhitePaint);

        RectF leftRectF = new RectF(startX * 5 + mCircleRadius,
                startY - mCircleRadius - mCircleRadius / 2 - 20, startX * 5 - mCircleRadius / 2 - 20, startY + mCircleRadius + mCircleRadius / 2 + 20);

        mCanvas.drawRect(leftRectF, mLineBackgroundPaint);

    }

    public void click() {
        selectDrawType();
        initCurrentValues();
        startCircleBlueDecrement();
    }

    private void startCircleBlueDecrement() {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                startThread();
            }
        });
    }

    private void startThread() {
        mThread.run();
    }
}
