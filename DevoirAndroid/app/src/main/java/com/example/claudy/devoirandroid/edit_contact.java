package com.example.claudy.devoirandroid;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class edit_contact extends AppCompatActivity {

    FloatingActionButton btnViewUpdate;
    DatabaseHelper mDatabaseHelper;
    EditText upnom;
    EditText upprenom;
    EditText upadr;
    EditText uptel;
    EditText upmail;
    EditText editTextId;
    EditText upstatut;
    private int selectedID;
    private String seleectedID;

    private String selectedName;
    private String selectedPrenom;
    private String selectedEmail;
    private String selectedTelephone;
    private String selectedAdresse;
    private String selectedStatut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_contact);
        mDatabaseHelper = new DatabaseHelper(this);
        upnom = (EditText) findViewById(R.id.enom);
        upprenom = (EditText) findViewById(R.id.eprenom);
        upadr = (EditText) findViewById(R.id.eadr);
        uptel = (EditText) findViewById(R.id.etel);
        upmail = (EditText) findViewById(R.id.email);
        upstatut = (EditText) findViewById(R.id.estat);
        //editTextId = (EditText) findViewById(R.id.edittId);


        btnViewUpdate = (FloatingActionButton) findViewById(R.id.btnViewUpdate);
        Intent receivedIntent = getIntent();
        selectedName = receivedIntent.getStringExtra("name");
        selectedPrenom = receivedIntent.getStringExtra("prenom");
        selectedEmail = receivedIntent.getStringExtra("mail");
        selectedTelephone = receivedIntent.getStringExtra("telephone");
        selectedAdresse = receivedIntent.getStringExtra("adresse");
        selectedStatut = receivedIntent.getStringExtra("statut");
        selectedID = receivedIntent.getIntExtra("id",-1); //NOTE: -1 is just the default value
        seleectedID = String.valueOf(selectedID);

        upnom.setText(selectedName);
        upprenom.setText(selectedPrenom);
        upadr.setText(selectedAdresse);
        uptel.setText(selectedTelephone);
        upmail.setText(selectedEmail);
        upstatut.setText(selectedStatut);

        btnViewUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String upsnom = upnom.getText().toString();
                String upsprenom = upprenom.getText().toString();
                String upsadr = upadr.getText().toString();
                String upstel = uptel.getText().toString();
                String upsmail = upmail.getText().toString();
                String upsstatut = upstatut.getText().toString();
                if(!upsnom.equals("")){
                    mDatabaseHelper.updateName(seleectedID,upsnom,upsprenom,upsmail,upstel,upsadr,upsstatut);
                    toastMessage("Dtata modified");
                }else{
                    toastMessage("You must enter a name");
                }
            }
        });
    }

    public  void cancelsv (View view)

    {
        Intent intent = new Intent(edit_contact.this, selected_contact.class);
        startActivity(intent);
    }

    public  void homesv (View view)

    {
        Intent intent = new Intent(edit_contact.this, MainActivity.class);
        startActivity(intent);
    }

    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }

    public  void mainpage (View view)

    {
        Intent intent = new Intent(edit_contact.this, MainActivity.class);
        startActivity(intent);
    }
}
