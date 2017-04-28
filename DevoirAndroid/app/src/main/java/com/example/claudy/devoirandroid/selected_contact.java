package com.example.claudy.devoirandroid;

import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class selected_contact extends AppCompatActivity {
    FloatingActionButton btnDelete;
    FloatingActionButton btnmodify;
    DatabaseHelper mDatabaseHelper;
    private static final String TAG = "selected_contact";


    private String selectedName;
    private String selectedPrenom;
    private String selectedEmail;
    private String selectedTelephone;
    private String selectedAdresse;
    private String selectedStatut;
    private String seleectedID;

    private TextView selnom;
    private TextView selprenom;
    private TextView seladr;
    private TextView seltel;
    private TextView selmail;
    private TextView selstatut;

    private int selectedID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.selected_contact);
        btnmodify = (FloatingActionButton) findViewById(R.id.btnmodify);

        btnDelete = (FloatingActionButton) findViewById(R.id.btndelete);
        mDatabaseHelper = new DatabaseHelper(this);

        selnom = (TextView) findViewById(R.id.selnomtxt);
        selprenom = (TextView) findViewById(R.id.selprenomtxt);
        seladr = (TextView) findViewById(R.id.seladrtxt);
        seltel = (TextView) findViewById(R.id.selteltxt);
        selmail = (TextView) findViewById(R.id.selmailtxt);
        selstatut = (TextView) findViewById(R.id.selstatuttxt);

        Intent receivedIntent = getIntent();
        selectedID = receivedIntent.getIntExtra("id",-1); //NOTE: -1 is just the default value
        selectedName = receivedIntent.getStringExtra("name");
        selectedPrenom = receivedIntent.getStringExtra("prenom");
        selectedEmail = receivedIntent.getStringExtra("mail");
        selectedTelephone = receivedIntent.getStringExtra("telephone");
        selectedAdresse = receivedIntent.getStringExtra("adresse");
        selectedStatut = receivedIntent.getStringExtra("statut");
        seleectedID = String.valueOf(selectedID);

        selnom.setText(selectedName);
        selprenom.setText(selectedPrenom);
        seladr.setText(selectedEmail);
        seltel.setText(selectedTelephone);
        selmail.setText(selectedAdresse);
        selstatut.setText(selectedStatut);

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabaseHelper.deleteName(seleectedID);
                selnom.setText("");
               selprenom.setText("");
                seladr.setText("");
                seltel.setText("");
                selmail.setText("");
                selstatut.setText("");
                toastMessage("removed from database");
            }
        });

        btnmodify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onItemClick: You Clicked on " + selectedName);

                Cursor data = mDatabaseHelper.getItemID(selectedName); //get the id associated with that name
                int itemID = -1;
                while(data.moveToNext()){
                    itemID = data.getInt(0);
                }
                if(itemID > -1){
                    Log.d(TAG, "onItemClick: The ID is: " + itemID);
                    Intent editScreenIntent = new Intent(selected_contact.this, edit_contact.class);
                    editScreenIntent.putExtra("id",itemID);
                    editScreenIntent.putExtra("name",selectedName);
                    editScreenIntent.putExtra("prenom",selectedPrenom);
                    editScreenIntent.putExtra("mail",selectedEmail);
                    editScreenIntent.putExtra("telephone",selectedTelephone);
                    editScreenIntent.putExtra("adresse",selectedAdresse);
                    editScreenIntent.putExtra("statut",selectedStatut);
                    startActivity(editScreenIntent);
                }
                else{
                    toastMessage("No ID associated with that name");
                }
            }
        });




    }

 /*   public  void modifyev (View view)

    {
        Intent intent = new Intent(selected_contact.this, edit_contact.class);
        intent.putExtra("id",selectedID);
        startActivity(intent);

    } */





    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
}
