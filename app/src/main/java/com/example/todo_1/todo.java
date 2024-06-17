package com.example.todo_1;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class todo extends ConstraintLayout{

    protected static ImageButton x_button;
    ImageButton add_button;

    LinearLayout todoList;

    ScrollView todo_scrollView;
    Toolbar toolbar;

    String index_FileName;

    Todo_item edit;
    public int index = 1;

    JSONArray jsonArray = new JSONArray();

    SharedPreferences preferences;

    public todo(@NonNull Context context, @Nullable AttributeSet attrs ,String name) throws JSONException {
        super(context, attrs);
        View.inflate(context,R.layout.todo_style,this);

        x_button = findViewById(R.id.x_button);
        x_button.setOnClickListener(x_listener);
        add_button = findViewById(R.id.add_button);
        add_button.setOnClickListener(add_listener);

        todo_scrollView = findViewById(R.id.todo_scroll);
        todoList = findViewById(R.id.todolist);

        create_Files(name);
        index_FileName = name;

        add_JsonArray();

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
    public todo(@NonNull Context context,String filename) {
        super(context);

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        requestDisallowInterceptTouchEvent(true);
        return super.dispatchTouchEvent(ev);
    }

    View.OnClickListener x_listener = new OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };
    View.OnClickListener add_listener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            add_ItemView("",false,0,index);
            Log.d("add_item", String.valueOf(index));
            Log.d("add_item", String.valueOf(index_FileName));
            index++;
            save_Files(index);
        }
    };






    /*Indexの数値保存
    private void save_index(){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("index",index);
        editor.apply();
    }
     */

    public void add_JsonArray() throws JSONException {

    }

    //Jsonの作成
    public String get_JsonArray() throws JSONException {

        if (jsonArray.length() >= 0 && index >= 0){//JsonFileが保存できていない場合、indexとjsonArrayの数が一致しないためエラー
            for (int view_i = 1; view_i < index ; view_i++){
                //アイテム作成
                JSONObject json_item = new JSONObject();
                Todo_item ed= todoList.findViewById(view_i);

                json_item.put("Text",ed.getEditText());
                json_item.put("checkBox",ed.getCheckBox());
                //アイテムを配列に入れる
                jsonArray.put(view_i,json_item);

                Log.d("一行目Name", jsonArray.getJSONArray(0).getJSONObject(0).getString("Text"));
                Log.d("Json作成成功", jsonArray.toString(4));
                Log.d("Json作成成功", jsonArray.toString());
            }
            //indexの保存
            save_Files(index);

        }else {
            Toast.makeText(getContext() , "ファイルの欠損により保存に失敗しました。", Toast.LENGTH_LONG).show();
            Log.d("error","Jsonファイルが破損しました");
        }
        return jsonArray.toString(4);
    }

    //viewの生成
    private void add_ItemView(String s,Boolean b,int i,int id){
        //itemのインスタンスを作成して追加
        edit = new Todo_item(getContext(),null);
        todoList.addView(edit);

        //引数を値に
        edit.setEditText(s);
        edit.setCheckBox(b);
        if (i == 0){//スタート用
            edit.setId(index);
            //Log.d("error", String.valueOf(index));
        }else {//再開用
            edit.setId(id);
        }
    }

    public void save_Files(int i){
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(index_FileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("index2",i);
        editor.apply();
        Log.d(index_FileName, String.valueOf(i));
    }

    //ファイルの作成
    public void create_Files(String name){
        //indexFileの作成
        preferences = getContext().getSharedPreferences(name, Context.MODE_PRIVATE);
        index = preferences.getInt("index2",0);

        /*
                json_File = new File(getContext().getFilesDir(),name);
        Log.d("ada",name + ".json");
        //JsonFileの作成
        try {
            object = new JSONObject();
            if (index == 0){
                jsonArray = new JSONArray();
            }else {
                setItem();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
         */

    }
}
