package com.example.todo_1;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

public class TodoItem extends ConstraintLayout implements CompoundButton.OnCheckedChangeListener {

    CheckBox checkBox;
    EditText editText;

    String s;
    Boolean b;

    public TodoItem(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View.inflate(context,R.layout.todo_item_row,this);
        checkBox = findViewById(R.id.checkBox);
        editText = findViewById(R.id.todo_edit);

        checkBox.setOnCheckedChangeListener(this);
    }

    public String getEditText() {
        s = String.valueOf(editText.getText());
        return s;
    }

    public void setEditText(String string) {
        editText.setText(string);
    }


    public Boolean getCheckBox() {
        b = checkBox.isChecked();
        return b;
    }

    public void setCheckBox(Boolean bool) {
        checkBox.setChecked(bool);
    }


    public Object get_item(){
     Object[] o = {getCheckBox(),getEditText()};
        return o;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        //Log.d("sas", String.valueOf(getEditText()));
        //Log.d("sab", String.valueOf(getCheckBox()));
    }
}


