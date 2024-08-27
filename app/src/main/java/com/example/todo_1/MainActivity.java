package com.example.todo_1;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

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

public class MainActivity extends AppCompatActivity {

    ImageButton button;
    LinearLayout layout;
    Toolbar toolbar;

    ArrayList<TodoView> todoViewArrayList = new ArrayList<>();
    ArrayList<Cos_EditText> editList = new ArrayList<>();

    JSONArray todoJSON;
    JSONArray editJSON;
    JSONArray memoArray;
    File json_File;
    Cos_EditText cosEditText;


    int index;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.imageButton2);
        button.setOnClickListener(add_button);
        layout = findViewById(R.id.todo_);
        toolbar = findViewById(R.id.toolbar2);


        createFile();
    }


    private void createFile(){
        try {
            //Fileの呼び出し
            json_File = new File(getFilesDir(),"memo.json");

            //jsonの作成
            if (json_File.exists()){
                Log.d("FileNameStart","");

                //jsonの読み込み
                memoArray = new JSONArray(read());
                //配列の初期化
                editJSON = memoArray.getJSONArray(0);
                todoJSON = memoArray.getJSONArray(1);
                //数の確認
                index = todoJSON.length();

                cosEditText = new Cos_EditText(this,null);
                add_EditText();

                cosEditText.setText(editJSON.getJSONObject(0).getString("content"));

                //Todoの追加
                for (int i = 0;i < index; i++){
                    addTodo(i,todoJSON.getJSONArray(i));
                    Log.d("FileNameStart","MEMO No" + 0 + "TODO No" + i);
                }
                //EditTextの文字セット
                for (int i = 1;i < index + 1; i++){
                    editList.get(i).setText(editJSON.getJSONObject(i).getString("content"));

                    Log.d("テキストがセットされました", (String) editJSON.get(i).toString());
                }

            }else {
                //JSON配列の作成
                memoArray = new JSONArray();
                editJSON = new JSONArray();
                todoJSON = new JSONArray();
                //箱にTodoとEditの配列追加
                memoArray.put(0 , editJSON);
                memoArray.put(1 , todoJSON);
                //editText
                cosEditText = new Cos_EditText(this,null);
                add_EditText();
            }

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        //Todoの内容をJSONに追加
        for (int i = 0; i < todoViewArrayList.size(); i++){
            todoJSON.remove(i);
            todoJSON.put(todoViewArrayList.get(i).getJsonArray());
            Log.d("保存:TODO", todoJSON.toString());
        }

        //EditTextの内容をJSONに追加
        for (int i = 0; i < editList.size(); i++){
            try {
                JSONObject editContent = new JSONObject();
                editContent.put("content",editList.get(i).getText());
                editJSON.put(i,editContent);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
        Log.d("保存:EditText", editJSON.toString());



        //Fileにjson形式を保存
        try (FileWriter writer = new FileWriter(json_File)) {
            writer.write(memoArray.toString());
            Log.d("保存が成功しました", memoArray.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Todoの追加
    View.OnClickListener add_button = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            JSONArray todoJSONArray = new JSONArray();
            addTodo(todoViewArrayList.size(),todoJSONArray);
        }
    };

    //TodoViewを作成するメソッド
    private void addTodo(int i,JSONArray todoJsonArray){

        //Viewと配列の追加
        TodoView add_TodoView = new TodoView(this,null,todoJsonArray,getSupportFragmentManager());
        layout.addView(add_TodoView);
        todoViewArrayList.add(add_TodoView);

        //EditTextの追加
        cosEditText = new Cos_EditText(this,null);
        add_EditText();

        Log.d("ファイルが追加されました", String.valueOf(todoViewArrayList.size()));

        //remove
        add_TodoView.x_button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                Log.d("ファイル削除", String.valueOf(i));
                //Viewの削除
                remove_view(add_TodoView, cosEditText);
                Log.d("ファイル削除されました", memoArray.toString());
            }
        });

    }

    private void remove_view (View todo,View edit){
        int todo_index = todoViewArrayList.indexOf(todo);
        int edit_index = editList.indexOf(edit);

        String prevEditText = String.valueOf(editList.get(edit_index - 1).getText());
        String deleteEditText = String.valueOf(editList.get(edit_index).getText());

        if (deleteEditText.isEmpty()){

        }else {
            editList.get(edit_index - 1).setText(prevEditText + "\n" + deleteEditText);
        }
        //jsonアイテムの削除
        todoJSON.remove(todoViewArrayList.indexOf(todo_index));
        editJSON.remove(editList.indexOf(edit_index));
        //View配列の削除
        todoViewArrayList.remove(todo_index);
        editList.remove(edit_index);
        //Viewの削除
        layout.removeView(todo);
        layout.removeView(edit);
    }

    private String read(){
        String json_string = "";
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(json_File));
            json_string = bufferedReader.readLine();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("読み込みが完了しました", json_string);
        return json_string;
    }

    private void add_EditText(){
        Log.d("削除に成功しました", String.valueOf(toolbar.getHeight()));
        layout.addView(cosEditText);
        editList.add(cosEditText);
    }

    public void deleteDate(String deleteFileName){
        File deleteFile = new File(getFilesDir(),deleteFileName);
        deleteFile.delete();
        Log.d("削除に成功しました", deleteFileName);
    }
}