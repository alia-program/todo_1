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

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ImageButton button;
    LinearLayout layout;

    int index = 0;

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

}