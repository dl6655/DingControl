package widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by dlingli on 16/10/3.
 */
public class TextCounterView extends View implements View.OnClickListener{
    private Paint mPaint;

    private Rect mBounds;

    private int mCount;

    public TextCounterView(Context context) {
        super(context);
    }

    public TextCounterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBounds = new Rect();
        setOnClickListener(this);
    }

    public TextCounterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.BLUE);
        canvas.drawRect(0, 0, getWidth(), getHeight(), mPaint);
        mPaint.setColor(Color.YELLOW);
        mPaint.setTextSize(30);
        String text = String.valueOf(mCount);
        mPaint.getTextBounds(text, 0, text.length(), mBounds);
        float textWidth = mBounds.width();
        float textHeight = mBounds.height();
        canvas.drawText(text, getWidth() / 2 - textWidth / 2, getHeight() / 2
                + textHeight / 2, mPaint);
    }


    @Override
    public void onClick(View v) {
        mCount++;
        invalidate();
    }

    // 开始
//    private void startTimer(){
//        if(timerTask == null){
//            timerTask = new TimerTask() {
//                @Override
//                public void run() {
//                    tenMSecs++;
//                }
//            };
//            timer.schedule(timerTask,10,10);
//        }
//    }
//
//    // 结束
//    private void stopTimer(){
//        if(timerTask != null){
//            timerTask.cancel();
//            timerTask = null;
//        }
//    }
//
//    // 取消计时
//    public void onDestory(){
//        timer.cancel();
//    }
//
//    private Handler handler = new Handler(){
//        public void handleMessage(Message msg){
//            switch (msg.what){
//                // 如果消息匹配，则将相应时间计算后显示在相应TextView上
//                case MSG_WHAT_SHOW_TIME:
//                    tvHour.setText(tenMSecs/100/60/60+);
//                    tvMinute.setText(tenMSecs/100/60%60+);
//                    tvSecond.setText(tenMSecs/100%60+);
//                    tvMSecond.setText(tenMSecs%100+);
//                    break;
//                default:
//                    break;
//            }
//        };
//    };


}
