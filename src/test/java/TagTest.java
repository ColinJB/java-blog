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

public class TagTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void save_returnsTrueForNameTagname_true() {
    Tag testTag = new Tag("Colin");
    testTag.save();
    String name = testTag.getName();
    assertEquals(Tag.all().get(0).getName(), "Colin");
  }

  @Test
  public void find_returnsCorrectTag_true() {
    Tag testTag = new Tag("Colin");
    testTag.save();
    Tag testTag2 = new Tag("Alex");
    testTag2.save();
    assertEquals(Tag.find(testTag.getId()).getName(), "Colin");
    assertEquals(Tag.find(testTag2.getId()).getName(), "Alex");
  }

  @Test
  public void update_returnsUpdatedTag_true() {
    Tag testTag = new Tag("Colin");
    testTag.save();
    testTag.update("name", "'Alex'");
    assertEquals(Tag.all().get(0).getName(), "Alex");
  }

  @Test
  public void delete_deletesTag_true() {
    Tag testTag = new Tag("Colin");
    testTag.save();
    testTag.delete();
    assertEquals(Tag.all().size(), 0);
  }

  @Test
  public void equals_returnsTrueIfNameAndTagnameAreSame_true() {
    Tag testTag = new Tag("Colin");
    testTag.save();
    Tag savedTag = Tag.all().get(0);
    assertTrue(savedTag.equals(testTag));
  }

  @Test
  public void getPosts_() {
    Post testPost = new Post("Colin",1);
    testPost.save();
    Post testPost2 = new Post("Colin2",1);
    testPost2.save();
    Tag tag = new Tag("shit");
    Tag tag2 = new Tag("crap");
    tag.save();
    tag2.save();
    testPost.addTag(tag);
    testPost.addTag(tag2);
    testPost2.addTag(tag);
    assertTrue(tag.getPosts().size() == 2);
    assertTrue(tag2.getPosts().size() == 1);
  }

}
