package com.example.todo_1;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TodoView extends ConstraintLayout implements Cos_Dialog.DialogListener {

    ImageButton x_button;
    ImageButton add_button;

    ScrollView todo_scrollView;
    Toolbar toolbar;

    public JSONArray jsonArray;
    List<TodoData> dataArray = new ArrayList<>();
    int index;
    TodoViewItem todoViewItem;
    int listenerPosition;


    public TodoView(@NonNull Context context, @Nullable AttributeSet attrs , JSONArray jsonString, FragmentManager fragmentManager) {
        super(context, attrs);
        View.inflate(context,R.layout.todo_style,this);
        x_button = findViewById(com.example.todo_1.R.id.x_button);
        //x_button.setOnClickListener(x_listener);
        add_button = findViewById(R.id.add_button);
        add_button.setOnClickListener(add_listener);
        todo_scrollView = findViewById(R.id.todo_scroll);
        RecyclerView recyclerView = findViewById(R.id.todolist);

        create_Files(jsonString);

        //viewの管理（追加・削除など）(仮定)
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(manager);
        todoViewItem = new TodoViewItem(dataArray,fragmentManager);
        recyclerView.setAdapter(todoViewItem);
        todoViewItem.notifyDataSetChanged();

        //引数が必要なため、変数としてインターフェースをインスタンス化して渡す→呼び出されたときメソッドの内容を定義するためメソッドが出現する
        todoViewItem.setOnItemLongClickListener(new TodoViewItem.OnItemLongClickListener() {
            @Override
            public void onItemLongClickListener(View view, int position) {
                listenerPosition = position;
                show_Dialog(fragmentManager);
            }
        });


        ItemTouchHelper.SimpleCallback item_Swipe = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                //上下
                int nowPos = viewHolder.getAdapterPosition();
                int afPos = target.getAdapterPosition();
                todoViewItem.notifyItemMoved(nowPos, afPos);
                return true;
            }
            //左右
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                listenerPosition = viewHolder.getAdapterPosition();
                show_Dialog(fragmentManager);
                todoViewItem.notifyDataSetChanged();
            }
            //描写
            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }

        };
        new ItemTouchHelper(item_Swipe).attachToRecyclerView(recyclerView);
        //toolbarとScrollViewのスクロールを停止
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
                if (index == 0){
                    requestDisallowInterceptTouchEvent(false);
                }
                return false;
            }
        });
    }
    View.OnClickListener add_listener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            TodoData todoData = new TodoData("",false);
            dataArray.add(todoData);
            todoViewItem.notifyDataSetChanged();
            try {
                //アイテムを配列に入れる
                jsonArray.put(add_json("",false));
                Log.d("add_item", String.valueOf(index));
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            index++;
        }
    };
    private OnClickListener listener;
    //触っても動かないように
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        requestDisallowInterceptTouchEvent(true);

        if (ev.getAction() == MotionEvent.ACTION_UP) {
            if (listener != null) listener.onClick(this);
        }

        return super.dispatchTouchEvent(ev);
    }
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_UP && (event.getKeyCode() == KeyEvent.KEYCODE_DPAD_CENTER || event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
            if (listener != null) listener.onClick(this);
        }
        return super.dispatchKeyEvent(event);
    }
    public void setOnClickListener(OnClickListener listener) {
        this.listener = listener;
    }




    private JSONObject add_json(String string,Boolean bool) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("EditText",string);
        jsonObject.put("checkBox",bool);
        return jsonObject;
    }

    //ファイルの作成
    private void create_Files(JSONArray json){

            jsonArray = json;
            index = jsonArray.length();

            if (0 < index){
                Log.d("ファイル数", String.valueOf(jsonArray.length()));
                for (int i = 0; i < index; i++){
                    try {
                        //番号のインスタンスから保存した内容の取得
                        JSONObject json_item = jsonArray.getJSONObject(i);
                        String string = json_item.getString("EditText");
                        Boolean bool = json_item.getBoolean("checkBox");

                        TodoData todoData = new TodoData(string,bool);
                        dataArray.add(todoData);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }else {
                jsonArray = new JSONArray();
                Log.d("newFile","新しくファイルが作成されます");
            }

    }


    public JSONArray getJsonArray() {
        for (int i = 0; i < index ; i++){
            try {
                jsonArray.put(i,add_json(dataArray.get(i).getText(),dataArray.get(i).getboolean()));

            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
        return jsonArray;
    }

    //Todoファイルの削除
    public void deleteDate(String deleteFileName){
        //File deleteFile = new File(getContext().getFilesDir(),deleteFileName);
        //deleteFile.delete();
        Log.d("削除に成功しました", deleteFileName);
    }

    //Todoアイテムの削除
    public void deleteItem(int id){
        dataArray.remove(id);
        todoViewItem.notifyDataSetChanged();
        jsonArray.remove(id);
        index--;
    }






    public void show_Dialog(FragmentManager fragmentManager){
        Cos_Dialog dialogFragment = new Cos_Dialog();
        Bundle args = new Bundle();
        args.putString("CONTENT", "このアイテムを削除しますか？");
        args.putString("POSITIVE", "削除");
        args.putString("NEGATIVE", "キャンセル界隈");
        dialogFragment.setArguments(args);
        dialogFragment.setDialogListener(this);
        dialogFragment.setCancelable(false);

        dialogFragment.show(fragmentManager, "my_dialog");
    }

    @Override
    public DialogInterface.OnClickListener onPositiveListener() {
        deleteItem(listenerPosition);
        Log.d("add_item","削除されました");
        return null;
    }

    @Override
    public DialogInterface.OnClickListener onNegativeListener() {
        return null;
    }
}
