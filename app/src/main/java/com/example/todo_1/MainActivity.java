package com.example.todo_1;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

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

    ArrayList<TodoView> todoViewArrayList = new ArrayList<>();
    ArrayList<Cos_EditText> editList = new ArrayList<>();

    JSONArray memoArray;
    JSONArray editJSON;
    File json_File;


    int index;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.imageButton2);
        button.setOnClickListener(add_button);
        layout = findViewById(R.id.todo_);

        createFile();

    }

    private void createFile(){
        try {
            json_File = new File(getFilesDir(),"memo.json");

            Cos_EditText cosEditText = new Cos_EditText(this,null);
            layout.addView(cosEditText);

            if (json_File.exists()){
                Log.d("FileNameStart","");
                memoArray = new JSONArray(read());
                index = memoArray.length();
                editJSON = new JSONArray(memoArray.getJSONArray(0));

                for (int i = 0;i < index; i++){
                    addTodoView(i);
                    Log.d("FileNameStart","MEMO No" + 0 + "TODO No" + i);
                }

                for (int i = 0;i < index; i++){
                    editList.get(i).setText(editJSON.getJSONObject(i).getString("MEMO_content"));
                    Log.d("テキストがセットされました", (String) editJSON.get(i));
                }

            }else {
                memoArray = new JSONArray();
                editJSON = new JSONArray();
                memoArray.put(0 , editJSON);
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        //TodoのSave
        for (int i = 0; i < todoViewArrayList.size(); i++){
            memoArray.put(todoViewArrayList.get(i).getJsonArray());
        }

        for (int i = 0; i < editList.size(); i++){
            try {
                editJSON.put(i,editList.get(i));
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }

        //Fileにjson形式を保存
        try (FileWriter writer = new FileWriter(json_File)) {
            writer.write(memoArray.toString());
            Log.d("保存が成功しました", memoArray.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    View.OnClickListener add_button = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            addTodoView(todoViewArrayList.size());
        }
    };

    private void addTodoView(int i){
        try {
            JSONArray todo = new JSONArray();
            memoArray.put(todo);

            TodoView add_TodoView = new TodoView(this,null ,memoArray.getString(i+1),getSupportFragmentManager());
            layout.addView(add_TodoView);
            todoViewArrayList.add(add_TodoView);

            JSONObject content = new JSONObject();
            content.put("MEMO_content","");
            editJSON.put(content);

            Cos_EditText cosEditText = new Cos_EditText(this,null);
            layout.addView(cosEditText);
            editList.add(cosEditText);

            Log.d("ファイルが追加されました", String.valueOf(index));

            //remove
            add_TodoView.x_button.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onClick(View v) {
                    //Todoのデータ削除
                    add_TodoView.deleteDate("MEMO No" + 0 + "TODO No" + i);
                    Log.d("ファイル削除", String.valueOf(i));
                    //Viewの削除
                    remove_view(add_TodoView, cosEditText, i);



                }
            });
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    private void remove_view (View todo,View edit,int index){
        layout.removeView(todo);
        layout.removeView(edit);
        todoViewArrayList.remove(index);
        editList.remove(index);
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



    public void deleteDate(String deleteFileName){
        File deleteFile = new File(getFilesDir(),deleteFileName);
        deleteFile.delete();
        Log.d("削除に成功しました", deleteFileName);
    }

}