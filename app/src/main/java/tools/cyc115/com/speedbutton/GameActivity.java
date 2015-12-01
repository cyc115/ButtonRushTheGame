package tools.cyc115.com.speedbutton;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.support.annotation.UiThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

@EActivity(R.layout.activity_game)
public class GameActivity extends AppCompatActivity {
    private static final String TAG = GameActivity_.class.getCanonicalName();
    private TransitionDrawable crossfader;

    @ViewById(R.id.GameBtn1)
    ImageView buttonImg1;
    @ViewById(R.id.GameBtn2)
    ImageView buttonImg2;
    @ViewById(R.id.GameBtn3)
    ImageView buttonImg3;


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

        setContentView(R.layout.activity_game);
        Log.d(TAG, "CREATED");
    }

    public void buttonDownTasks(int buttonNum){
        //TODO add code that each button has to do on the down click. The functions are already in their place
    }

    public void buttonUpTasks(int buttonNum){
        //TODO add code that each button has to do on the up click. The functions are already in their place
    }

    @Touch({R.id.GameBtn1})
    public void onButton1Touch (View view, MotionEvent event){
        int actionID = event.getActionMasked();


        switch (actionID){
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "ACTION_DOWN");
                //process stuff
                buttonDownTasks(1);
                //start the animation
                startBtnAnimation(buttonImg1);
                break;
            case MotionEvent.ACTION_UP:
                Log.d(TAG, "ACTION_UP");
                //Process stuff
                buttonUpTasks(1);
                stopButtonAnimation();
                break;
            case MotionEvent.ACTION_MOVE: // do nothing for move
                break;
            default:
                Log.e(TAG, "action performed on btn is not recognized : " + actionID);
                Toast.makeText(this, "action not recognized", Toast.LENGTH_SHORT);
        }
    }
    @Touch({R.id.GameBtn2})
    public void onButton2Touch (View view, MotionEvent event){
        int actionID = event.getActionMasked();


        switch (actionID){
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "ACTION_DOWN");
                //Process stuff
                buttonDownTasks(2);
                //start the animation
                startBtnAnimation(buttonImg2);
                break;
            case MotionEvent.ACTION_UP:
                Log.d(TAG, "ACTION_UP");
                //Process stuff
                buttonUpTasks(2);
                stopButtonAnimation();
                break;
            case MotionEvent.ACTION_MOVE: // do nothing for move
                break;
            default:
                Log.e(TAG, "action performed on btn is not recognized : " + actionID);
                Toast.makeText(this, "action not recognized", Toast.LENGTH_SHORT);
        }
    }

    @Touch({R.id.GameBtn3})
    public void onButton3Touch (View view, MotionEvent event){
        int actionID = event.getActionMasked();


        switch (actionID){
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "ACTION_DOWN");
                //Process Stuff
                buttonDownTasks(3);
                //start the animation
                startBtnAnimation(buttonImg3);
                break;
            case MotionEvent.ACTION_UP:
                Log.d(TAG, "ACTION_UP");
                buttonUpTasks(3);
                stopButtonAnimation();
                break;
            case MotionEvent.ACTION_MOVE: // do nothing for move
                break;
            default:
                Log.e(TAG, "action performed on btn is not recognized : " + actionID);
                Toast.makeText(this, "action not recognized", Toast.LENGTH_SHORT);
        }
    }


    /**
     * start the btn change color animation
     * <br> runs on the ui thread
     *
     */
    @UiThread
    private void startBtnAnimation(ImageView buttonImg) {
        Log.i(TAG, "start Button Animation");

        Drawable img[] = new Drawable[2];
        Resources res = getResources();

        img[0] = res.getDrawable(R.drawable.round_btn);
        img[1] = res.getDrawable(R.drawable.round_btn_pressed);

        crossfader = new TransitionDrawable(img);

        buttonImg.setImageDrawable(crossfader);
        crossfader.setCrossFadeEnabled(true);
        crossfader.startTransition(300);
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


}
