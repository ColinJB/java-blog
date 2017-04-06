import org.sql2o.*;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.sql.Timestamp;
import java.text.DateFormat;


public class Post extends Submission{

  public Post(String title, int user_id) {
    this.title = title;
    this.user_id = user_id;
    created = new Timestamp(new Date().getTime());
  }

  public List<Tag> getTags(){
    try(Connection con = DB.sql2o.open()){
      String joinQuery = "SELECT tag_id FROM tags_posts WHERE post_id = :post_id";
      List<Integer> tagIds = con.createQuery(joinQuery)
        .addParameter("post_id", this.getId())
        .executeAndFetch(Integer.class);

        List<Tag> tags = new ArrayList<Tag>();

      for (Integer tagId : tagIds) {
        String tagQuery = "SELECT * FROM tags WHERE id = :id";
        Tag tag = con.createQuery(tagQuery)
          .addParameter("id", tagId)
          .executeAndFetchFirst(Tag.class);
        tags.add(tag);
      }
      return tags;
    }
  }

  public void save(){
    try(Connection con = DB.sql2o.open()){
      String sql = "INSERT INTO posts (title, content, user_id, created) VALUES (:title, :content, :user_id, :created);";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("title", title)
        .addParameter("content", this.content)
        .addParameter("user_id", user_id)
        .addParameter("created", created)
        .executeUpdate()
        .getKey();
    }
  }

  public static List<Post> all(){
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM posts;";
      return con.createQuery(sql)
        .executeAndFetch(Post.class);
    }
  }

  public static List<Post> getUserPosts(User user){
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM posts WHERE user_id = :user_id;";
      return con.createQuery(sql)
        .addParameter("user_id", user.getId())
        .executeAndFetch(Post.class);
    }
  }

  public static Post find(int id){
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM posts WHERE id=:id;";
      return con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Post.class);
    }
  }

  public void update(String column, String value){
    try (Connection con = DB.sql2o.open()){
      String sql = String.format("UPDATE posts SET %s = %s WHERE id=:id;", column, value);
      con.createQuery(sql)
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }

  public void delete(){
    try (Connection con = DB.sql2o.open()){
      String sql = "DELETE FROM posts WHERE id=:id;";
      con.createQuery(sql)
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }

  public static List<Post> findByTag(Tag tag){
    System.out.println(tag.getId());
    try (Connection con = DB.sql2o.open()){
      String sql = "SELECT posts.*  FROM tags JOIN tags_posts ON (tags.id = tags_posts.tag_id) JOIN posts ON (tags_posts.post_id = posts.id) WHERE tags.id =:id;";
      return con.createQuery(sql)
        .addParameter("id", tag.getId())
        .executeAndFetch(Post.class);
    }
  }

  public void addTag(Tag tag){
    try (Connection con = DB.sql2o.open()){
      String sql = "INSERT INTO tags_posts (tag_id, post_id) VALUES (:tag_id, :post_id);";
      con.createQuery(sql)
        .addParameter("tag_id", tag.getId())
        .addParameter("post_id", this.id)
        .executeUpdate();
    }
  }

  @Override
  public boolean equals(Object otherObject){
    if (!(otherObject instanceof Post)) {
      return false;
    } else {
      Post newPost = (Post) otherObject;
      return this.getTitle().equals(newPost.getTitle()) &&
             this.getUserId() == (newPost.getUserId());
    }
  }

}
