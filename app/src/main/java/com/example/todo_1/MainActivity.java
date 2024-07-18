package com.example.todo_1;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
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

    int index = 1;

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

    private void setView(){
        //ファイルの作成
        index_Save = getApplicationContext().getSharedPreferences("todo_index", Context.MODE_PRIVATE);
        int date = index_Save.getInt("view_index",0);
        index = date;

        Cos_EditText cosEditText = new Cos_EditText(this,null);
        cosEditText.setId(0);
        layout.addView(cosEditText);
        Log.d("数値を取得しました", String.valueOf(index));

        if (index != 0){
            for (int i = 0;i < index; i++){
                addTodoView(i,"MEMO No" + 0 + "TODO No" + i);
                Log.d("FileNameStart","MEMO No" + 0 + "TODO No" + i);
            }
        }
    }
    @Override
    protected void onStop() {
        super.onStop();
        for (int i = 0; i < todoViewArrayList.size(); i++){
            try {
                Log.d("保存中", String.valueOf(todoViewArrayList.get(i).getId()));
                todoViewArrayList.get(i).save_JsonArray();
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            Log.d("保存が成功しました", String.valueOf(todoViewArrayList.get(i).getId()));

        }
    }

    View.OnClickListener add_button = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            addTodoView(index,"MEMO No" + 0 + "TODO No" + index);
            index++;
            save();
        }
    };



    private void addTodoView(int i,String fileName){
        TodoView add_TodoView = new TodoView(this,null ,fileName,getSupportFragmentManager());
        todoViewArrayList.add(add_TodoView);
        add_TodoView.setId(i);
        layout.addView(add_TodoView);



        Cos_EditText cosEditText = new Cos_EditText(this,null);
        cosEditText.setId(i);
        layout.addView(cosEditText);

        Log.d("ファイルが追加されました", String.valueOf(i));
        //remove
        add_TodoView.x_button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                add_TodoView.deleteDate(fileName);
                remove_view(add_TodoView, cosEditText);
                todoViewArrayList.remove(index);
                for (int i = add_TodoView.getId(); i < todoViewArrayList.size(); i++){
                    todoViewArrayList.get(i).setId(i);
                    Log.d("FileName1", String.valueOf(i));
                }

                //deleteFile(file_List[add_Todo.getId()].getName());
            }
        });
        save();
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