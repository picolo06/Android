package claudy.todolist.esih.com.alarmeprojet;

/**
 * Created by CLAUDY on 3/17/2017.
 */

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.app.PendingIntent;
import android.database.Cursor;
import android.media.RingtoneManager;
import android.net.Uri;
import android.app.AlarmManager;
import android.renderscript.Sampler;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.ViewParent;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.StreamHandler;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;
import static android.content.Context.ALARM_SERVICE;
import static android.media.RingtoneManager.TYPE_RINGTONE;



public class   AdapterAlarme extends ArrayAdapter<Alarme> {

    Context context;
    int layoutResourceId;
     int startI;
    AlarmManager alarmManager;
    ArrayList<Alarme> alarmes = new ArrayList<Alarme>();
    PendingIntent pendingIntente;

    public AdapterAlarme(Context context, int layoutResourceId,
                         ArrayList<Alarme> alrms) {
        super(context, layoutResourceId, alrms);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.alarmes = alrms;
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
    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        View item = convertView;
        AlarmeWrapper AlarmeWrapper = null;


        if (item == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            item = inflater.inflate(layoutResourceId, parent, false);
            AlarmeWrapper = new AlarmeWrapper();
            AlarmeWrapper.timeAdd = (TextView) item.findViewById(R.id.timeAdd);
            AlarmeWrapper.dateAdd = (TextView) item.findViewById(R.id.dateAdd);
            AlarmeWrapper.etatAdd = (Button) item.findViewById(R.id.etatAdd);
            AlarmeWrapper.totalActif = (TextView) item.findViewById(R.id.totalActif);
            AlarmeWrapper.totalSave = (TextView) item.findViewById(R.id.totalSave);
            //AlarmeWrapper.edit = (Button) item.findViewById(R.id.btnEdit);
            AlarmeWrapper.delete = (Button) item.findViewById(R.id.btnDelete);
            AlarmeWrapper.spinner = (Spinner) item.findViewById(R.id.richard_spinner);
           // AlarmeWrapper.stop_alarm = (Button) item.findViewById(R.id.stop_alarm);

            item.setTag(AlarmeWrapper);
        } else {
            AlarmeWrapper = (AlarmeWrapper) item.getTag();
        }

        final Alarme alarme = alarmes.get(position);
        AlarmeWrapper.timeAdd.setText(alarme.getTime());
        AlarmeWrapper.dateAdd.setText(alarme.getDate());
        AlarmeWrapper.etatAdd.setText(alarme.getEtatAdd());
        /*assert AlarmeWrapper.etatAdd != null;
        switch (AlarmeWrapper.etatAdd) {
            case "OFF":
                startI = 0;
                break;
            case "ON":
                startI = 1;
                break;
            default:
                startI = 0;
                break;
        }
        if (startI == 1 )
        {

        }
        else
        {

        }*/

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,
                R.array.dawkins_sounds, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner

        AlarmeWrapper.spinner.setAdapter(adapter);
        //AlarmeWrapper.stop_alarm.setText((CharSequence) new RingtonePlayingService.(context, 0, 0));

       /* AlarmeWrapper.edit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Edit", Toast.LENGTH_LONG).show();
            }
        }); */

        final AdapterAlarme.AlarmeWrapper finalAlarmeWrapper = AlarmeWrapper;
        final AdapterAlarme.AlarmeWrapper finalAlarmeWrapper1 = AlarmeWrapper;
        AlarmeWrapper.etatAdd.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if(finalAlarmeWrapper1.etatAdd.getText()=="ON"){
                    alarme.setEtatAdd("OFF");
                    finalAlarmeWrapper.etatAdd.setText(alarme.getEtatAdd());
                    Toast.makeText(context, "Enregistrer nouveau Etat: "+alarme.getTime()+" \n Date: "+alarme.getDate()+"\n Etat: "+alarme.getEtatAdd()+"/\n position: "+alarmes.get(position), Toast.LENGTH_LONG).show();
                }else if(finalAlarmeWrapper1.etatAdd.getText()=="OFF"){
                    alarme.setEtatAdd("ON");
                    finalAlarmeWrapper.etatAdd.setText(alarme.getEtatAdd());
                    Toast.makeText(context, "Enregistrer nouveau Etat: "+alarme.getTime()+" \n Date: "+alarme.getDate()+"\n Etat: "+alarme.getEtatAdd()+"/\n position: "+alarmes.get(position), Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(context, "Enregistrer nouveau Etat", Toast.LENGTH_LONG).show();
                }
            }
        });

        AlarmeWrapper.delete.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                Toast.makeText(context, "Delete Time: "+alarme.getTime()+" \n Date: "+alarme.getDate()+"\n Etat: "+alarme.getEtatAdd()+"/\n position: "+alarmes.get(position), Toast.LENGTH_LONG).show();
                alarmes.remove(alarmes.get(position));

                notifyDataSetChanged();
            }
        });

        AlarmeWrapper.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent , View v, int position, long id) {
                //parent.getItemAtPosition(position);

               // Object k = parent.getItemAtPosition(position);
                Toast.makeText(context, "it s : "+parent.getItemAtPosition(position), Toast.LENGTH_LONG).show();
               // Uri uri = intent.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

       /* final long richard_id= AlarmeWrapper.spinner.getSelectedItemId();
        AlarmeWrapper.stop_alarm.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(context, AlarmReceiver.class);
               // intent.putExtra("extra", "no");

              // context.s
                //pendingIntent = PendingIntent.getBroadcast(AdapterAlarme.this, 0, intent, 0);


                //pendingIntent.cancel();
                //alarmeManager.cancel(pendingIntent);
                //Toast.makeText(context, "Arret en cours ...",Toast.LENGTH_LONG) .show();
                Intent u = new Intent(context, RingtonePlayingService.class);
               u.putExtra("extra", "no");
                u.putExtra("quote id", richard_id);
                //String extra="no";
               // Snackbar.make(v, "arret... ", Snackbar.LENGTH_LONG).setAction(context, in)).show();
                    //.setAction(context, position) ).show();

                pendingIntente = PendingIntent.getBroadcast(context, 0, u, FLAG_UPDATE_CURRENT);
                 alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
                context.sendBroadcast(u);



                Toast.makeText(context,"position: "+alarmes.get(position)+  "\n extra :  "+u.getExtras()+"\n quote_id  : "+richard_id+ "\n arret... ", Toast.LENGTH_LONG).show();

               // intent.putExtra("quote id", String.valueOf(finalAlarmeWrapper2.spinner.getLastVisiblePosition()));
               // context.sendBroadcast(intent);


            }
        });*/

        return item;

    }

    static class AlarmeWrapper {
        TextView timeAdd;
        TextView dateAdd;
        TextView totalActif;
        TextView totalSave;
        Button etatAdd;
        //Button edit;
        Button delete;
        Spinner spinner;
        //Button stop_alarm;

    }

}

