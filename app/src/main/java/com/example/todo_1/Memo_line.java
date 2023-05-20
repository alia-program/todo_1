package com.example.todo_1;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowMetrics;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;

public class Memo_line extends NestedScrollView {
    int display_height = 0;
    int display_width = 0;
    int editText_Size = 0;
    int line_Color = 0;
    Paint paint = new Paint();
    TypedArray array;


    public Memo_line(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        //attrsがないとXmlの値取得できない
        array= context.obtainStyledAttributes(attrs,R.styleable.Memo_line);
        //edittextのマージンのせいかずれるの防止-10
        editText_Size = array.getDimensionPixelSize(R.styleable.Memo_line_lineSize, 100);
        line_Color = array.getColor(R.styleable.Memo_line_lineColor,Color.parseColor("#acacac"));
        Log.d("aa", String.valueOf(line_Color));
    }

    public Memo_line(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        View child = this.getChildAt(0);
        int height = child.getHeight();
        int line_Count = height/editText_Size;
        int fast_Count = this.getHeight()/editText_Size;

        paint.setColor(line_Color);
        paint.setStrokeWidth(4);

        //行数のログ
        //Log.d("aaa", String.valueOf(fast_Count));
        //Scrollviewの子要素全体のサイズ取得
        for (int i = 1; i < line_Count; i++){
            canvas.drawLine(0, editText_Size*i, canvas.getWidth(),  editText_Size*i, paint);
        }
        //最初の一回はviewの中身がないためScrollView自体のサイズの取得
        if (line_Count < 11){
            for (int fi = 1; fi < fast_Count;fi++)
                canvas.drawLine(0, editText_Size*fi, canvas.getWidth(),  editText_Size*fi, paint);
        }

        super.onDraw(canvas);
    }
}
