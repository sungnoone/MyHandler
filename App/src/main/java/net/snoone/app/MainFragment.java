package net.snoone.app;

import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.logging.Handler;
import java.util.logging.LogRecord;

/**
 * Created by hl on 2013/11/28.
 */
public class MainFragment extends Fragment {

    CountToTen countToTen;
    TextView txtCount;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Log.i("test", "MyHandler onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        return rootView;
    }

    @Override
    public void onStart(){
        super.onStart();
        txtCount = (TextView)getView().findViewById(R.id.textView);
        countToTen = new CountToTen();
        countToTen.start();

    }

    public android.os.Handler mHandler = new android.os.Handler(){
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
                    countBundle.putString("message", "數字增加中..."+String.valueOf(i));
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
