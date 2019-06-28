package com.example.com.reminder;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class AddSchedule extends AppCompatActivity {

    TimePicker timePicker;
    Calendar calendar;
    Button done;
    int hour,minute;
    DatabaseHelper databaseHelper;
    EditText judul, subjudul;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedule);

        timePicker = findViewById(R.id.time);
        judul = findViewById(R.id.et_title);
        subjudul = findViewById(R.id.et_subtitle);
        done = findViewById(R.id.btn_done);
        calendar = Calendar.getInstance();

        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);

        databaseHelper = new DatabaseHelper(AddSchedule.this);

        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int i, int i1) {
                hour = i;
                minute = i1;
            }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int time1, time2;
                time1 = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)*60 + Calendar.getInstance().get(Calendar.MINUTE);
                time2 = hour*60 + minute;
                if(time2 <= time1){
                    Toast.makeText(AddSchedule.this, "Waktu telah berlalu", Toast.LENGTH_SHORT).show();
                }
                else
                if(judul.getText().toString().equals("") || subjudul.getText().toString().equals("")){
                    Toast.makeText(AddSchedule.this, "Title dan Subtitle tidak boleh kosong", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Intent intent = new Intent(AddSchedule.this,Main2Activity.class);
                    intent.putExtra("title", judul.getText().toString());
                    intent.putExtra("subtitle",subjudul.getText().toString());
                    PendingIntent pIntent = PendingIntent.getBroadcast(AddSchedule.this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
                    AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                    alarmManager.set(AlarmManager.RTC_WAKEUP,(System.currentTimeMillis()/60000*60000)+((time2-time1)*60000),pIntent);
                    String jam;
                    if(hour < 10 && minute < 10){
                        jam = String.format("0%d:0%d",hour,minute);
                    }
                    else
                    if(hour > 10 && minute <10){
                        jam = String.format("%d:0%d",hour,minute);
                    }
                    else
                    if(hour < 10 && minute > 10){
                        jam = String.format("0%d:%d",hour,minute);
                    }
                    else
                    {
                        jam = String.format("%d:%d",hour,minute);
                    }

                    databaseHelper.insertData(judul.getText().toString(), subjudul.getText().toString(), jam);
                    Intent i  = new Intent(AddSchedule.this,MainActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        });

    }
}
