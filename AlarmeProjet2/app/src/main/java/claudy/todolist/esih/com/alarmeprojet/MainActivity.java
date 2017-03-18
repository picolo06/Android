package claudy.todolist.esih.com.alarmeprojet;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import android.content.Context;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.os.Looper;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.DigitalClock;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;

public class MainActivity extends AppCompatActivity {
    ListView listview;

    DigitalClock nowTime;
    TextView lastAlarm;
    TextView totalActif;
    TextView totalSave;
    Button stopAlarm;
    Button stop_alarm;

    AdapterAlarme alarmArrayAdapter;
    ArrayList<Alarme> alarmArray = new ArrayList<Alarme>();

    Calendar c;
    int hour;
    int minute;
    int tSave = 0;



    String hZero;
    String mZero;
    SimpleDateFormat simpleDateFormat;
    SimpleDateFormat simpleHeureFormat;
    String formatDate;
    String formatHeure;
    PendingIntent pendingIntent;
    AlarmManager alarmManager;
    int alarmUsed;
    private static final int Time_id = 1;
    int richard_quote = 0;

    FloatingActionButton fab;
    Toolbar toolbar;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        //Parcelable state = listAlarm.onSaveInstanceState();
        savedInstanceState.putInt("click_count", tSave);
//save array list
//savedInstanceState.putStringArrayList("ITEM_ID_LIST", alarmArray);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*public Map<String, String> getNotifications() {
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
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//Get The Time Instance in , use to set TimePicker Dialog by Time Now
        c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);

//Get All View in XML
        totalActif = (TextView) findViewById(R.id.totalActif);
        totalSave = (TextView) findViewById(R.id.totalSave);
        lastAlarm = (TextView) findViewById(R.id.lastAlarm);
        stopAlarm = (Button) findViewById(R.id.stopAlarm);
        stopAlarm.setVisibility(View.GONE);
        fab = (FloatingActionButton) findViewById(R.id.fab);

// add default Alarm Time with Info in array, default date add is set.
        alarmArray.add(new Alarme("09:00", "15-03-2017", "ON"));
        alarmArray.size();
        totalActif.setText(alarmArray.size() + "Actifs");
        totalSave.setText(alarmArray.size() + "Enregistrees");


// set the array adapter to use the above array list and tell the listview to set as the adapter
// our custom adapter
        alarmArrayAdapter = new AdapterAlarme(MainActivity.this, R.layout.list_alarm, alarmArray);
        listview = (ListView) findViewById(R.id.listAlarm);
        listview.setItemsCanFocus(false);
        listview.setAdapter(alarmArrayAdapter);

        listview.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    final int position, long id) {

                Toast.makeText(MainActivity.this,
                        "List Item Clicked:" + position, Toast.LENGTH_LONG)
                        .show();
                sendBroadcast(getIntent());
            }
        });

        /*stopAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);
                intent.putExtra("extra", "no");
                intent.putExtra("quote id", String.valueOf(richard_quote));
                sendBroadcast(intent);
                //Snackbar.make(view, "arret... ", Snackbar.LENGTH_LONG)
                 //       .setAction("Action", null).show();
                pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                //alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
               alarmManager.cancel(pendingIntent);
                pendingIntent.cancel();

                //alarmManager.cancel(pendingIntent);
                //alarmManager.cancel(pendingIntent);

            }
        });*/

        Button stop_alarm;
        stop_alarm = (Button) findViewById(R.id.stop_alarm);
        stop_alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 Intent intente = new Intent(MainActivity.this, AlarmReceiver.class);
                intente.putExtra("extra", "no");
                intente.putExtra("quote id", String.valueOf(richard_quote));
                sendBroadcast(intente);


                PendingIntent pendingIntentee = PendingIntent.getBroadcast(MainActivity.this, 0, intente, FLAG_UPDATE_CURRENT);
                alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

                //pendingIntentee.cancel();

               // alarmManager.cancel(pendingIntentee);
               // sendBroadcast(intente);

                //setAlarmText("ID is " + richard_quote);
            }
        });

