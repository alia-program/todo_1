package com.example.todo_1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.ViewCompat;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    ImageButton button;
    LinearLayout layout;
    EditText editText_style;
    int index = 0;
    int x;

    todo add_Todo;

    SharedPreferences index_Save;


    int memoIndex = 0;
    int todoIndex = 0;
    String file_Name = memoIndex + "_"+ todoIndex + "saveTodo.json";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.imageButton2);
        //editText_style = findViewById(R.id.editText);
        layout = findViewById(R.id.todo_);

        button.setOnClickListener(add_button);

        //ファイルの作成
        index_Save = getSharedPreferences("todo_index" + 1, Context.MODE_PRIVATE);
        int date = index_Save.getInt("view_index",0);
        index = date;
        //Log.d("ddd", String.valueOf(date));

        if (index != 0){
            for (int i = 0;i < index; i++){
                addView(i);
                //Log.d("dawrawer", String.valueOf(date));
            }
        }else {

        }

    }

    @Override
    protected void onStart() {
        super.onStart();

        //editTextの生成
        EditText editText = new EditText(this);
        add_edit(editText);

    }

    @Override
    protected void onStop() {
        super.onStop();


        //Log.d("ssssssssssss", String.valueOf(index));
    }

    View.OnClickListener add_button = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            index++;
            addView(index);
        }
    };




    private void addView(int i){
        EditText editText = new EditText(this);
        add_Todo = new todo(this,null ,"MEMO No" + 0 + "TODO No" + i);
        //Log.d("erroraaaa", "MEMO No" + 0 + "TODO No" + i);
        //add
        add_edit(editText);
        add_todo(add_Todo);
        //remove
        todo.x_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remove_view(add_Todo, editText);
            }
        });
        save();
    }

    private void add_todo(todo todo_View){
        todo_View.setId('t' + index);

        layout.addView(todo_View);
    }

    private void add_edit(EditText editText){

        //editTextの下線を透明色に
        editText.setBackgroundColor(Color.parseColor("#00000000"));
        editText.setLineSpacing(105,0);
        editText.setTextSize(22);
        editText.setId('e' + index);

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
    }

}