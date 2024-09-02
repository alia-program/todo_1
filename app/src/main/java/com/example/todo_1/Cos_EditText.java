package com.example.todo_1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Cos_EditText extends androidx.appcompat.widget.AppCompatEditText {

    int editWidth = 800;
    float editHeight = 100;
    float editTextSize = 70;
    float editMargin = 10;

    public Cos_EditText(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Cos_EditText(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        setTextsize(20);
        setEditText(this);

        this.setBackgroundColor(Color.parseColor("#00000000"));

        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        for (int i = 1;i < 50; i++){
            canvas.drawLine(10, editTextSize * i +40, editWidth - 10, editTextSize * i +40, paint);
        }
    }

    /*
    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);

        edit.setOnEditorActionListener(new OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                return false;
            }
        });
    }

 */

    //改行したときできるようにしたい


    public void setEditText(EditText editText){
        this.editTextSize = (float) (editText.getTextSize() + 9);

        this.editWidth = editText.getWidth();
        this.editHeight = editText.getHeight();

        //Log.d("margin", String.valueOf(editMargin));
        //Log.d("height", String.valueOf(editTextSize));
    }

    public void setTextsize (int size){
        this.setTextSize(size);
    }
}
