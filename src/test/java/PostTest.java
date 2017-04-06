import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.sql.Timestamp;

public class PostTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void save_returnsTrueForTitlePostname_true() {
    Post testPost = new Post("Colin", 1);
    testPost.save();
    String name = testPost.getTitle();
    assertEquals(Post.all().get(0).getTitle(), "Colin");
  }

  @Test
  public void find_returnsCorrectPost_true() {
    Post testPost = new Post("Colin",1);
    testPost.save();
    Post testPost2 = new Post("Alex",2);
    testPost2.save();
    assertEquals(Post.find(testPost.getId()).getTitle(), "Colin");
    assertEquals(Post.find(testPost2.getId()).getTitle(), "Alex");
  }

  @Test
  public void update_returnsUpdatedPost_true() {
    Post testPost = new Post("Colin",1);
    testPost.save();
    testPost.update("title", "'Alex'");
    assertEquals(Post.all().get(0).getTitle(), "Alex");
  }

  @Test
  public void delete_deletesPost_true() {
    Post testPost = new Post("Colin",1);
    testPost.save();
    testPost.delete();
    assertEquals(Post.all().size(), 0);
  }

  @Test
  public void equals_returnsTrueIfTitleAndPostnameAreSame_true() {
    Post testPost = new Post("Colin",1);
    testPost.save();
    Post savedPost = Post.all().get(0);
    assertTrue(savedPost.equals(testPost));
  }

  @Test
  public void test_awesome() {
    Post testPost = new Post("Colin",1);
    testPost.save();
    Post testPost2 = new Post("Colin2",1);
    testPost2.save();
    Post savedPost = Post.all().get(0);
    Tag tag = new Tag("shit");
    Tag tag2 = new Tag("crap");
    tag.save();
    tag2.save();
    testPost.addTag(tag);
    testPost.addTag(tag2);
    testPost2.addTag(tag);
    assertTrue(testPost.getTags().size() == 2);
    assertTrue(testPost2.getTags().size() == 1);
  }

  @Test
  public void findByTag_returnsPostsContainingTag_true() {
    Post testPost = new Post("Colin",1);
    testPost.save();
    Post testPost2 = new Post("Colin3",4);
    testPost2.save();
    Post testPost3 = new Post("Colin3",2);
    testPost3.save();
    Tag tag = new Tag("crap");
    Tag tag2 = new Tag("shit");
    tag.save();
    tag2.save();
    testPost3.addTag(tag);
    testPost2.addTag(tag);
    testPost.addTag(tag2);
  }

  @Test
  public void getUserPosts_returnsAllPostsOfUser_true() {
    User testUser = new User("Alex", "Doomcat");
    testUser.save();
    Post testPost = new Post("Colin", testUser.getId());
    testPost.save();
    Post testPost2 = new Post("Colin2", testUser.getId());
    testPost2.save();
    List<Post> savedPosts = Post.all();
    assertEquals(savedPosts, Post.getUserPosts(testUser));
  }


}
