package com.myapp.chat.ClinetServer;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.myapp.chat.R;
import org.json.JSONException;
import org.json.JSONObject;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class ClinetActivity extends AppCompatActivity {
    public RecyclerView myRecylerView ;
    public List<MessageAdapter> messageAdapterList;
    public ClinetAdapter clinetAdapter;
    public  EditText messagetxt ;
    public ImageButton send ;
    private Socket socket;
    public String Nickname ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_box);

        messagetxt = (EditText) findViewById(R.id.message_input) ;
        send = (ImageButton)findViewById(R.id.send_button);
        Nickname= (String)getIntent().getExtras().getString(HomeClinet.NICKNAME);

        try {
            socket = IO.socket("http://192.168.1.103:3000");
            socket.connect();
            socket.emit("join", Nickname);
        } catch (URISyntaxException e) {
            e.printStackTrace();

        }
        messageAdapterList = new ArrayList<>();
        myRecylerView = (RecyclerView) findViewById(R.id.messagesXXX);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        myRecylerView.setLayoutManager(mLayoutManager);
        myRecylerView.setItemAnimator(new DefaultItemAnimator());


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!messagetxt.getText().toString().isEmpty()){
                    socket.emit("messagedetection",Nickname,messagetxt.getText().toString());
                    messagetxt.setText("");
                }
            }
        });

        socket.on("userjoinedthechat", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String data = (String) args[0];

                        Toast.makeText(ClinetActivity.this,data,Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });
        socket.on("userdisconnect", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String data = (String) args[0];

                        Toast.makeText(ClinetActivity.this,data,Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });
        socket.on("message", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        JSONObject data = (JSONObject) args[0];
                        try {
                            String nickname = data.getString("senderNickname");
                            String message = data.getString("message");
                            MessageAdapter m = new MessageAdapter(nickname,message);
                            messageAdapterList.add(m);
                            clinetAdapter = new ClinetAdapter(messageAdapterList);
                            clinetAdapter.notifyDataSetChanged();
                            myRecylerView.setAdapter(clinetAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                });
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        socket.disconnect();
    }
}