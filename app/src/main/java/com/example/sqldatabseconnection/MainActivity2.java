package com.example.sqldatabseconnection;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity2 extends ListActivity {

    static int j =15;

    /** Items entered by the user is stored in this ArrayList variable */
    ArrayList list = new ArrayList();


    /** Declaring an ArrayAdapter to set items to ListView */
    ArrayAdapter adapter;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Button btn = (Button) findViewById(R.id.btnAdd);

        /** Reference to the delete button of the layout main.xml */
        Button btnDel = (Button) findViewById(R.id.btnDel);

        /** Defining the ArrayAdapter to set items to ListView */
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, list);
        databaseHelper = new DatabaseHelper(this);

        /** Defining a click event listener for the button "Add" */

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText edit = (EditText) findViewById(R.id.txtItem);
                list.add(edit.getText().toString());
                String item = edit.getText().toString();
                if(item.length()!=0) {
                    //int j = databaseHelper.maxSR();
                    boolean insert = databaseHelper.addList(j,item);
                    if(insert == true) {
                        Toast.makeText(getApplicationContext(), "DATA INSERTED", Toast.LENGTH_SHORT).show();
                        j++;
                    }
                    else
                        Toast.makeText(getApplicationContext(),"COULD NOT INSERT DATA",Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(getApplicationContext(),"something went wrong",Toast.LENGTH_SHORT).show();

                edit.setText("");
                adapter.notifyDataSetChanged();

            }
        };



        /** Defining a click event listener for the button "Delete" */
        View.OnClickListener listenerDel = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /** Getting the checked items from the listview */
                SparseBooleanArray checkedItemPositions = getListView().getCheckedItemPositions();
                int itemCount = getListView().getCount();

                for (int i = itemCount - 1; i >= 0; i--) {
                    if (checkedItemPositions.get(i)) {
                        adapter.remove(list.get(i));
                        boolean delete = databaseHelper.deleteRow(i);
                        if(delete == true) {
                            Toast.makeText(getApplicationContext(), "DATA DELETED", Toast.LENGTH_SHORT).show();
                        }
                        else
                            Toast.makeText(getApplicationContext(),"COULD NOT DELETE DATA",Toast.LENGTH_SHORT).show();
                    }
                }
                checkedItemPositions.clear();
                adapter.notifyDataSetChanged();
            }
        };


        /** Setting the event listener for the add button */
        btn.setOnClickListener(listener);

        /** Setting the event listener for the delete button */
        btnDel.setOnClickListener(listenerDel);

        /** Setting the adapter to the ListView */
        setListAdapter(adapter);
    }
}