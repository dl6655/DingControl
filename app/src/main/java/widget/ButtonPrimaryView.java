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

/**
 * Created by dingli on 16-10-19.
 */
public class ButtonPrimaryView extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    private SurfaceHolder mHolder;
    private Canvas mCanvas;
    private Paint lineWhitePaint, lineBluePaint,textWhitePaint;
    private int startX, startY, endBlueX, endBlueY, maxValueY, minValueY,mIncrementStep,mDecrementStep;
    private boolean isRunning, isIncrement;
    //定义宽度和中心宽度
    int mWidth;
    int mHeight;
    int centerWidth;
    int centerHeight;
    private int incrementInterval, decrementInterval;
    private Thread mThread;
    private String buttonText="Button";
    public String getButtonText() {
        return buttonText;
    }

    public void setButtonText(String buttonText) {
        this.buttonText = buttonText;
    }

    public ButtonPrimaryView(Context context) {
        super(context);
    }

    public ButtonPrimaryView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mHolder = getHolder();
        mHolder.addCallback(this);
    }

    public ButtonPrimaryView(Context context, AttributeSet attrs, int defStyleAttr) {
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
        lineWhitePaint = new Paint();
        lineWhitePaint.setAntiAlias(true);
        lineWhitePaint.setStrokeWidth(5);
        lineWhitePaint.setColor(getResources().getColor(android.R.color.white));
        lineWhitePaint.setStyle(Paint.Style.STROKE);


        lineBluePaint = new Paint();
        lineBluePaint.setAntiAlias(true);
        lineBluePaint.setStrokeWidth(20);
        lineBluePaint.setColor(getResources().getColor(android.R.color.holo_blue_bright));
        lineBluePaint.setStyle(Paint.Style.FILL);

        textWhitePaint=new Paint();
        textWhitePaint.setColor(Color.WHITE);
        textWhitePaint.setTextSize(100f);

        mThread = new Thread(this);
        incrementInterval = 10;
        decrementInterval = 10;

        mIncrementStep=10;
        mDecrementStep=20;

        startX =centerWidth /4;
        startY = centerHeight-startX;

        endBlueX=mWidth-startX;
        endBlueY=centerHeight+startX;

        maxValueY=endBlueY;
        minValueY=startY+5;

    }

    private void initCurrentValue() {
        endBlueY=centerHeight+startX;

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        init();
        initCurrentValue();
        drawWhiteRectangularBg();
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
            drawBlueRectangularBg();
            try {
                Thread.sleep(incrementInterval);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }

    private void drawBlueRectangularBg() {
        try {
            mCanvas = mHolder.lockCanvas();
            if (mCanvas != null) {
                mCanvas.drawColor(Color.BLACK);

                setBlueRectangularValues();
                drawWhiteRectangular();

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (mCanvas != null) {
                mHolder.unlockCanvasAndPost(mCanvas);
            }
        }

    }

    private void drawWhiteRectangularBg() {
        try {
            mCanvas = mHolder.lockCanvas();
            if (mCanvas != null) {
                mCanvas.drawColor(Color.BLACK);
                drawWhiteRectangular();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (mCanvas != null) {
                mHolder.unlockCanvasAndPost(mCanvas);
            }
        }

    }

    private void drawBlueRectangular() {
        mCanvas.drawRect(startX,centerHeight+startX-5,endBlueX,endBlueY,lineBluePaint);
      }
    private void drawWhiteRectangular() {
//        Log.i("AAAD","drawWhiteRectangular startX="+startX+",startY="+startY+",endX="+endX+",endY="+endY);
//        mRectf=new RectF(startX,startY,startX*2,startY*2);
//        mCanvas.drawRect(150,150,250,250,lineWhitePaint);

        mCanvas.drawRect(startX,startY,mWidth-startX,centerHeight+startX,lineWhitePaint);
        mCanvas.drawLine(startX, centerHeight +startX - 5, mWidth - startX, centerHeight +startX - 5, lineBluePaint);
        mCanvas.drawText(getButtonText(),centerWidth-150,centerHeight+50,textWhitePaint);

    }
   private void setBlueRectangularValues(){
//       Log.i("AAAD", "setBlueRectangularValues endBlueY=" + endBlueY +",startX = "+startX+", startY = "+startY+",minValueY=" + minValueY + ",maxValueY=" + maxValueY);
      if(isIncrement){
          if(endBlueY-mIncrementStep<=minValueY){
              endBlueY=minValueY;
          }
      }else{
          if(endBlueY+mDecrementStep>maxValueY){
              endBlueY=maxValueY;
          }
      }
       drawBlueRectangular();
       if(isIncrement){
           incrementBlueValues();
       }else{

           decrementBlueValues();
       }

       if(endBlueY<minValueY){
           isRunning=false;
           cancleDown();
       }else if(endBlueY>maxValueY){
           isRunning=false;
       }


   }

    private void incrementBlueValues(){
        endBlueY-=mIncrementStep;

    }
    private void decrementBlueValues(){
        endBlueY+=mDecrementStep;
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        switch (event.getAction()){
//            case MotionEvent.ACTION_DOWN:
//
//                onTouchDown();
//                break;
//            case MotionEvent.ACTION_UP:
//
//                onTouchUp();
//                break;
//        }
//        return true;
//    }

    @Override
    public boolean callOnClick() {
//        Log.i("AAAD","callOnClick");
        onTouchDown();
        return true;
    }

    public void click(){
//        Log.i("AAAD","click");
        onTouchDown();
    }

    private void startThread(){
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                mThread.run();
            }
        });

    }
    private void cancleDown(){
        isIncrement=false;
        isRunning=true;
        startThread();

    }
    public void onTouchDown(){
//        Log.i("AAAD","onTouchDown");
        initCurrentValue();
        isIncrement=true;
        isRunning=true;
        startThread();

    }
    public void onTouchUp(){
//        Log.i("AAAD","onTouchUp");
        isIncrement=false;
        isRunning=true;
        startThread();
    }
}
