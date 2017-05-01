package com.example.claudy.devoirandroid;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.ContactsContract;
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

public class add_contact extends AppCompatActivity {
    public static final int IMAGE_REQUEST = 20;
    // SqqDev salli;
   DatabaseHelper mDatabaseHelper;

    private FloatingActionButton btnAddData;
    private EditText insnom;
    private EditText insprenom;
    private EditText insadr;
    private EditText instel;
    private EditText insmail;
   private EditText insstatut;
    ImageView pic;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_contact);

        mDatabaseHelper = new DatabaseHelper(this);
        //salli = new SqqDev(this);
       insnom = (EditText) findViewById(R.id.nom);
        insprenom = (EditText) findViewById(R.id.prenom);
        insadr = (EditText) findViewById(R.id.adr);
        instel = (EditText) findViewById(R.id.tel);
       insmail = (EditText) findViewById(R.id.mail);
        insstatut = (EditText) findViewById(R.id.stat);
        btnAddData = (FloatingActionButton) findViewById(R.id.btnAddData);
        pic = (ImageView) findViewById(R.id.pic);


     /*   btnAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newEntry = insnom.getText().toString();
                if (insnom.length() != 0) {
                    AddData();
                } else {
                    Toast.makeText(add_contact.this, "You must put something in the text field!", Toast.LENGTH_LONG).show();
                }

            }
        });



     */

        pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent picpick = new Intent(Intent.ACTION_PICK);

                File pictureDirectory =  Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

                String pictureDirectoryPath= pictureDirectory.getPath();
                Uri data = Uri.parse(pictureDirectoryPath);
                picpick.setDataAndType(data,"image/*");


                startActivityForResult(picpick,IMAGE_REQUEST);
            }
        });

        //onActivityResult(IMAGE_REQUEST,RESULT_OK,);


        


        btnAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newnom = insnom.getText().toString();
                String newprenom = insprenom.getText().toString();
                String newmail = insmail.getText().toString();
                String newtel = instel.getText().toString();
                String newadr = insadr.getText().toString();
                String newstat = insstatut.getText().toString();

                if (insnom.length() != 0) {
                    AddData(newnom,newprenom,newmail,newtel,newadr,newstat);
                   /* insnom.setText("");
                    insstatut.setText("");
                    insadr.setText("");
                    instel.setText("");
                    insmail.setText("");
                    insprenom.setText(""); */
                    Intent intent = new Intent(add_contact.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    toastMessage("You must put something in the text field!");
                }

            }
        });
    }




    public  void cancelmv (View view)

    {
        Intent intent = new Intent(add_contact.this, MainActivity.class);
        startActivity(intent);
    }

    public void AddData(String newnom,String newprenom,String newmail,String newtel,String newadr,String newstat) {
        boolean insertData = mDatabaseHelper.addData(newnom,newprenom,newmail,newtel,newadr,newstat);

        if (insertData) {
            toastMessage("Data Successfully Inserted!");
        } else {
            toastMessage("Something went wrong");
        }
    }

   /* public void AddData() {

                boolean isInserted = salli.insertdata(insnom.getText().toString(), insprenom.getText().toString(), insmail.getText().toString(), instel.getText().toString(), insadr.getText().toString(), insstatut.getText().toString());
                if (isInserted){
                    Toast.makeText(add_contact.this,"Data inserted",Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(add_contact.this,"Data not inserted",Toast.LENGTH_LONG).show();

                }
    } */

    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }

    public  void mainpage (View view)

    {
        Intent intent = new Intent(add_contact.this, MainActivity.class);
        startActivity(intent);
    }

/*    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            if (resultCode == RESULT_OK){
                if (requestCode == IMAGE_REQUEST){
                   Uri imageuri = data.getData();

                    InputStream inputStream;

                    try {
                        inputStream = getContentResolver().openInputStream(imageuri);

                        Bitmap image = BitmapFactory.decodeStream(inputStream);

                        pic.setImageBitmap(image);

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        Toast.makeText(this, "Unable to open image", Toast.LENGTH_SHORT).show();
                    }
                }
            }


    } */
}
