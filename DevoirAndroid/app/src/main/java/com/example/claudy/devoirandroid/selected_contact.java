package com.example.claudy.devoirandroid;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Path;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.lang.reflect.Field;

import static java.nio.charset.StandardCharsets.UTF_8;

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
    private String selectedImage;
    Bitmap SeeleectedImg;

    private TextView selnom;
    private TextView selprenom;
    private TextView seladr;
    private TextView seltel;
    private TextView selmail;
    private TextView selstatut;
    private ImageView selpic;

    private int selectedID;

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
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
        selpic = (ImageView) findViewById(R.id.selpic);

        Intent receivedIntent = getIntent();
        selectedID = receivedIntent.getIntExtra("id",-1); //NOTE: -1 is just the default value
        selectedName = receivedIntent.getStringExtra("name");
        selectedPrenom = receivedIntent.getStringExtra("prenom");
        selectedEmail = receivedIntent.getStringExtra("mail");
        selectedTelephone = receivedIntent.getStringExtra("telephone");
        selectedAdresse = receivedIntent.getStringExtra("adresse");
        selectedStatut = receivedIntent.getStringExtra("statut");

        selectedImage = receivedIntent.getStringExtra("image");
        byte[] bytes = selectedImage.getBytes(UTF_8);
        Bitmap just2 = BitmapFactory.decodeByteArray(bytes,0,bytes.length);

        toastMessage("bytes is:"+bytes);
        toastMessage("just is:"+just2);
        //String named = selectedImage;
        //int id = getResources().getIdentifier(named, "drawable", getPackageName());
        //Drawable drawable = getResources().getDrawable(id);
        toastMessage("selectedImage is : "+selectedImage);
        //SeeleectedImg =  StringToBitMap(selectedImage);
        //toastMessage("SSEEelectedImage is : "+SeeleectedImg);


        seleectedID = String.valueOf(selectedID);
        /*PackageManager pm = getPackageManager();
        ApplicationInfo info= null;
        try {
            info = pm.getApplicationInfo(selectedImage, PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }*/

        /*Resources res = getResources();
        String mDrawableName = selectedImage;
        int resID = res.getIdentifier(mDrawableName , "drawable", getPackageName());
        Drawable drawable = res.getDrawable(resID );
        selpic.setImageDrawable(drawable );*/

      /*  int id = getResources().getIdentifier(selectedImage, "drawable", this.getPackageName());
        if(id != 0){
            selpic.setImageResource(id);
        }else{
            //selpic.setImageResource(R.drawable.aluminium);
            toastMessage("no image");
        } */

        /*if(selectedImage!=null && selectedImage!="") <--CHECK FILENAME IS NOT NULL
        {
            File f = new File(pathName);
            if(f.exists())  <-- CHECK FILE EXISTS OR NOT
            {
                Drawable d = Drawable.createFromPath(selectedImage);
                selpic.setImageDrawable(d);

            }
        }*/


        /*Path path = ?;
        Bitmap bm = null;
        try {
            bm = BitmapFactory.decodeFile(path);
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        }
        toastMessage("bm is"+bm);
        selpic.setImageBitmap(bm);*/

        /*String name = "your_drawable";
        final Field field = R.drawable.getField(name);
        int id = 0;
        try {
            id = field.getInt(null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        Drawable drawable = getResources().getDrawable(id);
        */


        toastMessage("seleectedID is : "+seleectedID);
        selnom.setText(selectedName);
        selprenom.setText(selectedPrenom);
        selmail.setText(selectedEmail);
        seltel.setText(selectedTelephone);
        seladr.setText(selectedAdresse);
        selstatut.setText(selectedStatut);
        //selpic.setImageDrawable(info.loadIcon(pm));
        //selpic.setBackground(drawable);
        //selpic.setImageBitmap(SeeleectedImg);
        //selpic.setImageDrawable(Drawable.createFromPath(selectedImage));
        selpic.setImageBitmap(just2);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder myalert = new  AlertDialog.Builder(selected_contact.this);
                myalert.setTitle("Alert!!!");
                myalert.setIcon(R.drawable.ic_warning_black_24dp);
                myalert.setMessage("Voulez-vous supprimer ce contact?");
                myalert.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mDatabaseHelper.deleteName(seleectedID);
               /* selnom.setText("");
               selprenom.setText("");
                seladr.setText("");
                seltel.setText("");
                selmail.setText("");
                selstatut.setText(""); */
                        toastMessage("removed from database");

                        Intent intent = new Intent(selected_contact.this, MainActivity.class);
                        startActivity(intent);

                    }
                });
                myalert.setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    }
                });
                myalert.create();
                myalert.show();

            }
        });

        btnmodify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Log.d(TAG, "onItemClick: You Clicked on " + selectedName);

                Log.d(TAG, "onItemClick: item ID is " + seleectedID);
                Cursor data = mDatabaseHelper.getItemID(seleectedID); //get the id associated with that name
                int itemID = -1;
                while(data.moveToNext()){
                    itemID = data.getInt(0);
                }
                if(itemID > -1){
                    Log.d(TAG, "onItemClick: The ID is: " + itemID);
                    Intent editScreenIntent = new Intent(selected_contact.this, edit_contact.class);
                    editScreenIntent.putExtra("id",seleectedID);
                    editScreenIntent.putExtra("name",selectedName);
                    editScreenIntent.putExtra("prenom",selectedPrenom);
                    editScreenIntent.putExtra("mail",selectedEmail);
                    editScreenIntent.putExtra("telephone",selectedTelephone);
                    editScreenIntent.putExtra("adresse",selectedAdresse);
                    editScreenIntent.putExtra("statut",selectedStatut);
                    editScreenIntent.putExtra("image",selectedImage);
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


        // convert from bitmap to byte array
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




    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }

    public  void mainpage (View view)

    {
        Intent intent = new Intent(selected_contact.this, MainActivity.class);
        startActivity(intent);
    }

    public String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        String temp=Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    public Bitmap StringToBitMap(String encodedString){
        try {
            byte [] encodeByte=Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }


}
