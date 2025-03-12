package com.example.mobilejava;

import android.graphics.Color;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        AdapterView.OnItemClickListener{
    Button mainButton, ok_btn, cnc_btn, dlt_btn;
    TextView mainTextView;
    EditText mainEditText;
    ListView mainListView;
    ArrayAdapter<String> mArrayAdapter;
    ArrayList<String> mNameList = new ArrayList<>();
    int selectedPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        mainButton = findViewById(R.id.main_button);
        mainTextView = findViewById(R.id.main_textview);
        mainEditText = findViewById(R.id.main_edittext);
        mainListView = findViewById(R.id.main_listview);
        ok_btn = findViewById(R.id.ok_btn);
        cnc_btn = findViewById(R.id.cnc_btn);
        dlt_btn = findViewById(R.id.dlt_btn);


        mainTextView.setText("Set in Java!");

        mainButton.setOnClickListener(this);

        mainListView.setOnItemClickListener(this);

        mainListView = findViewById(R.id.main_listview);
        mArrayAdapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_activated_1,
                mNameList);
        mainListView.setAdapter(mArrayAdapter);


        View.OnClickListener ocl_btn = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = view.getId();
                if (id == R.id.ok_btn) {
                    mainTextView.setText("Нажата кнопка ОК");
                    Toast.makeText(getApplicationContext(), "Нажата кнопка OK",
                            Toast.LENGTH_LONG).show();
                } else if (id == R.id.cnc_btn) {
                    mainTextView.setText("Нажата кнопка Cancel");
                    Toast.makeText(getApplicationContext(), "Нажата кнопка Cancel",
                            Toast.LENGTH_LONG).show();
                }
            }
        };
        ok_btn.setOnClickListener(ocl_btn);
        cnc_btn.setOnClickListener(ocl_btn);
        dlt_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        Collections.sort(mNameList, new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                return s1.compareToIgnoreCase(s2);
            }
        });

        if(v.getId() == R.id.main_button) {
            addNewItem();
        }
        else if(v.getId() == R.id.dlt_btn) {
            if(selectedPosition != -1) {
                mNameList.remove(selectedPosition);
                mArrayAdapter.notifyDataSetChanged();
                selectedPosition = -1;
                mainTextView.setText("Элемент удалён");
            } else {
                mainTextView.setText("Выберите элемент для удаления!");
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d("omg android", position+": " + mNameList.get(position));
        mainTextView.setText(mNameList.get(position).toString()
                + " is learning "+ "\r\n" + "Android development!");
        selectedPosition = position;

        for(int i = 0; i < mainListView.getChildCount(); i++) {
            mainListView.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
        }
        view.setBackgroundColor(Color.LTGRAY);
    }

    public void addNewItem() {
        String input = mainEditText.getText().toString().trim();

        if(input.isEmpty()) {
            showToast("Введите текст!");
            return;
        }

        if(isDuplicate(input)) {
            showToast("Элемент '" + input + "' уже существует!");
            return;
        }

        mNameList.add(input);
        Collections.sort(mNameList, String.CASE_INSENSITIVE_ORDER);
        mArrayAdapter.notifyDataSetChanged();
        mainEditText.getText().clear();
    }

    private boolean isDuplicate(String newItem) {
        for(String existingItem : mNameList) {
            if(existingItem.trim().equalsIgnoreCase(newItem)) {
                return true;
            }
        }
        return false;
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }



}