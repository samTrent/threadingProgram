package com.example.samtrent.threadingprogram;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;  // for debug
import android.view.View; // for button
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.lang.Runnable; // for threading
import java.lang.Thread; // for threading


public class threadActivity extends AppCompatActivity implements Runnable {


//    Handler handler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            TextView myTextView =
//                    (TextView)findViewById(R.id.myTextView);
//            myTextView.setText("Button Pressed");
//        }
//    };

    ListView listView;
    ArrayList<Integer> arrayOfFileContent = new ArrayList<>();
    ArrayList<Integer> arrayofNumb = new ArrayList<>();
    ArrayAdapter<Integer> itemAdapter;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread);


        itemAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_list_item_1, arrayofNumb);
        listView = (ListView) findViewById(R.id.listView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setMax(100);
        progressBar.setProgress(0);

    }


    // private static final int PROGRESS = 0x1;
    //progressBar.setProgress(100);
    // private int progressBar().setProgress = 0;

    // private Handler mHandler = new Handler();


    //Clicking the Create button

    public void CreateButtonClicked(View v) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                int totalItems = 11;


                //Generating our numbers.txt file in internal storage
                String fileName = "number.txt";


                //filling contents up with 10 numbers
                for (int i = 1; i < totalItems; i++) {
                    arrayOfFileContent.add(i);


                    try {
                        //make it take some time...
                        Thread.sleep(250);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    progressBar.setProgress(i * 10);
                }

                FileOutputStream outputStream;

                try {
                    outputStream = openFileOutput(fileName, Context.MODE_PRIVATE); // make file

                    for (int i = 0; i < totalItems; i++) {
                        outputStream.write(arrayOfFileContent.indexOf(i)); // write contents
                    }

                    outputStream.close();
                    Log.d("file has been created!", fileName);

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("Error creating file.", fileName);
                }

                //Log.d("file content", String.valueOf(fileContent));
                progressBar.setProgress(0);
            }

        };
        Thread mythread = new Thread(runnable);
        mythread.start();

    }


    // @SuppressLint("LongLogTag")
    public void LoadButtonClicked(View v) {

        listView.setAdapter(itemAdapter);
        new AsyncCaller().execute("");

    }




    public void ClearButtonClicked(View v) {
        ArrayAdapter<Integer> itemAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_list_item_1, arrayofNumb);
        itemAdapter.clear();
        arrayofNumb.clear();
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(itemAdapter);
    }

    @Override
    public void run() {



    }


    private class AsyncCaller extends AsyncTask<Void, Void, Void>
    {
        private static final String TAG = "none";

        @Override
        protected void onPreExecute(){
            Log.d(TAG, "On preExceute...");
        }


        @Override
        protected Void doInBackground(Void... params) {

            Log.d(TAG, "On doInBackground...");
            try {
                // converting our listArray to an arrayAdapter so that we can display the items

                FileInputStream fin = openFileInput("number.txt");
                InputStreamReader isr = new InputStreamReader(fin);
                BufferedReader bufferReader = new BufferedReader(isr);
                StringBuilder sb = new StringBuilder();

                // threadActivity.this.runOnUiThread(new Runnable() {
                //   runOnUiThread(new Runnable() {

//                    Runnable runnable1 = new Runnable()  {
                //  @Override
                //  public void run() {


                int i = 0;

                while (i < 10) {


                    arrayofNumb.add(arrayOfFileContent.get(i));

                    try {
                        Thread.sleep(250);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                    i++;


                    //



                }

            } catch (Exception e) {
                Log.d("FFFFF!", String.valueOf(e));
            }
            //progressBar.setProgress(0);


            return null;
        }



        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            //this method will be running on UI thread
            itemAdapter.notifyDataSetChanged();
            progressBar.setProgress(10);
        }



        
        public void execute(String s)
        {
            doInBackground();
        }
    }






}





