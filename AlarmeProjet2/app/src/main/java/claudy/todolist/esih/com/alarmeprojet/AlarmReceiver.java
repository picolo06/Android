package claudy.todolist.esih.com.alarmeprojet;

/**
 * Created by CLAUDY on 3/17/2017.
 */

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static android.media.RingtoneManager.TYPE_RINGTONE;


public class AlarmReceiver extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {

      /* Toast.makeText(context, "\t C'est l'heure...\n Reveillez vous!\n Reveillez vous! Reveillez vous!", Toast.LENGTH_LONG).show();
        //Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        Uri alarmUri = intent.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
      //  Uri alarmUri = MediaStore.Audio.Media.getContentUriForPath(.getAbsolutePath());
        if (alarmUri == null)
        {
            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        }

       /* public Map<String, String> getNotifications() {
        RingtoneManager manager = new RingtoneManager(context);
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
       // Uri uri = RingtoneManager.setActualDefaultRingtoneUri(AdapterAlarme.AlarmeWrapper.class,  TYPE_RINGTONE, alarmUri );
        //String ringTonePath = uri.toString();
        //Ringtone ringtone = RingtoneManager.getRingtone(context, alarmUri);

        //Ringtone ringtone = RingtoneManager.getRingtone(this, uri);
        //String title = ringtone.getTitle(context);
       // ringtone.play();
        String state = intent.getExtras().getString("extra");
        Log.e("MyActivity", "In the receiver with " + state);

        String richard_id = intent.getExtras().getString("quote id");
        Log.e("Richard quote is" , richard_id);

        Intent serviceIntent = new Intent(context,RingtonePlayingService.class);
        serviceIntent.putExtra("extra", state);
        serviceIntent.putExtra("quote id", richard_id);

        context.startService(serviceIntent);

    }


}

