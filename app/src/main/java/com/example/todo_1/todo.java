package com.example.todo_1;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

public class todo extends ConstraintLayout{

    protected static ImageButton x_button;
    ImageButton add_button;

    LinearLayout todoList;
    ConstraintLayout c;

    View todo_item;

    private OnClickListener listener;

    ScrollView todo_scrollView;
    Toolbar toolbar;

    public todo(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View.inflate(context,R.layout.todo_style,this);

        x_button = findViewById(R.id.x_button);
        add_button = findViewById(R.id.add_button);

        add_button.setOnClickListener(add_listener);
        todo_scrollView = findViewById(R.id.todo_scroll);

        todoList = findViewById(R.id.todolist);
        c = findViewById(R.id.layout_c);
        toolbar = findViewById(R.id.toolbar);


        todo_scrollView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d("a","a");
                requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
    }



    View.OnClickListener x_listener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.d("a","s");
        }
    };
    View.OnClickListener add_listener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            addview();
        }
    };




    private void addview(){
        todo_item = inflate(getContext(),R.layout.todo_item_row,null);
        todoList.addView(todo_item);
    }


    @Override
    public boolean performClick()
    {
        super.performClick();
        return true;
    }








}
