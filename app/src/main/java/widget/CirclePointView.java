package widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by dingli on 16-10-8.
 */
public class CirclePointView extends View {
    //创建圆形对象，半径为100
    private CircleInfo point = new CircleInfo(50);

    public CirclePointView(Context context) {
        super(context);
    }

    public CirclePointView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CirclePointView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setAntiAlias(true);
        if(point.getRadius()>50){
            paint.setStyle(Paint.Style.FILL);
        }else{
            paint.setStyle(Paint.Style.STROKE);
        }

        //以300，300为圆心，以当前圆的半径为半径，画一个圆
        canvas.drawCircle(300,300, point.getRadius(), paint);

        super.onDraw(canvas);
    }
    public void setPointRadius(float radius){
        point.setRadius(radius);
        invalidate();
    }

     class CircleInfo {
        private float radius;

        public CircleInfo(float radius) {
            this.radius = radius;
        }

        public void setRadius(float radius) {
            this.radius = radius;
        }

        public float getRadius() {
            return radius;
        }
    }

}
