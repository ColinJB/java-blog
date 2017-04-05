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
  private List<Post> postList;
  private List<Comment> commentList;

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

  public List<Post> getPostList(){
    return post;
  }

  public List<Comment> getCommentList(){
    return comments;
  }

}
