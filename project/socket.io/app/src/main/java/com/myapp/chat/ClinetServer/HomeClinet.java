package com.myapp.chat.ClinetServer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.myapp.chat.R;


public class HomeClinet extends AppCompatActivity {


    private Button btn;
    private EditText nickname;
    public static final String NICKNAME = "usernickname";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        btn = (Button) findViewById(R.id.enterchat) ;
        nickname = (EditText) findViewById(R.id.nickname);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!nickname.getText().toString().isEmpty()){
                    Intent i  = new Intent(HomeClinet.this, ClinetActivity.class);
                    i.putExtra(NICKNAME,nickname.getText().toString());
                    startActivity(i);
                }
            }
        });

    }
}