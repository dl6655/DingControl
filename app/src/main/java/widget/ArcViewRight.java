package widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by dlingli on 16/10/7.
 */
public class ArcViewRight extends View {


    public ArcViewRight(Context context) {
        super(context);
    }

    public ArcViewRight(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ArcViewRight(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void init(Context context, AttributeSet attrs){

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint=new Paint();
        paint.setAntiAlias(true);                       //设置画笔为无锯齿
        paint.setColor(Color.RED);                    //设置画笔颜色
        canvas.drawColor(Color.WHITE);                  //白色背景
        paint.setStrokeWidth((float) 3.0);              //线宽
        paint.setStyle(Paint.Style.STROKE);

        RectF oval=new RectF(0,0,50,150);                 //RectF对象
        canvas.drawArc(oval, -90, 180, false, paint);    //绘制圆弧

    }
}










