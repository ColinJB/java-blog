import org.sql2o.*;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.sql.Timestamp;
import java.text.DateFormat;

public class Comment extends Submission{
  private int post_id;

  public Comment(String title, int user_id, int post_id) {
    this.title = title;
    this.user_id = user_id;
    this.post_id = post_id;
    created = new Timestamp(new Date().getTime());
  }

  public int getPostId(){
    return post_id;
  }

  public void saveComment(){
    try(Connection con = DB.sql2o.open()){
      String sql = "INSERT INTO comments (title, content, user_id, created, post_id) VALUES (:title, :content, :user_id, :created, :post_id);";
      // Post post = Post.find(post_id);
      // int count = Comment.getPostComments(post).size();
      // post.update("count", count);

      this.id = (int) con.createQuery(sql, true)
        .addParameter("title", title)
        .addParameter("content", this.content)
        .addParameter("user_id", user_id)
        .addParameter("created", created)
        .addParameter("post_id", post_id)
        .executeUpdate()
        .getKey();
    }
  }

  public void updatePostCount(){
    try(Connection con = DB.sql2o.open()){
      String sql = "UPDATE posts SET count = :count WHERE id = :post_id;";

      Post post = Post.find(post_id);
      int count = Comment.getPostComments(post).size();

      System.out.println(post_id);
      System.out.println(count);
      System.out.println(post);

      con.createQuery(sql)
      .addParameter("count", count)
      .addParameter("post_id", post_id)
      .executeUpdate();
    }
  }

  public void save() {
    this.saveComment();
    this.updatePostCount();
  }

  public static List<Comment> all(){
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM comments;";
      return con.createQuery(sql)
        .executeAndFetch(Comment.class);
    }
  }

  public static Comment find(int id){
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM comments WHERE id=:id;";
      return con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Comment.class);
    }
  }

  public void update(String column, String value){
    try (Connection con = DB.sql2o.open()){
      String sql = String.format("UPDATE comments SET %s = %s WHERE id=:id;", column, value);
      con.createQuery(sql)
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }

  public void delete(){
    try (Connection con = DB.sql2o.open()){
      String sql = "DELETE FROM comments WHERE id=:id;";
      con.createQuery(sql)
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }

  @Override
  public boolean equals(Object otherObject){
    if (!(otherObject instanceof Comment)) {
      return false;
    } else {
      Comment newPost = (Comment) otherObject;
      return this.getTitle().equals(newPost.getTitle()) &&
             this.getUserId() == (newPost.getUserId());
    }
  }

  public static List<Comment> getUserComments(User user){
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM comments WHERE user_id = :user_id;";
      return con.createQuery(sql)
        .addParameter("user_id", user.getId())
        .executeAndFetch(Comment.class);
    }
  }

  public static List<Comment> getPostComments(Post post){
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM comments WHERE post_id = :post_id;";
      return con.createQuery(sql)
        .addParameter("post_id", post.getId())
        .executeAndFetch(Comment.class);
    }
  }




}
