package com.example.todo_1;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import org.json.JSONException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ImageButton button;
    LinearLayout layout;

    ArrayList<TodoView> todoViewArrayList = new ArrayList<>();
    ArrayList<Cos_EditText> editList = new ArrayList<>();
    ArrayList DataSet[] = {todoViewArrayList,editList};

    int index = editList.size();
    String fileName = "MEMOdata.dat";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.imageButton2);
        button.setOnClickListener(add_button);
        layout = findViewById(R.id.todo_);

        read();
        setView();

    }

    private void read(){
        try {
            FileInputStream fileIN = new FileInputStream(fileName);
            ObjectInputStream objectIN = new ObjectInputStream(fileIN);
            editList = (ArrayList<Cos_EditText>) objectIN.readObject();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void save(){
        try {
            FileOutputStream fileIN = new FileOutputStream(fileName);
            ObjectOutputStream objectIN = new ObjectOutputStream(fileIN);
            objectIN.writeObject(editList);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private void setView(){

        Cos_EditText cosEditText = new Cos_EditText(this,null);
        add_View(editList,cosEditText,0);


        Log.d("数値を取得しました", String.valueOf(index));

        if (index != 0){
            for (int i = 0;i < index; i++){
                addTodoView(i);
                Log.d("FileNameStart","MEMO No" + 0 + "TODO No" + i);

                Cos_EditText add_EditText = new Cos_EditText(this,null);
                add_View(editList,add_EditText,i+1);
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        for (int i = 0; i < todoViewArrayList.size(); i++){
            try {
                Log.d("保存中", String.valueOf(todoViewArrayList.get(i)));
                todoViewArrayList.get(i).save_JsonArray();
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            Log.d("保存が成功しました", String.valueOf(todoViewArrayList.get(i)));

        }

        save();
    }

    View.OnClickListener add_button = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            addTodoView(index);
        }
    };

    private void addTodoView(int i){

        TodoView add_TodoView = new TodoView(this,null ,"MEMO No" + 0 + "TODO No" + i,getSupportFragmentManager());
        add_View(todoViewArrayList,add_TodoView,i);
        Cos_EditText cosEditText = new Cos_EditText(this,null);
        add_View(editList,cosEditText,i + 1);
        Log.d("ファイルが追加されました", String.valueOf(i));

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

                /*
                //idの変更
                for (int i = add_TodoView.getId(); i < todoViewArrayList.size(); i++){
                    todoViewArrayList.get(i).setId(i);
                    editList.get(i).setId(i + 1);
                    Log.d("アイテムが削除されidが変更されました", String.valueOf(i));
                }

                 */
            }
        });
    }

    private void add_View(ArrayList list,View view,int index){
        layout.addView(view);
        list.add(index);
    }

    private void remove_view (View todo,View edit,int index){
        layout.removeView(todo);
        layout.removeView(edit);
        todoViewArrayList.remove(index);
        editList.remove(index + 1);
    }



}