# todo-servlet
生のservlet入門用TODOアプリ

フレームワークなしのJavaのWebアプリケーションとDockerでの開発を学習する目的で作成した。

## 🛠 技術スタック
* **Backend:** Java 17, Servlet API, JDBC
* **Frontend:** JSP, HTML, CSS
* **Database:** PostgreSQL 15
* **Infrastructure:** Docker, Docker Compose
* **Hosting:** Render (Docker deploy)
* **Architecture:** DTO (Data Transfer Object) パターンを採用

## ✨ 機能
* ID/パスワードによるシンプルなログイン認証
* ToDoの一覧表示（優先度順）
* ToDoの新規登録
* ToDoの完了処理（取り消し線・グレーアウト）

---

## 🚀 ローカル環境の構築手順

### 1. リポジトリのクローン
```bash
git clone <リポジトリURL>
cd todo-servlet
```

### 2. 環境変数ファイル（.env）の作成
プロジェクトの直下に `.env` ファイルを作成し、以下の内容を記述。

```properties
# ログイン画面用のIDとパスワード
APP_LOGIN_ID=admin
APP_LOGIN_PASS=secret123
```

### 3. Dockerコンテナの起動
```bash
docker-compose up -d
```
起動後、ブラウザで `http://localhost:8080/login.jsp` にアクセスしてログイン画面が表示されれば成功。
※初回起動時、PostgreSQLには `init.sql` によってテストデータが自動的に投入される。

---

## 👨‍💻 開発・コンパイル手順

このプロジェクトでは、ローカルPCにJava環境をインストールしていなくても開発できるように、**Tomcatコンテナの中で直接コンパイル**を行う。
Javaファイルを編集した後は、以下の手順でコンパイルと再起動を行う。

### 1. コンテナ内に入って一括コンパイル
パス変換が上手くいかない場合があるため、コンテナ内に入って `javac` を実行する。

```bash
# コンテナの中に入る
docker-compose exec tomcat bash

# Javaファイルを一括コンパイル（外部ライブラリのパスも指定）
javac -encoding UTF-8 -cp "/usr/local/tomcat/lib/servlet-api.jar:/usr/local/tomcat/webapps/ROOT/WEB-INF/lib/postgresql-42.7.2.jar" -d /usr/local/tomcat/webapps/ROOT/WEB-INF/classes /usr/local/tomcat/webapps/ROOT/WEB-INF/src/com/todo/dto/*.java /usr/local/tomcat/webapps/ROOT/WEB-INF/src/*.java

# コンテナから出る
exit
```

### 2. Tomcatの再起動（変更の反映）
コンパイルが終わったら、以下のコマンドでTomcatを再起動して新しい `.class` ファイルを読み込ませる。
```bash
docker-compose restart tomcat
```
※JSPファイルやCSSファイルの変更は、コンパイルや再起動なしでブラウザを更新するだけで即座に反映される（CSSが反映されない場合は、URLの末尾に `?v=2` などのキャッシュバスティングを付与する）。

---

## ☁️ 本番環境 (Render) へのデプロイ
このアプリケーションは Render の Docker デプロイに対応している。
`Dockerfile` が用意されているため、リポジトリを連携し、以下の環境変数を設定するだけで自動的にデプロイ・起動される。

* `APP_LOGIN_ID` : アプリのログインID
* `APP_LOGIN_PASS` : アプリのパスワード
* `DB_URL` : Renderで作成したPostgreSQLのInternal URL (例: `jdbc:postgresql://dpg-xxxx:5432/todo_db`)
* `DB_USER` : DBユーザー名
* `DB_PASS` : DBパスワード