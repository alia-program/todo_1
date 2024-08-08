package com.example.todo_1;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

public class Cos_EditText extends androidx.appcompat.widget.AppCompatEditText {
    EditText editText;

    public Cos_EditText(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(getContext()).inflate(R.layout.cos_edittext,null);
        editText = findViewById(R.id.cosEdit);
    }
}
