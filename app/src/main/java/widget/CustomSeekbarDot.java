package widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

import com.dlingli.dingcontrol.R;

import java.util.ArrayList;

/**
 * Created by dlingli on 16/10/2.
 */
public class CustomSeekbarDot extends View {
    public CustomSeekbarDot(Context context) {
        super(context);
    }

    public CustomSeekbarDot(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomSeekbarDot(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public enum GesturesState {
        GESTURES_STATE_DOWN,
        GESTURES_STATE_MOVE,
        GESTURES_STATE_UP
    }

    private final String TAG = "CustomSeekbar";
    private int width;
    private int height;
    private int downX = 0;
    private int downY = 0;
    private int upX = 0;
    private int upY = 0;
    private int moveX = 0;
    private int moveY = 0;
    private float scale = 0;
    private int perWidth = 0;
    private Paint mPaint;
    private Paint mTextPaint;
    private Paint buttonPaint;
    private Canvas canvas;
    private Bitmap bitmap;
    private Bitmap thumb, thumb2;
    private Bitmap spot;
    private Bitmap spot_on;
    private int hotarea = 100;//点击的热区
    private int cur_sections = 2;
    private ResponseOnTouch responseOnTouch;
    private int bitMapHeight = 20;//第一个点的起始位置起始，图片的长宽是76，所以取一半的距离
    private int textMove = 60;//字与下方点的距离，因为字体字体是40px，再加上10的间隔
    private int[] colors = new int[]{0xffdf5600, 0x33000000};//进度条的橙色,进度条的灰色,字体的灰色
    private int textSize;
    private int circleRadius;
    private ArrayList<String> section_title;
    private int dotNum,lineWidth;
    private GesturesState gesturesState;
    private float gMoveX, gMoveY, gOldMoveX, gOldMoveY;
    private ArrayList<Point> arrayListPoint = new ArrayList<Point>();
    private Point point;

    public void init() {
        cur_sections = 0;
        bitmap = Bitmap.createBitmap(900, 900, Bitmap.Config.ARGB_8888);
        canvas = new Canvas();
        canvas.setBitmap(bitmap);
//        canvas.drawColor(Color.BLACK);

        thumb = BitmapFactory.decodeResource(getResources(), R.drawable.seekbar_thumb_normal);
        thumb2 = BitmapFactory.decodeResource(getResources(), R.drawable.background_single_uncheck);
        spot = BitmapFactory.decodeResource(getResources(), R.drawable.background_status_gray);
        spot_on = BitmapFactory.decodeResource(getResources(), R.drawable.background_status_blue);
//        bitMapHeight = thumb2.getHeight() / 2;

        textMove = bitMapHeight + 22;
        textSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics());
        circleRadius = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3, getResources().getDisplayMetrics());
        mPaint = new Paint(Paint.DITHER_FLAG);
        mPaint.setAntiAlias(true);//锯齿不显示
        mPaint.setStrokeWidth(3);
        mTextPaint = new Paint(Paint.DITHER_FLAG);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(textSize);
        mTextPaint.setColor(0xffb5b5b4);
        buttonPaint = new Paint(Paint.DITHER_FLAG);
        buttonPaint.setAntiAlias(true);


        //initData(null);

    }

    /**
     * 实例化后调用，设置bar的段数和文字
     */
    public void initData(ArrayList<String> section) {
        if (section != null) {
            section_title = section;
        } else {
            String[] str = new String[]{"低", "中", "高"};
            section_title = new ArrayList<String>();
            for (int i = 0; i < str.length; i++) {
                section_title.add(str[i]);
            }
        }
        dotNum=section_title.size()-1;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        width = widthSize;
        float scaleX = widthSize / 1080;
        float scaleY = heightSize / 1920;
        scale = Math.max(scaleX, scaleY);
        //控件的高度
        //        height = 185;
        height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 62, getResources().getDisplayMetrics());
        setMeasuredDimension(width, height);
//        width = width - bitMapHeight;
        perWidth = (width-2*bitMapHeight) / (section_title.size() - 1);
        lineWidth=perWidth*dotNum;
        hotarea = perWidth / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAlpha(255);
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);
        canvas.drawBitmap(bitmap, 0, 0, null);
