CREATE TABLE tasks (
    id SERIAL PRIMARY KEY,
    task_name VARCHAR(255) NOT NULL,
    priority INT NOT NULL, -- 1:高, 2:中, 3:低
    is_completed BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- テストデータの挿入
INSERT INTO tasks (task_name, priority, is_completed) VALUES ('Servletの書き方を調べる', 1, FALSE);
INSERT INTO tasks (task_name, priority, is_completed) VALUES ('画面のCSSを書く', 2, FALSE);