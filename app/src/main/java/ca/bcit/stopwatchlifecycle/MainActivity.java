package ca.bcit.stopwatchlifecycle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private int seconds = 0;
    private boolean running;
    private boolean wasRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        runTimer();
        if (savedInstanceState != null) {
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
        }
    }


    // Start the stopwatch running when the Start button is clicked
    public void onClickStart(View v) {
        running = true;
    }

    // Stop the stopwatch running when the Stop button is clicked
    public void onClickStop(View v) {
        running = false;
    }

    // Reset the stopwatch when the Reset button is clicked
    public void onClickReset(View v) {
        running = false;
        seconds = 0;
    }
//    protected void onStop() {
//        super.onStop();
//        wasRunning = running;
//        running = false;
//    }
//    @Override
//    protected void onStart() {
//        super.onStart();
//        if (wasRunning) {
//            running = true;
//        }
//    }
    @Override
    protected void onPause() {
        super.onPause();
        wasRunning = running;
        running = false;
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (wasRunning) {
            running = true;
        }
    }


    private void runTimer() {
        final TextView timeView = (TextView) findViewById(R.id.time_view);
        final android.os.Handler handler = new android.os.Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = seconds/3600;
                int minutes = (seconds%3600)/60;
                int secs = seconds%60;
                String time = String.format(Locale.getDefault(),
                        "%d:%02d:%02d", hours, minutes, secs);
                timeView.setText(time);

                if (running)
                    seconds++;

                handler.postDelayed(this,1000);
            }
        });

    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("seconds", seconds);
        savedInstanceState.putBoolean("running", running);
        savedInstanceState.putBoolean("wasRunning", wasRunning);
    }

}


