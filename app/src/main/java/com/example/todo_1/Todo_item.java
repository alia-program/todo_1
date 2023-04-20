package com.example.todo_1;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

public class Todo_item extends ConstraintLayout {

    public Todo_item(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View.inflate(context,R.layout.todo_item_row,this);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d("a","vbbh");
        return super.dispatchTouchEvent(ev);
    }

}


