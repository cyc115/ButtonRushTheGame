package tools.cyc115.com.speedbutton;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.UiThread;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Touch;
import org.androidannotations.annotations.ViewById;

import java.util.Timer;
import java.util.TimerTask;

@EActivity
public class RoomActivity extends AppCompatActivity {

    private static final String TAG = RoomActivity_.class.getCanonicalName();

    Timer timer;
    MyTimerTask myTimerTask;

     @ViewById(R.id.big_ready_btn)
     ImageView buttonImg;

    @ViewById(R.id.infoTV)
    TextView buttonText;
    private TransitionDrawable crossfader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        setContentView(R.layout.activity_room);

        //TODO set the plus button
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "settings ", Snackbar.LENGTH_LONG)
                        .setAction("Settings", null).show();
            }
        });
    }

    /**
     * start the game activity
     * @param view
     */
    @Click({R.id.big_ready_btn , R.id.infoTV})
    public void startGameActivity(View view) {
        GameActivity_.intent(this).start();
    }

    @Touch({R.id.big_ready_btn , R.id.infoTV})
    public void onButtonTouch (View view, MotionEvent event){
        int actionID = event.getActionMasked();

        switch (actionID){
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "ACTION_DOWN");

                //start the animation
                startBtnAnimation();
                //set up the timer
                if(timer != null){
                    timer.cancel();
                }
                timer = new Timer();
                myTimerTask = new MyTimerTask();
                timer.schedule(myTimerTask, 1100);


                break;
            case MotionEvent.ACTION_UP:
                if(timer != null){
                    timer.cancel();
                    timer = null;
                }
                Log.d(TAG, "ACTION_UP");
                stopButtonAnimation();
                break;
            case MotionEvent.ACTION_MOVE: // do nothing for move
                break;
            default:
                Log.e(TAG, "action performed on btn is not recognized : " + actionID);
                Toast.makeText(this, "action not recognized" , Toast.LENGTH_SHORT);
        }
    }

    /**
     * start the btn change color animation
     * <br> runs on the ui thread
     *
     */
    @UiThread
    private void startBtnAnimation() {
        Log.i(TAG, "start Button Animation");

        Drawable img[] = new Drawable[2];
        Resources res = getResources();

        img[0] = res.getDrawable(R.drawable.round_btn);
        img[1] = res.getDrawable(R.drawable.round_btn_pressed);

        crossfader = new TransitionDrawable(img);

        buttonImg.setImageDrawable(crossfader);
        crossfader.setCrossFadeEnabled(true);
        crossfader.startTransition(1000);
    }

    /**
     * stop the btn change color animation
     * <br> runs on the ui thread
     */
    @UiThread
    private void stopButtonAnimation() {
        crossfader.startTransition(0);
        crossfader.resetTransition();
    }

    class MyTimerTask extends TimerTask {

        @Override
        public void run() {


            runOnUiThread(new Runnable(){

                @Override
                public void run() {
                    GameActivity_
                            .intent(RoomActivity.this)
                            .start();
                }});
        }

    }

}


