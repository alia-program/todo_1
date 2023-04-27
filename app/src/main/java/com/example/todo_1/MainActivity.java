package com.example.todo_1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.ViewCompat;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
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
    int i = 0;
    int x;
    int[] todo_id = new int[20];
    int[] editText_id = new int[20];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.imageButton2);
        editText_style = findViewById(R.id.editText);
        layout = findViewById(R.id.todo_);

        button.setOnClickListener(add_button);

    }



    View.OnClickListener add_button = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            addView();
        }
    };



    private void addView(){
        EditText editText = new EditText(this);
        todo todo1 = new todo(this,null);

        //remove
        todo.x_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remove_view(todo1, editText);
            }
        });

        //add
        add_todo(todo1);
        add_edit(editText);
        i++;

    }




    private void add_todo(View view){
        todo_id[i] = ViewCompat.generateViewId();
        layout.setId('t' + todo_id[i]);

        layout.addView(view);
    }

    private void add_edit(EditText view){
        editText_id[i] = ViewCompat.generateViewId();


        //editTextの下線を透明色に
        view.setBackgroundColor(Color.parseColor("#00000000"));
        view.setLineSpacing(105,0);
        view.setTextSize(22);
        view.setId('e' + todo_id[i]);

        layout.addView(view);
    }

    private void remove_view (View todo,View edit){
        layout.removeView(todo);
        layout.removeView(edit);
    }

}