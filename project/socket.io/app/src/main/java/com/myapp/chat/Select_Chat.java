package com.myapp.chat;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.text.format.Formatter;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.myapp.chat.ClinetServer.HomeClinet;
import com.myapp.chat.OnlineServer.MainActivity;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

public class Select_Chat extends AppCompatActivity {

    private TextView ConectedWith , PingInfo , IPInfo;
    private ProgressBar progressBar;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select__chat);

        ConectedWith = (TextView)findViewById(R.id.ConectedWith);
        PingInfo = (TextView)findViewById(R.id.Ping);
        IPInfo = (TextView)findViewById(R.id.IpInfo);
        progressBar= (ProgressBar)findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

          ConnectivityManager connMgr = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connMgr != null) {
            NetworkInfo activeNetworkInfo = connMgr.getActiveNetworkInfo();

            if (activeNetworkInfo != null) {
                if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI)
                {

                    WifiManager wm = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
                    String ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());

                    IPInfo.setText("Ip Address is :"  + ip);

                    ConectedWith.setText("Conected With : WIFI");
                 }
                else
                    if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE)
                {
                    ConectedWith.setText("Conected With : Mobile Net");
                    String mobileIp = getMobileIPAddress();
                    String ip = Formatter.formatIpAddress(mobileIp.hashCode());
                    IPInfo.setText("Ip Address is :"  + ip);


                }
            }
        }else {
            ConectedWith.setText("Conected With : NoConection");
        }




    }



    public static String getMobileIPAddress() {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                for (InetAddress addr : addrs) {
                    if (!addr.isLoopbackAddress()) {
                        return  addr.getHostAddress();
                    }
                }
            }
        } catch (Exception ex) { }

        return "";
    }




    public void RandomServer (View view){
        progressBar.setVisibility(View.VISIBLE);
        Intent intent = new Intent(this , MainActivity.class);
        startActivity(intent);
        progressBar.setVisibility(View.INVISIBLE);
    }

    public void CusomServer (View view){
        progressBar.setVisibility(View.VISIBLE);

        Intent intent = new Intent(this , HomeClinet.class);
        progressBar.setVisibility(View.INVISIBLE);
        startActivity(intent);
    }

    public void Finish(View view) {
        this.finish();
    }
}
