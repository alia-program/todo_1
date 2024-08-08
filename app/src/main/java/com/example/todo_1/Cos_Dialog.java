package com.example.todo_1;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.EventListener;

public class Cos_Dialog extends DialogFragment {
    //初期化
    private DialogListener listener;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Activity activity = getActivity();
        if (activity == null) {
            return super.onCreateDialog(savedInstanceState);
        }

        String content = requireArguments().getString("CONTENT", "");
        String positive = requireArguments().getString("POSITIVE", "");
        String negative = requireArguments().getString("NEGATIVE", "");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(content);
        builder.setPositiveButton(positive, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.onPositiveListener();
                dismiss();
            }
        });
        builder.setNegativeButton(negative,null);
        return builder.create();
    }

    public void setDialogListener(DialogListener listener){
        this.listener = listener;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if(listener !=null) listener = null;
    }

    public interface DialogListener extends EventListener {
        DialogInterface.OnClickListener onPositiveListener();
        DialogInterface.OnClickListener onNegativeListener();
    }
}


