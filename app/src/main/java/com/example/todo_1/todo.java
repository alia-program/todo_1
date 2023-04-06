package com.example.todo_1;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

public class todo extends ConstraintLayout{

    protected static ImageButton x_button;
    ImageButton add_button;
    LinearLayout todoList;
    View tood_item;

    public todo(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View.inflate(context,R.layout.todo_style,this);

        x_button = findViewById(R.id.x_button);
        add_button = findViewById(R.id.add_button);

        todoList = findViewById(R.id.todolist);

        //x_button.setOnClickListener(x_listener);
        add_button.setOnClickListener(add_listener);
    }

    View.OnClickListener x_listener = new OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };
    View.OnClickListener add_listener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            addview();
        }
    };

    private void addview(){
        tood_item = inflate(getContext(),R.layout.todo_item_row,null);
        todoList.addView(tood_item);


    }




}
