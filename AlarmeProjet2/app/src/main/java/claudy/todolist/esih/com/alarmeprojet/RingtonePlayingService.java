package claudy.todolist.esih.com.alarmeprojet;

/**
 * Created by CLAUDY on 3/17/2017.
 */

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class RingtonePlayingService extends Service {

    private boolean isRunning;
    private Context context;
    MediaPlayer mMediaPlayer;
    private int startId;

    @Override
    public IBinder onBind(Intent intent)
    {
        Log.e("MyActivity", "In the Richard service");
        return null;
    }

   /* public Map<String, String> getNotifications() {
        RingtoneManager manager = new RingtoneManager(this);
        manager.setType(RingtoneManager.TYPE_RINGTONE);
        Cursor cursor = manager.getCursor();

        Map<String, String> list = new HashMap<>();
        while (cursor.moveToNext()) {
            String notificationTitle = cursor.getString(RingtoneManager.TITLE_COLUMN_INDEX);
            String notificationUri = cursor.getString(RingtoneManager.URI_COLUMN_INDEX);

            list.put(notificationTitle, notificationUri);
        }

        return list;
    }*/
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {


        final NotificationManager mNM = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);

        Intent intent1 = new Intent(this.getApplicationContext(), MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent1, 0);

        Notification mNotify  = new Notification.Builder(this)
                .setContentTitle("Richard Dawkins is talking" + "!")
                .setContentText("Click me!")
                .setSmallIcon(R.drawable.ic_action_call)
                .setContentIntent(pIntent)
                .setAutoCancel(true)
                .build();

        String state = intent.getExtras().getString("extra");

        Log.e("what is going on here  ", state);

        assert state != null;
        switch (state) {
            case "no":
                startId = 0;
                break;
            case "yes":
                startId = 1;
                break;
            default:
                startId = 0;
                break;
        }

        // get richard's thing
        String richard_id = intent.getExtras().getString("quote id");
        Log.e("Service: richard id is " , richard_id);

        if(!this.isRunning && startId == 1) {
            Log.e("if there was not sound ", " and you want start");

            assert richard_id != null;
            if (richard_id.equals("0")) {

                int min = 1;
                int max = 9;

                Random r = new Random();
                int random_number = r.nextInt(max - min + 1) + min;
                Log.e("random number is ", String.valueOf(random_number));

                if (random_number == 1) {
                    mMediaPlayer = MediaPlayer.create(this, R.raw.richard_dawkins_1);
                }
                else if (random_number == 2) {
                    mMediaPlayer = MediaPlayer.create(this, R.raw.richard_dawkins_2);
                }
                else if (random_number == 3) {
                    mMediaPlayer = MediaPlayer.create(this, R.raw.richard_dawkins_3);
                }
                else if (random_number == 4) {
                    mMediaPlayer = MediaPlayer.create(this, R.raw.richard_dawkins_4);
                }
                else if (random_number == 5) {
                    mMediaPlayer = MediaPlayer.create(this, R.raw.richard_dawkins_5);
                }
                else if (random_number == 6) {
                    mMediaPlayer = MediaPlayer.create(this, R.raw.richard_dawkins_6);
                }
                else if (random_number == 7) {
                    mMediaPlayer = MediaPlayer.create(this, R.raw.richard_dawkins_7);
                }
                else if (random_number == 8) {
                    mMediaPlayer = MediaPlayer.create(this, R.raw.richard_dawkins_8);
                }
                else if (random_number == 9) {
                    mMediaPlayer = MediaPlayer.create(this, R.raw.richard_dawkins_9);
                }
                else {
                    mMediaPlayer = MediaPlayer.create(this, R.raw.richard_dawkins_1);
                }
            }
            else if (richard_id.equals("1")) {
                mMediaPlayer = MediaPlayer.create(this, R.raw.richard_dawkins_1);
            }
            else if (richard_id.equals("2")) {
                mMediaPlayer = MediaPlayer.create(this, R.raw.richard_dawkins_2);
            }
            else if (richard_id.equals("3")) {
                mMediaPlayer = MediaPlayer.create(this, R.raw.richard_dawkins_3);
            }
            else if (richard_id.equals("4")) {
                mMediaPlayer = MediaPlayer.create(this, R.raw.richard_dawkins_4);
            }
            else if (richard_id.equals("5")) {
                mMediaPlayer = MediaPlayer.create(this, R.raw.richard_dawkins_5);
            }
            else if (richard_id.equals("6")) {
                mMediaPlayer = MediaPlayer.create(this, R.raw.richard_dawkins_6);
            }
            else if (richard_id.equals("7")) {
                mMediaPlayer = MediaPlayer.create(this, R.raw.richard_dawkins_7);
            }
            else if (richard_id.equals("8")) {
                mMediaPlayer = MediaPlayer.create(this, R.raw.richard_dawkins_8);
            }
            else if (richard_id.equals("9")) {
                mMediaPlayer = MediaPlayer.create(this, R.raw.richard_dawkins_9);
            }
            else {
                mMediaPlayer = MediaPlayer.create(this, R.raw.richard_dawkins_1);
            }


            mMediaPlayer.start();

            mNM.notify(0, mNotify);

            this.isRunning = true;
            this.startId = 0;

        }
        else if (!this.isRunning && startId == 0){
            Log.e("if there was not sound ", " and you want end");

            this.isRunning = false;
            this.startId = 0;

        }

        else if (this.isRunning && startId == 1){
            Log.e("if there is sound ", " and you want start");

            this.isRunning = true;
            this.startId = 0;

        }
        else {
            Log.e("if there is sound ", " and you want end");

            mMediaPlayer.stop();
            mMediaPlayer.reset();

            this.isRunning = false;
            this.startId = 0;
        }


        Log.e("MyActivity", "In the service");

        return START_NOT_STICKY;
    }



    @Override
    public void onDestroy() {
        Log.e("JSLog", "on destroy called");
        super.onDestroy();

        this.isRunning = false;
    }


}
