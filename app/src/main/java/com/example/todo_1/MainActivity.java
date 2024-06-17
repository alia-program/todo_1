package com.example.todo_1;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ImageButton button;
    LinearLayout layout;
    JSONArray kusodeka_Array = new JSONArray();

    int index = 0;
    String file ="unchi.json";

    SharedPreferences index_Save;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.imageButton2);
        button.setOnClickListener(add_button);
        layout = findViewById(R.id.todo_);

        setView();
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onStop() {
        super.onStop();
        String json_data = null;

        while (index != 0){
            try {
                todo add_Todo = new todo(this,"MEMO No" + 0 + "TODO No" + index);
                json_data += add_Todo.get_JsonArray();
                Log.d("index保存成功", "MEMO No" + 0 + "TODO No" + index);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            index--;
        }


        //kusodeka_Array = json_data;

        try {
            //ファイル作成
            FileOutputStream file_out = openFileOutput(file,MODE_PRIVATE);
            //書き込み
            OutputStreamWriter out_writer = new OutputStreamWriter(file_out);
            BufferedWriter bw = new BufferedWriter(out_writer);

            bw.write(kusodeka_Array.toString(4));
            bw.close();

            //Log.d("取得完了",getData());

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    View.OnClickListener add_button = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            try {
                index++;
                addView(index,"MEMO No" + 0 + "TODO No" + index);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };



    private void setView(){
        //ファイルの作成
        index_Save = getApplicationContext().getSharedPreferences("todo_index", Context.MODE_PRIVATE);
        int date = index_Save.getInt("view_index2",0);
        index = date;

        Log.d("index開始", String.valueOf(index));

        EditText editText = new EditText(this);
        add_EditSettings(editText,0);

        if (index != 0){
            for (int i = 0;i < index; i++){
                try {
                    addView(i,String.valueOf(i + "aaa"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d("index作成完了", String.valueOf(index));
            }
            Log.d("index作成終了", String.valueOf(index));
        }
    }

    private void addView(int i,String fileName) throws JSONException {
        EditText editText = new EditText(this);

        todo add_Todo = new todo(this,null ,fileName);

        add_Todo(add_Todo,i);
        add_EditSettings(editText,i);
        //remove
        add_Todo.x_button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                remove_view(add_Todo, editText);
                //deleteFile(file_List[add_Todo.getId()].getName());
            }
        });
        save();
    }

    private void add_Todo(todo todo_View,int id){
        todo_View.setId(id);
        layout.addView(todo_View);
    }

    private void add_EditSettings(EditText editText,int id){
        //editTextの下線を透明色に
        editText.setBackgroundColor(Color.parseColor("#00000000"));
        editText.setLineSpacing(105,0);
        editText.setTextSize(22);
        editText.setId(id);
        layout.addView(editText);
    }

    private void save(){
        SharedPreferences.Editor editor = index_Save.edit();
        editor.putInt("view_index2",index);
        editor.apply();
    }

    private void remove_view (View todo,View edit){
        layout.removeView(todo);
        layout.removeView(edit);
        index--;
        save();
    }




    //todo111111
    /*
    File file;
    File[] file_List;
    ArrayList<todo> todoArrayList = new ArrayList<>();
    SharedPreferences index_Save;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.imageButton2);
        button.setOnClickListener(add_button);
        layout = findViewById(R.id.todo_);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //ファイルの作成
        index_Save = getApplicationContext().getSharedPreferences("todo_index", Context.MODE_PRIVATE);
        int date = index_Save.getInt("view_index",0);
        index = date;

        EditText editText = new EditText(this);
        add_EditSettings(editText,0);

        file = new File(String.valueOf(getApplicationContext().getFilesDir()));
        file_List = file.listFiles();

        if (file_List.length != 0){
            for (int i = 0;i < file_List.length; i++){
                addView(i,file_List[i].getName());
                Log.d("FileName",file_List[i].getName());
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        for (int i = 0;i < todoArrayList.size(); i++){
            todoArrayList.get(i).save_Files(i);
        }
    }

    View.OnClickListener add_button = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            index++;
            addView(index,"MEMO No" + 0 + "TODO No" + index);
        }
    };




    private void addView(int i,String fileName){
        EditText editText = new EditText(this);

        todo add_Todo = new todo(this,null ,fileName);
        todoArrayList.add(add_Todo);

        add_Todo(add_Todo,i);
        add_EditSettings(editText,i);
        //remove
        add_Todo.x_button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                remove_view(add_Todo, editText);

                for (int i = 0;i < todoArrayList.size(); i++){
                    todoArrayList.get(i).save_Files(i);
                }
                for (int i = add_Todo.getId();i < todoArrayList.size();i++){
                    todoArrayList.get(i).setId(i);
                    Log.d("FileName1", String.valueOf(i));
                }

                //deleteFile(file_List[add_Todo.getId()].getName());
            }
        });
        save();
    }

    private void add_Todo(todo todo_View,int id){
        todo_View.setId(id);
        layout.addView(todo_View);
    }

    private void add_EditSettings(EditText editText,int id){
        //editTextの下線を透明色に
        editText.setBackgroundColor(Color.parseColor("#00000000"));
        editText.setLineSpacing(105,0);
        editText.setTextSize(22);
        editText.setId(id);
        layout.addView(editText);
    }

    private void save(){
        SharedPreferences.Editor editor = index_Save.edit();
        editor.putInt("view_index",index);
        editor.apply();
    }

    private void remove_view (View todo,View edit){
        layout.removeView(todo);
        layout.removeView(edit);
        index--;
        save();
    }
     */

}