//        mPaint.setAlpha(255);
//        bitMapHeight = thumb2.getHeight() / 2;
        int section = 0;
        if (gesturesState == GesturesState.GESTURES_STATE_MOVE) {
            arrayListPoint.size();
            for (int i = 0; i < arrayListPoint.size(); i++) {
                Point point = arrayListPoint.get(i);
                if (point.x > gMoveX - bitMapHeight * 2 && point.x < gMoveX + bitMapHeight * 2) {
                    thumb = big(thumb);
//                    Log.i("AAAD","point.x="+point.x+",width="+width+
//                            ",perWidth="+perWidth+",Lwidth="+(lineWidth));
//                    bitMapHeight = thumb2.getHeight() / 2;
                }
            }

            while (section < section_title.size()) {
//                if (section < cur_sections) {
//                    Log.i("AAAD", "gMoveX=" + gMoveX + ",gOldMoveX=" + gOldMoveX +
//                            ",section=" + section + ",cur_sections=" + cur_sections);
//                    if (gOldMoveX < gMoveX) {
//                    canvas.drawBitmap(spot_on,
//                            section * perWidth + section * spot_on.getWidth(),
//                            height * 1 / 2 - spot_on.getHeight() / 2,
//                            mPaint);

//                    } else {
//                        canvas.drawBitmap(spot,
//                                section * perWidth + section * spot_on.getWidth(),
//                                height * 1 / 2 - spot_on.getHeight() / 2,
//                                mPaint);
//                    }
//                    mPaint.setColor(colors[0]);
//
//                    canvas.drawBitmap(thumb,
//                            gMoveX,
//                            height * 1 / 2 - bitMapHeight, buttonPaint);


//                    canvas.drawLine(section * perWidth + (section + 1) * spot_on.getWidth(),
//                            height * 1 / 2,
//                            gMoveX,
//                            height * 1 / 2, mPaint);


//                     canvas.drawBitmap(spot_on,
//                             section * perWidth + section * spot_on.getWidth(),
//                             height * 1 / 2 - spot_on.getHeight() / 2,
//                             mPaint);


//                } else {

                canvas.drawBitmap(thumb,
                        gMoveX,
                        height * 1 / 2 - thumb.getHeight() / 2, buttonPaint);
//                    mPaint.setAlpha(255);
                if (section == section_title.size() - 1) {
                    canvas.drawBitmap(spot, lineWidth - spot.getWidth(),
                            height * 1 / 2 - spot.getHeight() / 2, mPaint);
                } else
                if (section == 0) {
                    canvas.drawBitmap(spot, bitMapHeight,
                            height * 1 / 2 - spot.getHeight() / 2, mPaint);
                } else {
                    canvas.drawBitmap(spot, section * perWidth,
                            height * 1 / 2 - spot.getHeight() / 2, mPaint);
                }


//                }

                section++;
            }


            gOldMoveX = gMoveX;
            gOldMoveY = gMoveY;


        } else {
            mPaint.setColor(colors[1]);
            canvas.drawLine(bitMapHeight, height * 1 / 2, lineWidth, height * 1 / 2, mPaint);
            normalDraw(canvas, section);
        }

//            while (section < section_title.size()) {
//                if (section < cur_sections) {
//                    mPaint.setColor(colors[0]);
////                    switch (gesturesState){
////                        case GESTURES_STATE_DOWN:
////                        case GESTURES_STATE_UP:
//                            canvas.drawLine(section * perWidth + (section + 1) * spot_on.getWidth(),
//                                    height * 1 / 2,
//                                    section * perWidth + (section + 1) * spot_on.getWidth() + perWidth,
//                                    height * 1 / 2, mPaint);
//                            canvas.drawBitmap(spot_on,
//                                    section * perWidth + section * spot_on.getWidth(),
//                                    height * 1 / 2 - spot_on.getHeight() / 2,
//                                    mPaint);
//
//
////                        break;
////                        case GESTURES_STATE_MOVE:
////                            canvas.drawBitmap(thumb,
////                                    gMoveX,
////                                    height * 1 / 2 - bitMapHeight, buttonPaint);
////
////                            canvas.drawLine(gMoveX,
////                                    height * 1 / 2,
////                                    section * perWidth + (section + 1) * spot_on.getWidth() + perWidth,
////                                    height * 1 / 2, mPaint);
////                            canvas.drawBitmap(spot_on,
////                                    section * perWidth + section * spot_on.getWidth(),
////                                    height * 1 / 2 - spot_on.getHeight() / 2,
////                                    mPaint);
////                        break;
////                    }
//
//
//                } else {
//                    mPaint.setAlpha(255);
//                    if (section == section_title.size() - 1) {
//                        canvas.drawBitmap(spot, width - spot_on.getWidth() - bitMapHeight / 2,
//                                height * 1 / 2 - spot.getHeight() / 2, mPaint);
//                    } else {
//                        canvas.drawBitmap(spot, section * perWidth + section * spot_on.getWidth(),
//                                height * 1 / 2 - spot.getHeight() / 2, mPaint);
//                    }
//                }
//
//                section++;
//            }


