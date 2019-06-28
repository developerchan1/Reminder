package com.example.com.reminder;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.INotificationSideChannel;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class Main2Activity extends BroadcastReceiver {

    String judul,subjudul;

    @Override
    public void onReceive(Context context, Intent intent) {
        judul = intent.getExtras().getString("title");
        subjudul = intent.getExtras().getString("subtitle");
        Toast.makeText(context, "TExt", Toast.LENGTH_SHORT).show();


        Intent intent1 = new Intent();
        intent1.setClassName(context.getPackageName(),Reminder_Activity.class.getName());
        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent1.putExtra("judul",intent.getExtras().getString("title"));
        intent1.putExtra("subjudul",intent.getExtras().getString("subtitle"));
        context.startActivity(intent1);

        NotificationCompat.Builder notification_builder;
                NotificationManager notification_manager = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    CharSequence name = "Reminder";
                    String description = "Message for task";
                    int importance = NotificationManager.IMPORTANCE_DEFAULT;
                    NotificationChannel mChannel = new NotificationChannel("chandra", name, importance);
                    mChannel.setDescription(description);
                    notification_manager = context.getSystemService(NotificationManager.class);
                    notification_manager.createNotificationChannel(mChannel);
                    notification_builder = new NotificationCompat.Builder(context, "chandra");

                } else {
                    notification_builder = new NotificationCompat.Builder(context);
                    notification_manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                }
                             notification_builder.setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(judul)
                        .setAutoCancel(true)
                        .setContentText(subjudul);

                notification_manager.notify(1,notification_builder.build());

    }
}
