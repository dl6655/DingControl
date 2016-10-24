package widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;

import com.dlingli.dingcontrol.R;

/**
 * Created by dlingli on 16/10/2.
 */
public class CustomSeekBar1 extends View {
    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;

    private Drawable mBackGroundDrawable;
    private Drawable mProgressDrawable;
    private Drawable mThumbDrawable;

    private int mOrientation = 0;

    private int mProgressWidth = 0;
    private int mThumbWidth = 0;
    private int maxProgress = 100;

    private int mProgress = 50;

    private OnSeekBarChangeListener mSeekBarChangeListener;

    public CustomSeekBar1(Context context) {
        this(context, null);
    }

    public CustomSeekBar1(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomSeekBar1(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.windows, defStyle, 0);
        mBackGroundDrawable = a
                .getDrawable(R.styleable.windows_background1);
        mProgressDrawable = a.getDrawable(R.styleable.windows_progress);
        mThumbDrawable = a.getDrawable(R.styleable.windows_thumb);
        mProgressWidth = a.getDimensionPixelSize(
                R.styleable.TvProgressBar_progressWidth, 0);
        mThumbWidth = a.getDimensionPixelSize(
                R.styleable.TvProgressBar_thumbWidth, 0);
        mOrientation = a.getInteger(R.styleable.TvProgressBar_orientation, 0);
        a.recycle();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();
        if (width <= 0 || height <= 0) {
            return;
        }

        float rate = ((float) mProgress) / maxProgress;

        // 画背景
        if (mBackGroundDrawable != null) {
            int bgleft = 0,bgright=0,bgtop=0,bgbottom=0;
            if (mOrientation == HORIZONTAL){
                bgleft = mThumbWidth / 2;
                bgright = width - mThumbWidth / 2;
                bgtop = (height - mProgressWidth) / 2;
                bgbottom = bgtop + mProgressWidth;
            }else if (mOrientation == VERTICAL){
                bgleft = (width - mProgressWidth) / 2;
                bgright = bgleft + mProgressWidth;
                bgtop = mThumbWidth / 2;
                bgbottom = height - mThumbWidth / 2;
            }
            mBackGroundDrawable.setBounds(bgleft, bgtop, bgright, bgbottom);
            mBackGroundDrawable.draw(canvas);
        }

        // 画进度条
        if (mProgressDrawable != null) {
            int progressleft=0,progressright=0,progresstop=0,progressbottom=0;
            if (mOrientation == HORIZONTAL){
                progressleft = mThumbWidth / 2;
                progressright = (int) (progressleft + rate
                        * (width - mThumbWidth));
                progresstop = (height - mProgressWidth) / 2;
                progressbottom = progresstop + mProgressWidth;
            }else if (mOrientation == VERTICAL){
                progressleft = (width - mProgressWidth) / 2;
                progressright = progressleft + mProgressWidth;
                progressbottom = height - mThumbWidth / 2;
                progresstop = (int) (progressbottom - rate
                        * (height - mThumbWidth));
            }
            mProgressDrawable.setBounds(progressleft, progresstop,
                    progressright, progressbottom);
            mProgressDrawable.draw(canvas);
        }

        // 画滑块
        if (mThumbDrawable != null) {
            int thumbleft=0,thumbright=0,thumbtop=0,thumbbottom=0;
            if (mOrientation == HORIZONTAL){
                thumbleft = (int) ((width - mThumbWidth) * rate);
                thumbright = thumbleft + mThumbWidth;
                thumbtop = (height - mThumbWidth) / 2;
                thumbbottom = thumbtop + mThumbWidth;
            }else if (mOrientation == VERTICAL){
                thumbleft = (width - mThumbWidth) / 2;
                thumbright = thumbleft + mThumbWidth;
                thumbbottom = (int) (height - rate * (height - mThumbWidth));
                thumbtop = thumbbottom - mThumbWidth;
            }
            mThumbDrawable.setBounds(thumbleft, thumbtop, thumbright,
                    thumbbottom);
            mThumbDrawable.draw(canvas);
        }

    }

    public void setOnSeekBarChangeListener(OnSeekBarChangeListener listener) {
        mSeekBarChangeListener = listener;
    }

    public interface OnSeekBarChangeListener {
        public abstract void onProgressChanged();

    }

    public void setOrientation(int orientation){
        mOrientation = orientation;
        invalidate();
    }

    public void setProgress(int progress) {

        if (progress < 0 || progress > maxProgress) {
            return;
        }

        mProgress = progress;
        invalidate();
        if (mSeekBarChangeListener != null) {
            mSeekBarChangeListener.onProgressChanged();
        }

    }

    public int getProgress() {
        return mProgress;
    }

    public int getMaxProgress() {
        return maxProgress;
    }

    public boolean increase() {
        if (mProgress < maxProgress) {
            mProgress++;
            setProgress(mProgress);
            return true;
        }
        return false;
    }

    public boolean reduce() {
        if (mProgress > 0) {
            mProgress--;
            setProgress(mProgress);
            return true;
        }
        return false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (mOrientation == HORIZONTAL) {
            if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
                if (mProgress > 0) {
                    mProgress--;
                    setProgress(mProgress);
                    return true;
                }

            }

            if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
                if (mProgress < maxProgress) {
                    mProgress++;
                    setProgress(mProgress);
                    return true;
                }

            }
        }

        if (mOrientation == VERTICAL) {

            if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
                if (mProgress > 0) {
                    mProgress--;
                    setProgress(mProgress);
                    return true;
                }

            }

            if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
                if (mProgress < maxProgress) {
                    mProgress++;
                    setProgress(mProgress);
                    return true;
                }

            }

        }

        return super.onKeyDown(keyCode, event);
    }

}
