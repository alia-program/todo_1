package com.example.todo_1;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

public class todo extends ConstraintLayout{

    protected static ImageButton x_button;
    ImageButton add_button;
    LinearLayout todoList;
    View todo_item;

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

        //x_button.setOnClickListener(x_listener);


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

    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //some code....
                v.getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_UP:
                v.performClick();
                break;
            default:
                break;
        }
        return true;
    }

}