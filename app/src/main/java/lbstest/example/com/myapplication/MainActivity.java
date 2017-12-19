package lbstest.example.com.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import static android.widget.Toast.LENGTH_LONG;

public class MainActivity extends AppCompatActivity {
    ImageView mImageView;
    String TAG = "MainActivity";

    //使用Handler每隔2000毫秒检查wifi状态
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0){
                checkWifiState();
                sendEmptyMessageDelayed(0,2000);
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImageView =  findViewById(R.id.image);
        mHandler.sendEmptyMessageDelayed(0,2000);

    }


    //检查wifi是否连接状态
    public boolean isWifiConnect(){
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifiInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return mWifiInfo.isConnected();
    }

    //检查wifi强弱及更改图标显示
    public void checkWifiState(){
        if (isWifiConnect()){
            @SuppressLint("WifiManagerLeak") WifiManager mWifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
            WifiInfo mWifiInfo = mWifiManager.getConnectionInfo();
            int wifi = mWifiInfo.getRssi(); //获取wifi信号强度
            if(wifi > -50 && wifi<0){   //最强
                mImageView.setImageResource(R.drawable.wifi4);
                Log.d(TAG,"now level is 4");
            }else if (wifi > -70 && wifi < -50){//较强
                mImageView.setImageResource(R.drawable.wifi3);
                Log.d(TAG,"now level is 3");
            }else if(wifi > -80 && wifi < -70){ //较弱
                mImageView.setImageResource(R.drawable.wifi2);
                Log.d(TAG,"now level is 2");
            }else {
                mImageView.setImageResource(R.drawable.wifi1);
                Log.d(TAG,"now level is 1");
            }

        }
    }


}
