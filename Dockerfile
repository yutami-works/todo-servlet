FROM tomcat:9.0-jdk17-corretto

# ローカルのwebappフォルダをTomcatの公開用ディレクトリにコピー
COPY ./webapp /usr/local/tomcat/webapps/ROOT

# PostgreSQLのドライバをダウンロード
RUN curl -o /usr/local/tomcat/webapps/ROOT/WEB-INF/lib/postgresql-42.7.2.jar https://jdbc.postgresql.org/download/postgresql-42.7.2.jar

# コンテナ内でJavaファイルを一括コンパイル
RUN javac -encoding UTF-8 -cp "/usr/local/tomcat/lib/servlet-api.jar:/usr/local/tomcat/webapps/ROOT/WEB-INF/lib/postgresql-42.7.2.jar" -d /usr/local/tomcat/webapps/ROOT/WEB-INF/classes /usr/local/tomcat/webapps/ROOT/WEB-INF/src/com/todo/dto/*.java /usr/local/tomcat/webapps/ROOT/WEB-INF/src/*.java

# Tomcatが使うポートを指定
EXPOSE 8080

# Tomcatを起動
CMD ["catalina.sh", "run"]