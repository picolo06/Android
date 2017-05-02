package com.example.claudy.devoirandroid;

import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private ListView mListView;
    FloatingActionButton btninfo;
    FloatingActionButton btnsearch;
    private EditText filter;
    //ArrayList<datamine> arraylist = new ArrayList<datamine>();

    //SqqDev salli;
    DatabaseHelper mDatabaseHelper;
   // ArrayAdapter adapterr;
    ArrayAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        filter = (EditText) findViewById(R.id.filter);

        mListView = (ListView) findViewById(R.id.lvdevsq);
        btninfo = (FloatingActionButton) findViewById(R.id.btninfo);
        btnsearch = (FloatingActionButton) findViewById(R.id.btnsearch);

        // salli = new SqqDev(this);
        mDatabaseHelper = new DatabaseHelper(this);



        btninfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 String count = String.valueOf(mListView.getCount());
                toastMessage("Il y a " +count+" contact(s) enregistres");
            }
        });



        populateListView();


    }

    public  void addcv (View view)

    {
        Intent intent = new Intent(MainActivity.this, add_contact.class);
        startActivity(intent);
    }



  /*  private void populateListView(){
        Log.d(TAG, "populateListView: Displaying data in the ListView.");

        Cursor res = salli.getAllData();
        ArrayList<String> arrayList = new ArrayList<>();
        while (res.moveToNext()){
            arrayList.add(res.getString(1));
        }
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayList);
        lvdevsq.setAdapter(adapter);
    } */

    private void populateListView() {
        Log.d(TAG, "populateListView: Displaying data in the ListView.");

        //get the data and append to a list
        Cursor data = mDatabaseHelper.getData();
        ArrayList<String> listData = new ArrayList<>();


        while(data.moveToNext()){
            //get the value from the database in column 1
            //then add it to the ArrayList
            listData.add(data.getString(1));


        }
        //create the list adapter and set the adapter

       adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listData);
        mListView.setAdapter(adapter);
         //adapterr = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listData);


        filter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                (MainActivity.this).adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //set an onItemClickListener to the ListView
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
                String name = adapterView.getItemAtPosition(i).toString();
                //String id1 = new String(String.valueOf(adapterView.getItemIdAtPosition(i)));
                long id3 = id +1 ;
                String id2 = new String(String.valueOf(id3));
                Log.d(TAG, "onItemClick: You Clicked on " + name);
                Log.d(TAG, "onItemClick: item ID is " + id3);

                //toastMessage("Id is" +id3+" pff");
                //toastMessage("Id2 is" +id2+" pff");

                Cursor data = mDatabaseHelper.getItemID(id2); //get the id associated with that name
                int itemID = 0;
                String prenom = "";
                String mail = "";
                String telephone = "";
                String adresse = "";
                String statut = "";
                String image = "";
                //toastMessage("itemID is" +data.getInt(0)+" pff");

                while(data.moveToNext()){
                    itemID = data.getInt(0);
                    name = data.getString(1);
                    prenom = data.getString(2);
                    mail = data.getString(3);
                    telephone = data.getString(4);
                    adresse = data.getString(5);
                    statut = data.getString(6);
                    image = data.getString(7);

                }
                if(itemID > -1){
                    Log.d(TAG, "onItemClick: The ID is: " + itemID);
                    Intent editScreenIntent = new Intent(MainActivity.this, selected_contact.class);
                    editScreenIntent.putExtra("id",itemID);
                    editScreenIntent.putExtra("name",name);
                    editScreenIntent.putExtra("prenom",prenom);
                    editScreenIntent.putExtra("mail",mail);
                    editScreenIntent.putExtra("telephone",telephone);
                    editScreenIntent.putExtra("adresse",adresse);
                    editScreenIntent.putExtra("statut",statut);
                    editScreenIntent.putExtra("image",image);

                    startActivity(editScreenIntent);
                }
                else{
                    toastMessage("No ID associated with that name");
                }
            }
        });
    }

    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
}
