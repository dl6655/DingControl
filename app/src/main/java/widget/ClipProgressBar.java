package widget;

import android.content.Context;
import android.graphics.drawable.ClipDrawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.dlingli.dingcontrol.R;


/**
 * Created by dlingli on 16/10/1.
 */
public class ClipProgressBar extends FrameLayout {
    public ClipProgressBar(Context context) {
        this(context,null,0);
    }

    public ClipProgressBar(Context context, AttributeSet attrs) {
        this(context,null,0);
    }

    public ClipProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
         init(context);
    }

    private boolean running;
    private int progress = 0;
    private static final int MAX_PROGRESS = 10000;

    private ClipDrawable clip;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(android.os.Message msg) {
            if(msg.what == 0x123)
                clip.setLevel(progress);
        }
    };

    public void init(Context context){
        View view = LayoutInflater.from(context).inflate(R.layout.cliprogress_bar, null);

        ImageView iv = (ImageView)view.findViewById(R.id.primary_button);

        addView(view);
        clip = (ClipDrawable)iv.getDrawable();

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                running = true;
                while(running){
                    handler.sendEmptyMessage(0x123);
                    if(progress == MAX_PROGRESS)
                        progress = 0;
                    progress += 100;
                    try {
                        Thread.sleep(18);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();
    }

    public void stop(){
        progress = 0;
        running = false;
    }



}
