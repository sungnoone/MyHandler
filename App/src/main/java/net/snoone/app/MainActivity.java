package net.snoone.app;

import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {

    CountToTen countToTen;
    TextView txtCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.container, new MainFragment()).commit();
        }
    }

    @Override
    public void onAttachFragment(Fragment fragment){
        super.onAttachFragment(fragment);
    }

    @Override
    public void onStart(){
        super.onStart();
        txtCount = (TextView)this.findViewById(R.id.textView);
        countToTen = new CountToTen();
        countToTen.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            txtCount.setText(txtCount.getText()+"\n"+Integer.toString(msg.getData().getInt("count", 0))+msg.getData().getString("message", ""));
        }
    };

    public class CountToTen extends Thread{
        @Override
        public void run(){
            super.run();
            try{
                for(int i=0 ; i<30 ; i++){
                    Thread.sleep(1000);
                    Log.i("MyHandler", String.valueOf(i));

                    Bundle countBundle = new Bundle();
                    countBundle.putInt("count", i+1);
                    countBundle.putString("message", "數字增加中"+String.valueOf(i));
                    Message msg = new Message();
                    msg.setData(countBundle);
                    mHandler.sendMessage(msg);
                }
            }catch (Exception e){
                Log.e("MyHandler", e.toString());
            }
        }
    }

}