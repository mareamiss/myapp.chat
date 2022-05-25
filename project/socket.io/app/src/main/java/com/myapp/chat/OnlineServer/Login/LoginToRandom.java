package com.myapp.chat.OnlineServer.Login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.myapp.chat.OnlineServer.Adapter.ChatApplication;
import com.myapp.chat.R;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import org.json.JSONException;
import org.json.JSONObject;


public class LoginToRandom extends Activity {
    private EditText mUsernameView;
    private String mUsername;
    private Socket mSocket;
    private Button signInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.random_server);


        ChatApplication app = (ChatApplication) getApplication();

        mSocket = app.getSocket();

        mUsernameView = (EditText) findViewById(R.id.username_input);



        signInButton  = (Button) findViewById(R.id.sign_in_button);

        signInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                attemptLogin();
            }
        });

        mSocket.on("login", onLogin);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mSocket.off("login", onLogin);

    }


    private void attemptLogin() {

        mUsernameView.setError(null);

        String username = mUsernameView.getText().toString().trim();


        if (TextUtils.isEmpty(username)) {

            mUsernameView.setError(getString(R.string.error_field_required));
            mUsernameView.requestFocus();
            return;
        }

        mUsername = username;

        mSocket.emit("add user", username);
    }

    private Emitter.Listener onLogin = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            JSONObject data = (JSONObject) args[0];

            int numUsers;
            try {
                numUsers = data.getInt("numUsers");
            } catch (JSONException e) {
                return;
            }

            Intent intent = new Intent();
            intent.putExtra("username", mUsername);
            intent.putExtra("numUsers", numUsers);
            setResult(RESULT_OK, intent);
            finish();
        }
    };
}



