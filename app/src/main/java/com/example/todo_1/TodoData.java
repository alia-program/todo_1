package com.example.todo_1;

public class TodoData {
    private String editText;
    private Boolean checkBox;

    public String getText() {
        return editText;
    }

    public Boolean getboolean() {
        return checkBox;
    }

    public void setText(String editText) {
        this.editText = editText;
    }

    public void setBool(Boolean checkBox) {
        this.checkBox = checkBox;
    }

    public TodoData(String editText, Boolean checkBox) {
        this.editText = editText;
        this.checkBox = checkBox;
    }
}