// Set onClick on Floatting Button to open Dialog
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(Time_id);

            }
        });

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        timerMethod();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    //Create Dialog for TimePicker or DatePicker
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case Time_id:
// Open the timepicker dialog
                return new TimePickerDialog(MainActivity.this, time_listener, hour,
                        minute, false);
        }
        return null;
    }

    //THe TimePicker Dialog Action ,
    TimePickerDialog.OnTimeSetListener time_listener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hour, int minute) {
            if (hour < 10) {
                hZero = 0 + String.valueOf(hour);
            } else {
                hZero = String.valueOf(hour);
            }
            if (minute < 10) {
                mZero = 0 + String.valueOf(minute);
            } else {
                mZero = String.valueOf(minute);
            }
// store the data in one string and set it to text
            String time1 = hZero + ":" + mZero;
//SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
            simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
            formatDate = simpleDateFormat.format(new Date());
            alarmArray.add(new Alarme(time1, formatDate, "ON"));
            alarmArray.size();
            lastAlarm.setText(time1);
            totalActif.setText(alarmArray.size() + " Actifs");
            totalSave.setText(alarmArray.size() + "Enregistrees");

            alarmArrayAdapter.notifyDataSetChanged();
            //Snackbar.make(view, "Alarme Enregistrer: "+ time1, Snackbar.LENGTH_LONG)
            //.setAction("Action", null).show();
        }
    };

    //Use Timer to reload app every 30 milliseconde
//
    Timer reload = new Timer();

    void timerMethod() {
        reload.schedule(new TimerTask() {
            public void run() {
                Log.d("timer", "timer ");
// Get the calander
//Boolean t=alarmArray.contains("itemsValueInArray);

//Get HHours and Minutes
                simpleHeureFormat = new SimpleDateFormat("HH:mm");
                formatHeure = simpleHeureFormat.format(new Date());
//use Date Instance to get Hours , Minutes and seconds
                int heu = new Date().getHours();
                int min = new Date().getMinutes();
                int sec = new Date().getSeconds();
                int result = 1;
//Conversion en millisegonde
                if (heu != 0) {
                    heu = heu * 60 * 60 * 1000;
                } else {
                    heu = 0;
                }
                if (min != 0) {
                    min = min * 60 * 1000;
                } else {
                    min = 0;
                }
                if (sec != 0) {
                    sec = sec * 1000;
                } else {
                    sec = 0;
                }
                result = heu + min + sec;

// Loop to compare is Time Now is equals to Time in Array (Time Alarm Set by user)

                for (int i = 0; i < alarmArray.size(); i++) {
                   final Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);
                   // pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, 0);
                   // alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                    if (alarmArray.get(i).getTime().equals(formatHeure) && alarmArray.get(i).getEtatAdd().equals("ON")) {
//alarm found

                        System.out.println("Touver : " + reload);
                        alarmArray.get(i).getTime();
                        alarmArray.get(i).getEtatAdd();
//                            Toast.makeText(getApplicationContext(),"C'est l'heure: "+formatHeure, Toast.LENGTH_LONG).show();
                        System.out.println("Touver: " + formatHeure + "\n Time Alarm: " + alarmArray.get(i).getTime() + "\n Time Etat: " + alarmArray.get(i).getEtatAdd());

                        //Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);

                        intent.putExtra("extra", "yes");
                        intent.putExtra("quote id", String.valueOf(richard_quote));
                        pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, FLAG_UPDATE_CURRENT);
                        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);



//can use to repeat Alarm every instance set
// alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, 0, 10000, pendingIntent);
//service.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), REPEAT_TIME, pending);
                        //or
//use to set alarm one time

                        alarmManager.set(AlarmManager.RTC_WAKEUP, 0, pendingIntent);
                    } else {
//If not Found
                         //Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);
                        //alarmArray.get(i).getTime();
                       // alarmArray.get(i).getEtatAdd();
                        System.out.println("Not found: " + formatHeure + "\n Time Alarm: " + alarmArray.get(i).getTime() + "\n Time Etat: " + alarmArray.get(i).getEtatAdd());

                       //Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);
                        //pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, 0);
                      //  alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

                        intent.putExtra("extra", "no");
                       intent.putExtra("quote id", String.valueOf(richard_quote));
                        //sendBroadcast(intent);

                        pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, FLAG_UPDATE_CURRENT);


                        //pendingIntent.cancel();
                       alarmManager.cancel(pendingIntent);
                        //sendBroadcast(intent);
                        //alarmManager.cancel(pendingIntent);
                       /* Button stop_alarm= (Button) findViewById(R.id.stop_alarm);
                        stop_alarm.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                intent.putExtra("extra", "no");
                                intent.putExtra("quote id", String.valueOf(richard_quote));
                                sendBroadcast(intent);

                                alarmManager.cancel(pendingIntent);


                                //setAlarmText("ID is " + richard_quote);
                            }
                        });*/
                        //alarmManager.set(AlarmManager.RTC_WAKEUP, 0, pendingIntent);

                    }
                }
            }
        }, 10000, 10000);
//1minutes = 60000 milliseconde
//0,0005minutes = 30 milliseconde
    }


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}

