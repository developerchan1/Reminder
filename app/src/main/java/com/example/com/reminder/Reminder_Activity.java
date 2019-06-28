package com.example.com.reminder;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Reminder_Activity extends AppCompatActivity {

    TextView tv_judul, tv_subjudul;
    Button back;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_reminder_);

        mediaPlayer = MediaPlayer.create(this, R.raw.analog_watch_alarm_daniel_simion);
        mediaPlayer.start();
        mediaPlayer.setLooping(true);
        String judul = getIntent().getExtras().getString("judul");
        String subJudul = getIntent().getExtras().getString("subjudul");

        tv_judul = findViewById(R.id.tv_judul);
        tv_subjudul = findViewById(R.id.tv_subjudul);
        back = findViewById(R.id.btn_back);

        tv_judul.setText(judul);
        tv_subjudul.setText(subJudul);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Reminder_Activity.this,MainActivity.class);
                startActivity(i);
                mediaPlayer.stop();
                finish();
            }
        });
    }
}
