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
    TodoViewItem todoViewItem;

    String index_FileName;
    File json_File;
    public JSONArray jsonArray;

    SharedPreferences preferences;
    int index = 0;

    public TodoView(@NonNull Context context, @Nullable AttributeSet attrs , String name) {
        super(context, attrs);
        View.inflate(context,R.layout.todo_style,this);
        x_button = findViewById(com.example.todo_1.R.id.x_button);
        add_button = findViewById(R.id.add_button);
        add_button.setOnClickListener(add_listener);
        todo_scrollView = findViewById(R.id.todo_scroll);
        todoList = findViewById(R.id.todolist);
        index_FileName = name;
        create_Files();
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
            add_TodoViewItem("",false,index);
            Log.d("add_item", String.valueOf(index));
            try {
                //アイテムを配列に入れる
                jsonArray.put(add_json("",false));
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            index++;
        }
    };



    //TodoViewItemの生成
    private void add_TodoViewItem(String content,Boolean checkBox,int id){
        //itemのインスタンスを作成して追加
        todoViewItem = new TodoViewItem(getContext(),null);
        //itemを表示・設定
        todoList.addView(todoViewItem);
        todoViewItem.setEditText(content);
        todoViewItem.setCheckBox(checkBox);
        todoViewItem.setId(id);
        Log.d("vvvvvvvvvvvv", String.valueOf(todoViewItem.getId()));
    }

    private JSONObject add_json(String string,Boolean bool) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("EditText",string);
        jsonObject.put("checkBox",bool);
        return jsonObject;
    }

    //ファイルの作成
    private void create_Files(){
        index = get_Index();

        json_File = new File(getContext().getFilesDir(),index_FileName);
        Log.d("ファイル名",index_FileName + ".json");
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

    //開始時のアイテムセット
    private void setItem() throws JSONException {
        jsonArray = new JSONArray(get_JsonArray());
        for (int i = 0; i < index; i++){
            //Log.d("aaaaaaaaaaaaa","あいてむ1");
            try {
                //番号のインスタンスから保存した内容の取得
                JSONObject json_item = jsonArray.getJSONObject(i);
                String string = json_item.getString("EditText");
                Boolean bool = json_item.getBoolean("checkBox");
                add_TodoViewItem(string ,bool,i);
                //Log.d("aaaaaaaaaaaaa","あいてむ");
            } catch (JSONException e) {
                e.printStackTrace();
            }
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
        //indexの保存
        save_index();

        if (jsonArray.length() >= 0 && get_Index() >= 0){//JsonFileが保存できていない場合、indexとjsonArrayの数が一致しないためエラー
            Log.d("回数", String.valueOf(get_Index()));
            for (int i = 0; i < get_Index() ; i++){
                Log.d("add_item", String.valueOf(i));
                TodoViewItem ed= todoList.findViewById(i);
                jsonArray.put(i,add_json(ed.getEditText(),ed.getCheckBox()));
                Log.d("成功あｄｄ", String.valueOf(get_Index()));
            }
            //Fileにjson形式を保存
            try (FileWriter writer = new FileWriter(json_File)) {
                writer.write(jsonArray.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }

        }else {
            Toast.makeText(getContext() , "ファイルの欠損により保存に失敗しました。", Toast.LENGTH_LONG).show();
            Log.d("error","Jsonファイルが破損しました");
        }

    }

    public int get_Index(){
        SharedPreferences pre = getContext().getSharedPreferences(index_FileName, Context.MODE_PRIVATE);
        int todo_in = pre.getInt("index",0);
        //Log.d("数値を取得しました", String.valueOf(index));
        return todo_in;
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
