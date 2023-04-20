package com.example.todo_1;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
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
    int i = 0;

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

        //toolbarとScrollViewのスクロールを一時停止
        toolbar = findViewById(R.id.toolbar);
        toolbar.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                requestDisallowInterceptTouchEvent(false);
                return false;
            }
        });
        todo_scrollView.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (i == 0){
                    Log.d("a","null item");
                    requestDisallowInterceptTouchEvent(false);
                }
                return false;
            }
        });



    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        requestDisallowInterceptTouchEvent(true);
        return super.dispatchTouchEvent(ev);
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
        Todo_item todo_item = new Todo_item(getContext(),null);
        Log.d("a","s");
        todoList.addView(todo_item);
        i++;
    }


}
