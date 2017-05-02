package com.example.claudy.devoirandroid;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class edit_contact extends AppCompatActivity {
    public static final int IMAGE_REQUEST = 208;
    public static final int REQUEST_CODE = 2010;
    FloatingActionButton btnViewUpdate;
    DatabaseHelper mDatabaseHelper;
    EditText upnom;
    EditText upprenom;
    EditText upadr;
    EditText uptel;
    EditText upmail;
    EditText editTextId;
    EditText upstatut;
    ImageView upimage;
    private String selectedID;
    //private String seleectedID;

    private String selectedName;
    private String selectedPrenom;
    private String selectedEmail;
    private String selectedTelephone;
    private String selectedAdresse;
    private String selectedStatut;
    private String selectedImage;

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
        upimage = (ImageView) findViewById(R.id.epic);
        //editTextId = (EditText) findViewById(R.id.edittId);


        btnViewUpdate = (FloatingActionButton) findViewById(R.id.btnViewUpdate);
        Intent receivedIntent = getIntent();
        selectedName = receivedIntent.getStringExtra("name");
        selectedPrenom = receivedIntent.getStringExtra("prenom");
        selectedEmail = receivedIntent.getStringExtra("mail");
        selectedTelephone = receivedIntent.getStringExtra("telephone");
        selectedAdresse = receivedIntent.getStringExtra("adresse");
        selectedStatut = receivedIntent.getStringExtra("statut");
        selectedImage = receivedIntent.getStringExtra("image");
        selectedID = receivedIntent.getStringExtra("id"); //NOTE: -1 is just the default value


        upnom.setText(selectedName);
        upprenom.setText(selectedPrenom);
        upadr.setText(selectedAdresse);
        uptel.setText(selectedTelephone);
        upmail.setText(selectedEmail);
        upstatut.setText(selectedStatut);
        upimage.setImageBitmap(selected_contact.getPhoto(selectedImage.getBytes()));

        upimage.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
                    invopic();
                }else {
                    String[] premissionRequest= {android.Manifest.permission.READ_EXTERNAL_STORAGE};
                    requestPermissions(premissionRequest, REQUEST_CODE);

                }

            }
        });



        btnViewUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String upsnom = upnom.getText().toString();
                String upsprenom = upprenom.getText().toString();
                String upsadr = upadr.getText().toString();
                String upstel = uptel.getText().toString();
                String upsmail = upmail.getText().toString();
                String upsstatut = upstatut.getText().toString();
                Intent inteent = getIntent();
                String immagestring = inteent.getStringExtra("imaaggee");
                String upsimage = immagestring;
                if(!upsnom.equals("")){
                    mDatabaseHelper.updateName(selectedID,upsnom,upsprenom,upsmail,upstel,upsadr,upsstatut,upsimage);
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                invopic();
            }
            else {
                Toast.makeText(this,"Permission",Toast.LENGTH_LONG).show();
            }
        }
    }

    private void invopic() {
        Intent picpick = new Intent(Intent.ACTION_PICK);

        File pictureDirectory =  Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

        String pictureDirectoryPath= pictureDirectory.getPath();
        Uri data = Uri.parse(pictureDirectoryPath);
        picpick.setDataAndType(data,"image/*");


        startActivityForResult(picpick,IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK){
            if (requestCode == IMAGE_REQUEST){
                Uri imageuri = data.getData();

                InputStream inputStream;

                try {
                    inputStream = getContentResolver().openInputStream(imageuri);

                    Bitmap image = BitmapFactory.decodeStream(inputStream);

                    upimage.setImageBitmap(image);


                    //String imagestring = new String(selected_contact.getBytes(image));


                    //Intent imageintent = new Intent(edit_contact.this, add_contact.class);

                    //imageintent.putExtra("imaaggee",imagestring);
                    //startActivity(imageintent);


                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(edit_contact.this, "Unable to open image", Toast.LENGTH_SHORT).show();
                }
            }
        }


    }
}