//        if (cur_sections == section_title.size() - 1) {
//            canvas.drawBitmap(thumb,
//                    width - spot_on.getWidth() / 2 - bitMapHeight / 2 ,
//                    height * 1 / 2 - bitMapHeight, buttonPaint);
//        } else {
//
//            canvas.drawBitmap(thumb, cur_sections * perWidth + cur_sections * spot_on.getWidth() - thumb.getWidth() / 4,
//                    height * 1 / 2 - bitMapHeight, buttonPaint);
//        }


    }

    public void normalDraw(Canvas canvas, int section) {
        arrayListPoint.clear();
        while (section < section_title.size()) {
            point = new Point();
//            if (section < cur_sections) {
//                mPaint.setColor(colors[0]);
//                canvas.drawLine(section * perWidth + (section + 1) * spot_on.getWidth(),
//                        height * 1 / 2,
//                        section * perWidth + (section + 1) * spot_on.getWidth() + perWidth,
//                        height * 1 / 2, mPaint);
//                canvas.drawBitmap(spot_on,
//                        section * perWidth + section * spot_on.getWidth(),
//                        height * 1 / 2 - spot_on.getHeight() / 2,
//                        mPaint);
//                point.x=section * perWidth + section * spot_on.getWidth();
//                point.y=height * 1 / 2 - spot.getHeight() / 2;
//
//                arrayListPoint.add(point);
//
//            } else {
            mPaint.setAlpha(255);

            if (section == section_title.size() - 1) {
                canvas.drawBitmap(spot, lineWidth - spot.getWidth(),
                        height * 1 / 2 -spot.getHeight()/2, mPaint);
            } else
            if (section == 0) {
                canvas.drawBitmap(spot, bitMapHeight,
                        height * 1 / 2 -spot.getHeight()/2 , mPaint);
            } else {
                canvas.drawBitmap(spot, section * perWidth,
                        height * 1 / 2 -spot.getHeight()/2 , mPaint);
            }


            point.x = section * perWidth;
            point.y = height * 1 / 2 - spot.getHeight() / 2;

            arrayListPoint.add(point);
//            }

            section++;
        }

        if (cur_sections == section_title.size() - 1) {
            canvas.drawBitmap(thumb,
                     lineWidth-thumb.getWidth()/2-spot.getWidth()/2,
                    height * 1 / 2 -thumb.getHeight()/2, buttonPaint);
        } else
        if (cur_sections == 0) {
            canvas.drawBitmap(thumb, bitMapHeight - spot.getWidth() / 2,
                    height * 1 / 2 -thumb.getHeight()/2 , buttonPaint);
        } else {
            canvas.drawBitmap(thumb, cur_sections * perWidth  - spot.getWidth() / 2,
                    height * 1 / 2 -thumb.getHeight()/2 , buttonPaint);
        }

    }

    public static Drawable bitmapToDrawble(Bitmap bitmap, Context mcontext) {
        Drawable drawable = new BitmapDrawable(mcontext.getResources(), bitmap);
        return drawable;
    }

    public static Bitmap drawableToBitamp(Drawable drawable) {
        BitmapDrawable bd = (BitmapDrawable) drawable;
        return bd.getBitmap();
    }

    private Bitmap big(Bitmap bitmap) {
        Matrix matrix = new Matrix();
        matrix.postScale(1.5f, 1.5f); //长和宽放大缩小的比例
        Bitmap resizeBmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizeBmp;
    }

    private Bitmap small(Bitmap bitmap) {
        Matrix matrix = new Matrix();
        matrix.postRotate(180);
        matrix.postScale(1.0f, 1.0f); //长和宽放大缩小的比例
        Bitmap resizeBmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizeBmp;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                gesturesState = GesturesState.GESTURES_STATE_DOWN;
                thumb = BitmapFactory.decodeResource(getResources(), R.drawable.seekbar_thumb_pressed);
                downX = (int) event.getX();
                downY = (int) event.getY();
                responseTouch(downX, downY);
                break;
            case MotionEvent.ACTION_MOVE:

                gesturesState = GesturesState.GESTURES_STATE_MOVE;
                thumb = BitmapFactory.decodeResource(getResources(), R.drawable.seekbar_thumb_pressed);
                moveX = (int) event.getX();
                moveY = (int) event.getY();

                gMoveX = event.getX();
                gMoveY = event.getY();

                if (gMoveX < bitMapHeight-thumb.getWidth()/2 || gMoveX > lineWidth-thumb.getWidth()) {
                    break;
                }
//                responseTouch(moveX, moveY);
                responseTouchMove(gMoveX, gMoveY);
                break;
            case MotionEvent.ACTION_UP:
                gesturesState = GesturesState.GESTURES_STATE_UP;
                thumb = BitmapFactory.decodeResource(getResources(), R.drawable.seekbar_thumb_normal);
                upX = (int) event.getX();
                upY = (int) event.getY();
                responseTouch(upX, upY);
                responseOnTouch.onTouchResponse(cur_sections);
                break;
        }
        return true;
    }

    private void responseTouch(int x, int y) {
        if (x < width - bitMapHeight) {
            cur_sections = (x + perWidth / 2) / perWidth;
        } else {
            cur_sections = section_title.size() - 1;
        }
        invalidate();
    }

    private void responseTouchMove(float x, float y) {
        if (x <= width - bitMapHeight) {
            gMoveX = x;
        } else {
            cur_sections = section_title.size() - 1;
        }
        invalidate();
    }

    //设置监听
    public void setResponseOnTouch(ResponseOnTouch response) {
        responseOnTouch = response;
    }

    //设置进度
    public void setProgress(int progress) {
        cur_sections = progress;
        invalidate();
    }

    //activity实现了下面的接口ResponseOnTouch，每次touch会回调onTouchResponse
    public interface ResponseOnTouch {
        public void onTouchResponse(int volume);
    }
}
