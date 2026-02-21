package com.todo.dto; // 自分がどのフォルダ（パッケージ）にいるかを宣言します

public class TaskDto {
    private int id;
    private String taskName;
    private int priority;
    private boolean isCompleted;

    // ゲッター（データを取り出すメソッド）
    public int getId() { return id; }
    public String getTaskName() { return taskName; }
    public int getPriority() { return priority; }
    public boolean getIsCompleted() { return isCompleted; }

    // セッター（データをセットするメソッド）
    public void setId(int id) { this.id = id; }
    public void setTaskName(String taskName) { this.taskName = taskName; }
    public void setPriority(int priority) { this.priority = priority; }
    public void setIsCompleted(boolean isCompleted) { this.isCompleted = isCompleted; }
}