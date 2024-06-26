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

import org.json.JSONException;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ImageButton button;
    LinearLayout layout;

    int index = 0;

    File file;
    File[] file_List;
    ArrayList<TodoView> todoViewArrayList = new ArrayList<>();
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
    protected void onStop() {
        super.onStop();
        for (int i = 0; i < todoViewArrayList.size(); i++){
            try {
                Log.d("保存中id", String.valueOf(todoViewArrayList.get(i).getId()));
                todoViewArrayList.get(i).save_JsonArray();
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            Log.d("保存が成功しましたid", String.valueOf(todoViewArrayList.get(i).getId()));

        }
    }

    View.OnClickListener add_button = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            index++;
            addView(index,"MEMO No" + 0 + "TODO No" + index);
            Log.d("uuuuuu", String.valueOf(index));
        }
    };


    private void setView(){
        //ファイルの作成・数値の入力
        index_Save = getApplicationContext().getSharedPreferences("todo_index", Context.MODE_PRIVATE);
        int date = index_Save.getInt("view_index",0);
        index = date;
        Log.d("StartIndex", String.valueOf(index));

        //最初のEditText
        EditText editText = new EditText(this);
        add_EditSettings(editText,0);

        if (index != 0){
            for (int i = 0;i < index; i++){
                addView(i,"MEMO No" + 0 + "TODO No" + i);
                Log.d("Todo id","MEMO No" + 0 + "TODO No" + i);
            }
        }
    }

    private void addView(int i,String fileName){

        TodoView add_TodoView = new TodoView(this,null ,fileName);
        add_TodoSettings(add_TodoView,i);
        todoViewArrayList.add(add_TodoView);

        EditText editText = new EditText(this);
        add_EditSettings(editText,i);

        //remove
        add_TodoView.x_button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                remove_view(add_TodoView, editText);
                todoViewArrayList.remove(index);

                for (int i = 0; i < todoViewArrayList.size(); i++){
                    todoViewArrayList.get(i).save_index();
                }
                for (int i = add_TodoView.getId(); i < todoViewArrayList.size(); i++){
                    todoViewArrayList.get(i).setId(i);
                    Log.d("FileName1", String.valueOf(i));
                }

                //deleteFile(file_List[add_Todo.getId()].getName());
            }
        });
        save();
    }

    private void add_TodoSettings(TodoView todoView_View, int id){
        todoView_View.setId(id);
        layout.addView(todoView_View);
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
        Log.d("index保存成功", String.valueOf(index_Save.getInt("view_index",0)));
    }

    private void remove_view (View todo,View edit){
        layout.removeView(todo);
        layout.removeView(edit);
        index--;
        save();
    }

}