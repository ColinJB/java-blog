import org.sql2o.*;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.sql.Timestamp;

public class User {
  private int id;
  private String name;
  private String username;
  private List<Post> posts;
  private List<Comment> comments;

  public User(String name, String username) {
    this.name = name;
    this.username = username;
  }

  public int getId(){
    return id;
  }

  public String getName(){
    return name;
  }

  public String getUsername(){
    return username;
  }

  public List<Post> getPosts(){
    return posts;
  }

  public List<Comment> getComments(){
    return comments;
  }

  public void save(){
    try(Connection con = DB.sql2o.open()){
      String sql = "INSERT INTO users (name, username) VALUES (:name, :username);";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", name)
        .addParameter("username", username)
        .executeUpdate()
        .getKey();
    }
  }

  public static List<User> all(){
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM users;";
      return con.createQuery(sql)
        .executeAndFetch(User.class);
    }
  }

  public static User find(int id){
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM users WHERE id=:id;";
      return con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(User.class);
    }
  }

  public void update(String column, String value){
    try (Connection con = DB.sql2o.open()){
      String sql = String.format("UPDATE users SET %s = %s WHERE id=:id;", column, value);
      System.out.println(sql);
      con.createQuery(sql)
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }

}
