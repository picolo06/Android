package com.example.claudy.devoirandroid;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import static java.nio.charset.StandardCharsets.UTF_8;

public class add_contact extends AppCompatActivity {
    public static final int IMAGE_REQUEST = 200;
    public static final int pic_permission_request_code = 8675309;

    // SqqDev salli;
   DatabaseHelper mDatabaseHelper;

    private FloatingActionButton btnAddData;
    //private FloatingActionButton picadd;
    private EditText insnom;
    private EditText insprenom;
    private EditText insadr;
    private EditText instel;
    private EditText insmail;
   private EditText insstatut;
    private ImageView pic;




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
        //picadd = (FloatingActionButton) findViewById(R.id.picadd);

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
            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
                    invokpic();
                }else {
                    String[] premissionRequest= {android.Manifest.permission.READ_EXTERNAL_STORAGE};
                    requestPermissions(premissionRequest, pic_permission_request_code);

                }

            }
        });


        //Bitmap bitmap = ((BitmapDrawable)pic.getDrawable()).getBitmap();
        //final String immagestring = new String(selected_contact.getBytes(bitmap));
        //toastMessage("string new image is "+ immagestring);

        


        btnAddData.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {

               // selectedName = inntent.getStringExtra("name");
                String newnom = insnom.getText().toString();
                String newprenom = insprenom.getText().toString();
                String newmail = insmail.getText().toString();
                String newtel = instel.getText().toString();
                String newadr = insadr.getText().toString();
                String newstat = insstatut.getText().toString();
               // Bitmap bitmap = ((BitmapDrawable)pic.getDrawable()).getBitmap();
                //final String immagestring = new String(selected_contact.getBytes(bitmap));
                //String newimage = new String(getBytes(((BitmapDrawable)pic.getDrawable()).getBitmap()));
                //toastMessage("string new image is "+ newimage);
                String just = new String(imageViewToByte(pic),UTF_8);

                //byte[] bytes = text.getBytes(UTF_8);
                //String text = new String(bytes, UTF_8);
                //String newimage = pic.getDrawable().toString();
                //String newimage = just.getStringExtra("imaaggeeuri");
                String newimage = just;

                if (insnom.length() != 0) {
                    AddData(newnom,newprenom,newmail,newtel,newadr,newstat,newimage);
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == pic_permission_request_code){
            if (grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                invokpic();
            }
            else {
                Toast.makeText(this,"Permission",Toast.LENGTH_LONG).show();
            }
        }
    }

    private void invokpic() {
        Intent picpick = new Intent(Intent.ACTION_PICK);

        File pictureDirectory =  Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

        String pictureDirectoryPath= pictureDirectory.getPath();
        Uri data = Uri.parse(pictureDirectoryPath);
        picpick.setDataAndType(data,"image/*");


        startActivityForResult(picpick,IMAGE_REQUEST);
    }


    public  void cancelmv (View view)

    {
        Intent intent = new Intent(add_contact.this, MainActivity.class);
        startActivity(intent);
    }

    public void AddData(String newnom,String newprenom,String newmail,String newtel,String newadr,String newstat,String newimage) {
        boolean insertData = mDatabaseHelper.addData(newnom,newprenom,newmail,newtel,newadr,newstat,newimage);

        if (insertData) {
            toastMessage("Data Successfully Inserted! newimage is:"+newimage);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            if (resultCode == RESULT_OK){
                if (requestCode == IMAGE_REQUEST){
                   Uri imageuri = data.getData();

                    InputStream inputStream;

                    try {
                        inputStream = getContentResolver().openInputStream(imageuri);

                        Bitmap image = BitmapFactory.decodeStream(inputStream);

                        toastMessage("imageuri is:"+imageuri);
                        toastMessage("inputstream is: "+inputStream);

                        toastMessage("image is "+image);


                        pic.setImageBitmap(image);


                       //String imagestring = new String(getBytes(image));


                       // Intent imageintent = new Intent(add_contact.this, add_contact.class);

                       // imageintent.putExtra("imaaggee",imagestring);
                       // startActivity(imageintent);




                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        Toast.makeText(add_contact.this, "Unable to open image", Toast.LENGTH_SHORT).show();
                    }
                }
            }


    }

    private byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }
    public  static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

    // convert from byte array to bitmap
    public static Bitmap getPhoto(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }
}
