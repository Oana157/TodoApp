package com.oana.todoapp;

import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.graphics.Color.DKGRAY;
import static android.graphics.Color.GRAY;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> items;
    private CustomAdapter itemsAdapter;
    ListView lvlItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvlItems = (ListView) findViewById(R.id.listView);
        readItems();
        itemsAdapter = new CustomAdapter(items);
        lvlItems.setAdapter(itemsAdapter);
        setUpListViewListener();
    }

    protected void onAddItem(View view){
        EditText newItem = (EditText) findViewById(R.id.editText);
        String itemText = newItem.getText().toString();
        if(!itemText.isEmpty()){
            items.add(itemText);
            newItem.setText("");
            itemsAdapter.notifyDataSetChanged();
            writeItems();
        }

    }

    protected void onEnterName(View view){
        EditText defaultText = (EditText) findViewById(R.id.editText);
        String itemText = defaultText.getText().toString();
        if(itemText.equals("Item Name")){
            defaultText.setText("");
        }

    }
    private void setUpListViewListener(){
        lvlItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                items.remove(position);
                itemsAdapter.notifyDataSetChanged();
                writeItems();
                return true;
            }
        });

        lvlItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                itemsAdapter.getSelectedStrings().add(items.get(i));
                itemsAdapter.notifyDataSetChanged();
                writeItems();

            }
        });
    }

    private void readItems(){
        File filesDirectory = getFilesDir();
        File todoFile = new File(filesDirectory, "todo.txt");
        try{
            items = new ArrayList<String>(FileUtils.readLines(todoFile));
        }catch (IOException e){
            items = new ArrayList<String>();
        }
    }

    private void writeItems(){
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try{
            FileUtils.writeLines(todoFile, items);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
