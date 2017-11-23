package com.android.www.android_text;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.animation.AccelerateInterpolator;
import android.widget.Toast;

import com.android.www.android_text.view.BBBB;
import com.android.www.android_text.view.WaveView;

import java.io.File;
import java.io.FileInputStream;

public class MainActivity extends AppCompatActivity {

    private CountDownTimer timer;
    private WaveView waveView;
    private BBBB bbbb;

    private int mint=2;
    private Manager mg;

    Handler  handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);


        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        waveView = (WaveView)findViewById(R.id.yuan);

        bbbb = (BBBB)findViewById(R.id.bbbb);

        waveView.setDuration(5000);
        //waveView.setStyle(Paint.Style.STROKE);
        waveView.setStyle(Paint.Style.FILL);
        waveView.setSpeed(400);
        waveView.setColor(Color.parseColor("#ff0000"));
        waveView.setInterpolator(new AccelerateInterpolator(1.2f));
        waveView.start();

        timer = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long l) {
            }

            @Override
            public void onFinish() {
                waveView.stop();

                bbbb.stp();
            }

        };


        String x = new String("goeasyway");
        change(x);
        System.out.println(x);

        timer.start();


//        if (mg == null){
//            mg=new Manager();
//        }
//
//        mg.cc();


        File file=new File(Environment.getExternalStorageDirectory(),"txt.text");

        try {

            FileInputStream fileInputStream=new FileInputStream(file);

            int i=fileInputStream.read();


        } catch (Exception e) {

            e.printStackTrace();

        }
         finally {

            Log.i("dengpoa", "onCreate: ");

        }


        new Thread(new Runnable() {
            @Override
            public void run() {


            }

        }).start();

    }

    public static void change(String x) {
        x = "even";
    }

    private long mCreateTime;

    public int getAlpha() {
        float percent = (System.currentTimeMillis() - mCreateTime) * 1.0f / 2000;
        return (int) ((1.0f - percent) * 255);
    }


    class Manager{

        int m=0;

        void cc(){

            mint=mint++;

            m=mint++;

            Toast.makeText(MainActivity.this, String.valueOf(mint++)+"+", Toast.LENGTH_SHORT).show();
            Toast.makeText(MainActivity.this, String.valueOf(m)+"=", Toast.LENGTH_SHORT).show();
        }

    }

}
