package widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.SeekBar;
import com.dlingli.dingcontrol.R;

/**
 * Created by dingli on 16-10-11.
 */
public class RotationSeekbarThumb extends SeekBar implements SeekBar.OnSeekBarChangeListener{
    private int mPerAverageAngle = 0;
    private float mScaleX = 1.0f, mScaleY = 1.0f;
    private boolean mIsCycle = false;
    public RotationSeekbarThumb(Context context) {
        super(context);
    }

    public RotationSeekbarThumb(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RotationSeekbarThumb(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * set animation of thumb;
     * use:eg
     *  public void onStopTrackingTouch(SeekBar seekBar)
     *  setThumbRotationAnimation(null);
     */
    public void setThumbRotationAnimation(Drawable drawable){
        mPerAverageAngle = 0;
        mScaleX = 1.0f;
        mScaleY = 1.0f;
        if(drawable==null){
            drawable=getResources().getDrawable(R.drawable.ic_launcher);
        }
        setThumb(drawable);
        rotationImage(getThumb());
    }

    public Drawable bitmapToDrawble(Bitmap bitmap, Context mcontext) {
        Drawable drawable = new BitmapDrawable(mcontext.getResources(), bitmap);
        return drawable;
    }

    public Bitmap drawableToBitamp(Drawable drawable) {
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
        if (mPerAverageAngle <360) {
            mIsCycle = true;
            mPerAverageAngle += 30;
            mScaleX -= 0.02;
            mScaleY -= 0.02;
            if (mScaleX < 0.1 || mScaleY < 0.1) {
                mScaleX = 0.1f;
                mScaleY = 0.1f;
            }
            matrix.postRotate(mPerAverageAngle);
            matrix.postScale(mScaleX, mScaleY); //长和宽放大缩小的比例
        } else {
            mIsCycle = false;
            matrix.postRotate(0);
            matrix.postScale(0.1f, 0.1f); //长和宽放大缩小的比例
        }
        Bitmap resizeBmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizeBmp;

    }

    public Drawable rotationView(Drawable drawable) {
        Bitmap bb = drawableToBitamp(drawable);
        bb = small(bb);
        return bitmapToDrawble(bb, getContext());

    }

    public void rotationImage(final Drawable drawable) {
        setThumb(rotationView(drawable));
        if (!mIsCycle) {
            setThumb(getResources().getDrawable(R.drawable.seekbar_thumb));
        }
        if (mIsCycle) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    rotationImage(drawable);
                }
            }, 10);
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }
}
