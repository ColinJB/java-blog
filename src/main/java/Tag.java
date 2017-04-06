import org.sql2o.*;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.sql.Timestamp;

public class Tag {
  private int id;
  private String name;

  public Tag(String name) {
    this.name = name;
  }

  public String getName(){
    return name;
  }

  public int getId(){
    return id;
  }

  public void save(){
    try(Connection con = DB.sql2o.open()){
      String sql = "INSERT INTO tags (name) VALUES (:name);";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", name)
        .executeUpdate()
        .getKey();
    }
  }

  public static List<Tag> all(){
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM tags;";
      return con.createQuery(sql)
        .executeAndFetch(Tag.class);
    }
  }

  public static Tag find(int id){
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM tags WHERE id=:id;";
      return con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Tag.class);
    }
  }

  public void update(String column, String value){
    try (Connection con = DB.sql2o.open()){
      String sql = String.format("UPDATE tags SET %s = %s WHERE id=:id;", column, value);
      con.createQuery(sql)
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }

  public void delete(){
    try (Connection con = DB.sql2o.open()){
      String sql = "DELETE FROM tags WHERE id=:id;";
      con.createQuery(sql)
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }

  @Override
  public boolean equals(Object otherObject){
    if (!(otherObject instanceof Tag)) {
      return false;
    } else {
      Tag newTag = (Tag) otherObject;
      return this.getName().equals(newTag.getName());
    }
  }

  public List<Post> getPosts(){
    try(Connection con = DB.sql2o.open()){
      String joinQuery = "SELECT post_id FROM tags_posts WHERE tag_id = :tag_id";
      List<Integer> postIds = con.createQuery(joinQuery)
        .addParameter("tag_id", this.getId())
        .executeAndFetch(Integer.class);

        List<Post> posts = new ArrayList<Post>();

      for (Integer postId : postIds) {
        String postQuery = "SELECT * FROM posts WHERE id = :id";
        Post post = con.createQuery(postQuery)
          .addParameter("id", postId)
          .executeAndFetchFirst(Post.class);
        posts.add(post);
      }
      return posts;
    }
  }



}
