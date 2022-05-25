package com.myapp.chat.OnlineServer.Adapter;

import android.app.Application;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;


public class ChatApplication extends Application {

    private io.socket.client.Socket mSocket;
    {

            try {
                mSocket = IO.socket(Constants.CHAT_SERVER_URL);
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }


    }

    public Socket getSocket() {
        return mSocket;
    }




}
