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
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class TodoView extends ConstraintLayout{

    ImageButton x_button;
    ImageButton add_button;

    LinearLayout todoList;
    ScrollView todo_scrollView;
    Toolbar toolbar;
    TodoItem todoItem;

    String index_FileName;
    File json_File;
    public JSONArray jsonArray;

    SharedPreferences preferences;
    int index = 1;

    public TodoView(@NonNull Context context, @Nullable AttributeSet attrs , String name) {
        super(context, attrs);
        View.inflate(context,R.layout.todo_style,this);
        x_button = findViewById(R.id.x_button);
        add_button = findViewById(R.id.add_button);
        add_button.setOnClickListener(add_listener);
        todo_scrollView = findViewById(R.id.todo_scroll);
        todoList = findViewById(R.id.todolist);
        create_Files(name);
        index_FileName = name;
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

    //触っても動かないように
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        requestDisallowInterceptTouchEvent(true);
        return super.dispatchTouchEvent(ev);
    }

    View.OnClickListener add_listener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            add_ItemView("",false,index);
            Log.d("add_item", String.valueOf(index));
            index++;
            /*
            try {
                save_JsonArray();
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

             */

        }
    };



    //開始時のアイテムセット
    private void setItem() throws JSONException {
        jsonArray = new JSONArray(get_JsonArray());
        for (int i = 0; i < index; i++){
            try {
                //番号のインスタンスから保存した内容の取得
                JSONObject json_item = jsonArray.getJSONObject(i);
                String string = json_item.getString("EditText");
                Boolean bool = json_item.getBoolean("checkBox");
                add_ItemView(string ,bool,index);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    //TodoItemの生成
    private void add_ItemView(String content,Boolean checkBox,int id){
        //itemのインスタンスを作成して追加
        todoItem = new TodoItem(getContext(),null);
        //itemを表示・設定
        todoList.addView(todoItem);
        todoItem.setEditText(content);
        todoItem.setCheckBox(checkBox);
        todoItem.setId(id);
    }

    //ファイルの作成
    private void create_Files(String name){
        //indexFileの作成
        preferences = getContext().getSharedPreferences(name, Context.MODE_PRIVATE);
        index = preferences.getInt("index",0);

        json_File = new File(getContext().getFilesDir(),name);
        Log.d("ada",name + ".json");
        //JsonFileの作成
        try {
            if (index == 0){
                jsonArray = new JSONArray();
            }else {
                setItem();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void save_index(){
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(index_FileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("index",index);
        editor.apply();
        Log.d(index_FileName, String.valueOf(index));
    }

    public void save_JsonArray() throws JSONException {
        if (jsonArray.length() >= 0 && index >= 0){//JsonFileが保存できていない場合、indexとjsonArrayの数が一致しないためエラー

            for (int i = 0; i < index ; i++){
                //アイテムを格納
                JSONObject j_Item = new JSONObject();
                TodoItem ed= todoList.findViewById(i);
                j_Item.put("EditText",ed.getEditText());
                j_Item.put("checkBox",ed.getCheckBox());
                //アイテムを配列に入れる
                jsonArray.put(i,j_Item);
            }
            //Fileにjson形式を保存
            try (FileWriter writer = new FileWriter(json_File)) {
                writer.write(jsonArray.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
            //indexの保存
            save_index();

        }else {
            Toast.makeText(getContext() , "ファイルの欠損により保存に失敗しました。", Toast.LENGTH_LONG).show();
            Log.d("error","Jsonファイルが破損しました");
        }

    }

    //保存したJsonファイルを取り出す
    private String get_JsonArray(){
        String json_string = "";
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(json_File));
            json_string = bufferedReader.readLine();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json_string;
    }
}
