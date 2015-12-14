package tools.cyc115.com.speedbutton;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.MediaRouteButton;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {


    private static final String TAG = MainActivity.class.getCanonicalName();
    private static final String USERIDExtra = "userid";
    @ViewById(R.id.user1_btn)
    Button user1;
    @ViewById(R.id.user2_btn)
    Button user2;
    @ViewById(R.id.set_ws_uri_btn)
    Button setWSUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        getDefaultWSURI();
    }

    /**
     * TODO gets the websocket uri from a webservice
     */
    @Background
    void getDefaultWSURI() {
        //TODO replace the result which is the uri "ws:// ..." obtained from the server
        String wsUri = "url of the static REST server";

        /*  commented out for test purpose
        RestTemplate template = new RestTemplate();
        template.getMessageConverters().add(new StringHttpMessageConverter());
        String result = template.getForObject(wsUri, String.class, "Android");

        */
        //TODO App.WS_URI = result;

        App.WS_URI = "ws://echo.websocket.org";

        //allow user selection when ws uri is obtained
        enableUserSelection(true);
    }

    @AfterViews
    void afterView(){
        enableUserSelection(false);
    }

    @UiThread
    void enableUserSelection(boolean b) {
        user1.setEnabled(b);
        user2.setEnabled(b);
    }

    @Click({R.id.user2_btn, R.id.user1_btn})
    void userBtnClicked(View view){

        if (view.getId() == R.id.user1_btn){
            App.currentUser= User.USER1;
        }
        else if (view.getId() == R.id.user2_btn){
            App.currentUser = User.USER2;
        }

        try {
            connectToServer(App.WS_URI);

        } catch (URISyntaxException e) {
            Log.e(TAG,"unable to connect to WebSocket");
            e.printStackTrace();
        }

        //start the game room activity
        RoomActivity_
                .intent(MainActivity.this).extra(USERIDExtra, App.currentUser )
                .start();
    }

    //TODO extract this to a service.
    private void connectToServer(String socketiouri ) throws URISyntaxException {

        if (socketiouri == null){
            throw new RuntimeException("uri is null");
        }

        /** commented out for test purpose
        final Socket socket = IO.socket(socketiouri);

        socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                socket.emit("user1", "hello world ");
                Log.i(TAG, "connected to server, starting game activity");
                //TODO start game activity
            }
        }).on(Socket.EVENT_MESSAGE, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                //TODO on message received

            }
        }).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                //TODO on disconnect
            }
        });

         **/


    }


}
