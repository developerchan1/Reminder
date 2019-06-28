package com.example.com.reminder;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    Cursor cursor;
    FloatingActionButton FAB;
    DatabaseHelper databaseHelper;
    ListView schedule;
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor edit;
    String currentDate;
    String savedDate;
    TextView day,date;
    LinearLayout emptyActivity;
    String arrDay[] = {"Minggu","Senin","Selasa", "Rabu", "Kamis","Jumat","Sabtu"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Date c = Calendar.getInstance().getTime();
        int today = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);

        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

        FAB = findViewById(R.id.FAB);
        schedule = findViewById(R.id.lv_schedule);
        day = findViewById(R.id.tv_day);
        date = findViewById(R.id.tv_date);
        emptyActivity = findViewById(R.id.empty_activity);
        sharedPreferences = getSharedPreferences("date",Context.MODE_PRIVATE);
        edit = sharedPreferences.edit();

        savedDate = sharedPreferences.getString("savedDate","");
        currentDate = df.format(c);
        day.setText(arrDay[today-1]);
        date.setText(currentDate);

        databaseHelper = new DatabaseHelper(MainActivity.this);

        if(!currentDate.equals(savedDate)){
            databaseHelper.deleteAllData();
        }

        savedDate = currentDate;
        edit.putString("savedDate",savedDate);
        edit.commit();

        cursor = databaseHelper.getAllData();

        if(cursor!= null && cursor.getCount() > 0){
            while(cursor.moveToNext()){
                arrayList.add(cursor.getString(2));
            }
        }

        if(arrayList.size() == 0){
            emptyActivity.setVisibility(View.VISIBLE);
            schedule.setVisibility(View.GONE);
        }
        else
        {
            emptyActivity.setVisibility(View.GONE);
            schedule.setVisibility(View.VISIBLE);
        }

        arrayAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1,arrayList);
        schedule.setAdapter(arrayAdapter);


        FAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,AddSchedule.class);
                startActivity(i);
            }
        });
   
    }
}