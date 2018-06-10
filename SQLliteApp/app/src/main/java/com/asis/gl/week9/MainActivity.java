package com.asis.gl.week9;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void loadSQLFromFile(View v){
        //when the button is pressed
        SQLiteDatabase db = getApplicationContext().openOrCreateDatabase("mydb",MODE_PRIVATE,null);

        Scanner scn = new Scanner(getResources().openRawResource(R.raw.db));
        String query="";

        while(scn.hasNext()){
            query+= scn.nextLine() + "\n";
            if(query.trim().endsWith(";")){
                db.execSQL(query);
                Log.d("DBDB",query);
                query="";
            }
        }
    }

    public void runQeury(View view) {
        EditText qText = (EditText) findViewById(R.id.txtQuery);

        String query = qText.getText().toString();
        SQLiteDatabase db = getApplicationContext().openOrCreateDatabase("mydb",MODE_PRIVATE,null);
        Cursor cr = db.rawQuery(query,null);

        if(!cr.moveToFirst()){
            Toast.makeText(this,"DB not loaded", Toast.LENGTH_LONG).show();
        }

        do{
            String name = cr.getString(cr.getColumnIndex("Name"));
            String sname = cr.getString(cr.getColumnIndex("Surname"));
            Toast.makeText(this, name +" "+ sname, Toast.LENGTH_SHORT).show();
        }while(cr.moveToNext());
        cr.close();
    }
}
