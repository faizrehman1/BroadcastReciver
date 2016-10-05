package com.example.faiz.broadcastrecitask2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.support.v4.content.SharedPreferencesCompat;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private MyReceiverRef MyReceiverRef;
    TextView textView;
    boolean flag = false;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView)findViewById(R.id.retText);

        sharedPreferences = getSharedPreferences("myPref",MODE_WORLD_WRITEABLE);

        if(sharedPreferences.getString("name","")!=null){
         String name =  sharedPreferences.getString("name","");
            textView.setText(name);
        }

        // Receive broad Cast fromn External App
        IntentFilter intentFilter=new  IntentFilter("com.example.faiz.broadcastrecitask");
                MyReceiverRef =new MyReceiverRef();

        if(intentFilter!=null)
        {
            registerReceiver(MyReceiverRef,intentFilter);

        }



    }



    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(MyReceiverRef!= null){
            unregisterReceiver(MyReceiverRef);;
    }
    }

    public class MyReceiverRef extends WakefulBroadcastReceiver {
        String s;

        @Override
        public void onReceive(Context context, Intent intent) {
            s = intent.getStringExtra("KeyName");
            Log.d("TAG",s);
        textView.setText(s.toString());
SharedPreferences.Editor shared = sharedPreferences.edit();
            shared.putString("name",s.toString());
            shared.commit();
        }



    }
}
