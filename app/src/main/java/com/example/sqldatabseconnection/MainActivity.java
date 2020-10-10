package com.example.sqldatabseconnection;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public EditText name , email;
    Button save , display , list;
    TextView display_name;

    DatabaseHelper databaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        display_name = findViewById(R.id.displayText);
        save = findViewById(R.id.save);
        display = findViewById(R.id.display_name);
        list = findViewById(R.id.display_List);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name1 = name.getText().toString();
                String email1 = email.getText().toString();
                if(name1.length()!=0 && email1.length() !=0){
                    boolean result = databaseHelper.addText(name1,email1) ;
                    if(result == true)
                        Toast.makeText(getApplicationContext(),"DATA INSERTED",Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(getApplicationContext(),"COULD NOT INSERT DATA",Toast.LENGTH_SHORT).show();

                }
                else
                    Toast.makeText(getApplicationContext(),"something went wrong",Toast.LENGTH_SHORT).show();

            }
        });

        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,MainActivity2.class);
                startActivity(i);
            }
        });


        databaseHelper = new DatabaseHelper(this);



        display.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor = databaseHelper.alldata();

                if(cursor.getCount() == 0 )
                    Toast.makeText(getApplicationContext(),"NO DATA" ,Toast.LENGTH_SHORT).show();
                else if(cursor != null && cursor.moveToFirst()) {
                    display_name.setText(cursor.getString(0));
                    cursor.close();
                }

            }
        });

    }
}