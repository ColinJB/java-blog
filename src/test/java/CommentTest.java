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

public class CommentTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

    @Test
    public void save_returnsTrueForTitleCommentname_true() {
      Comment testComment = new Comment("Colin", 1 ,1);
      testComment.save();
      String name = testComment.getTitle();
      assertEquals(Comment.all().get(0).getTitle(), "Colin");
    }

    @Test
    public void find_returnsCorrectComment_true() {
      Comment testComment = new Comment("Colin", 1, 1);
      testComment.save();
      Comment testComment2 = new Comment("Alex", 2, 1);
      testComment2.save();
      assertEquals(Comment.find(testComment.getId()).getTitle(), "Colin");
      assertEquals(Comment.find(testComment2.getId()).getTitle(), "Alex");
    }

    @Test
    public void update_returnsUpdatedComment_true() {
      Comment testComment = new Comment("Colin", 1, 1);
      testComment.save();
      testComment.update("title", "'Alex'");
      assertEquals(Comment.all().get(0).getTitle(), "Alex");
    }

    @Test
    public void delete_deletesComment_true() {
      Comment testComment = new Comment("Colin",1, 1);
      testComment.save();
      testComment.delete();
      assertEquals(Comment.all().size(), 0);
    }

    @Test
    public void equals_returnsTrueIfTitleAndCommentnameAreSame_true() {
      Comment testComment = new Comment("Colin",1, 1);
      testComment.save();
      Comment savedComment = Comment.all().get(0);
      assertTrue(savedComment.equals(testComment));
    }

    @Test
    public void getUserComments_returnsAllCommentsOfUser_true() {
      User testUser = new User("Alex", "Doomcat");
      testUser.save();
      Comment testComment = new Comment("Colin", testUser.getId(), 1);
      testComment.save();
      Comment testComment2 = new Comment("Colin2", testUser.getId(), 1);
      testComment2.save();
      List<Comment> savedComments = Comment.all();
      assertEquals(savedComments, Comment.getUserComments(testUser));
    }

    @Test
    public void getPostComments_returnsAllCommentsOfPost_true() {
      Post testPost = new Post("Alex", 1);
      testPost.save();
      Comment testComment = new Comment("Colin", 1, testPost.getId());
      testComment.save();
      Comment testComment2 = new Comment("Colin2", 1, testPost.getId());
      testComment2.save();
      List<Comment> savedComments = Comment.all();
      assertEquals(savedComments, Comment.getPostComments(testPost));
    }




}
