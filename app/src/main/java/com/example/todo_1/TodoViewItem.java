package com.example.todo_1;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TodoViewItem extends RecyclerView.Adapter<TodoViewItem.TodoHolder> {

    private final List<TodoData> dataArray;
    private final FragmentManager fm;
    boolean checked = false;
    private OnItemLongClickListener listener;

    //呼び出されたときにListを設定してもらう
    public TodoViewItem(List<TodoData> list,FragmentManager fragmentManager) {
        this.dataArray = list;
        this.fm = fragmentManager;
    }

    //Start
    @NonNull
    @Override
    public TodoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //itemのxml
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_item_row, parent, false);
        return new TodoHolder(inflate,fm);
    }

    //Viewの更新とデータとの紐づけ
    @Override
    public void onBindViewHolder(@NonNull TodoHolder holder, @SuppressLint("RecyclerView") int position) {
        //ポジションのviewからデータを取得
        holder.getEditTextView().setText(dataArray.get(position).getText());
        holder.getEditTextView().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //変更された時のみ実行するため、変更されたことを知らせる
                checked = true;
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (checked){
                    checked = false;
                    dataArray.get(position).setText(String.valueOf(holder.getEditTextView().getText()));
                }
            }
        });
        holder.getCheckBoxView().setChecked(dataArray.get(position).getboolean());
        holder.getCheckBoxView().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                dataArray.get(position).setBool(isChecked);
            }
        });


        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (listener != null) {
                    //長押しされたら他クラスで実装されたメソッドを呼び出す→リスナーを他クラスに共有できる
                    //呼び出されたメソッド全てに値の提供
                    listener.onItemLongClickListener(v, position);
                }
                return true;
            }
        });
        holder.getCheckBoxView().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                listener.onItemLongClickListener(v, position);
                return true;
            }
        });
    }

    //内容入力クラス
    public interface OnItemLongClickListener {
        //リスナー側呼び出しメソッド（内容は他クラス）
        void onItemLongClickListener(View view, int position);
    }

    //他クラスで内容入力クラスを呼び出すメソッド（これがないと継承してクラス全体が箱になる？）
    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        //渡されたインスタンスで、長押しされたときに呼び出したいメソッド（場所）を指定
        this.listener = listener;
    }

    //itemの数
    @Override
    public int getItemCount() {
        return dataArray.size();
    }



    public static class TodoHolder extends RecyclerView.ViewHolder{
        CheckBox checkBox;
        EditText editText;
        //viewの参照
        public TodoHolder(@NonNull View itemView ,FragmentManager fragmentManager) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.checkBox);
            editText = itemView.findViewById(R.id.todo_edit);
        }


        public EditText getEditTextView() {
            return editText;
        }
        public CheckBox getCheckBoxView() {
            return checkBox;
        }
    }

}


