package com.charmi.sqliteapp;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText name, surname, marks, id;
    Button btn, view,update, del, src;

    DatabaseHelper mydb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mydb = new DatabaseHelper(this);
        id = (EditText)findViewById(R.id.id);
        name = (EditText)findViewById(R.id.name);
        surname = (EditText)findViewById(R.id.surname);
        marks = (EditText)findViewById(R.id.marks);
        btn = (Button)findViewById(R.id.button);
        view = (Button)findViewById(R.id.view);
        del = (Button)findViewById(R.id.delete);
        src = (Button)findViewById(R.id.src);
        update = (Button)findViewById(R.id.update);

        onDelete();
        Particular();

    }
    public void addData(View v){

      boolean isInserted =  mydb.insertdata(name.getText().toString(), surname.getText().toString(), marks.getText().toString());
        if(isInserted == true)
        {
            Toast.makeText(MainActivity.this, "data added successfully", Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(MainActivity.this, "data could not be added", Toast.LENGTH_SHORT).show();

        clear();

    }
    public void viewAll(View v)
    {
       Cursor result = mydb.getAllData();
        if(result.getCount()==0){

            showMessage("Error", "No data found");
            return;
        }
        else{
            StringBuffer strBuffer = new StringBuffer();
            while (result.moveToNext()){
                strBuffer.append("\n ID"+result.getString(0) + "\nNAME" +result.getString(1)+ "\n SURMANE " + result.getString(2)+ "\n MARKS"+ result.getString(3)+ "\n\n");

            }
            showMessage("Data", strBuffer.toString());
        }
    }


    public void showMessage(String title, String msg)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.show();
    }
    public void updateData(View v)
    {
        boolean isUpdated = mydb.updateData(id.getText().toString(), name.getText().toString(), surname.getText().toString(), marks.getText().toString());
    if(isUpdated==true)
    {

        Toast.makeText(MainActivity.this, "data updated successfully", Toast.LENGTH_SHORT).show();
    }
    else
        Toast.makeText(MainActivity.this, "data could not be updated", Toast.LENGTH_SHORT).show();
        clear();
    }

    public  void Particular()
    {
        src.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Cursor result= mydb.getAllData();
                        String src_id;
                        int i=0;
                        String comp_id;
                        src_id= id.getText().toString();
                        if(result.getCount()==0){
                            Toast.makeText(MainActivity.this, "no data found", Toast.LENGTH_SHORT).show();

                            return;
                        }
                        else {

                            while( result.moveToNext()) {
                                comp_id = result.getString(0).toString();
                                if (src_id.compareTo(comp_id)==0) {

                                    name.setText(result.getString(1).toString());
                                    surname.setText(result.getString(2).toString());
                                    marks.setText(result.getString(3).toString());
                                    i++;
                                    Toast.makeText(MainActivity.this, " data found", Toast.LENGTH_SHORT).show();

                                }

                            }
                            if(i==0)
                            {
                                Toast.makeText(MainActivity.this, " data can not be  found", Toast.LENGTH_SHORT).show();
                                clear();
                            }
                        }
                    }
                }
        );
    }
    public void onDelete( ){
        del.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        Integer deleted= mydb.deleteData(id.getText().toString());
                        if (deleted>0)
                        {
                            Toast.makeText(MainActivity.this, "data deleted successfully", Toast.LENGTH_SHORT).show();
                        }
                        else
                            Toast.makeText(MainActivity.this, "data could not be deleted", Toast.LENGTH_SHORT).show();
                        clear();

                    }
                }

        );


    }

    public void clear()
    {
        id.setText("");
        name.setText("");
        surname.setText("");
        marks.setText("");
    }


}
