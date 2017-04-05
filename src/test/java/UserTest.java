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

public class UserTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void save_returnsTrueForNameUsername_true() {
    User testUser = new User("Colin", "Colinjb");
    testUser.save();
    String name = testUser.getName();
    String username = testUser.getUsername();
    assertEquals(User.all().get(0).getName(), "Colin");
    assertEquals(User.all().get(0).getUsername(), "Colinjb");
  }

  @Test
  public void find_returnsCorrectUser_true() {
    User testUser = new User("Colin", "Colinjb");
    testUser.save();
    User testUser2 = new User("Alex", "Doomcat");
    testUser2.save();
    assertEquals(User.find(testUser.getId()).getName(), "Colin");
    assertEquals(User.find(testUser2.getId()).getName(), "Alex");
  }

  @Test
  public void update_returnsUpdatedUser_true() {
    User testUser = new User("Colin", "Colinjb");
    testUser.save();
    testUser.update("name", "'Alex'");
    assertEquals(User.all().get(0).getName(), "Alex");
  }

  @Test
  public void delete_deletesUser_true() {
    User testUser = new User("Colin", "Colinjb");
    testUser.save();
    testUser.delete();
    assertEquals(User.all().size(), 0);
  }

  @Test
  public void equals_returnsTrueIfNameAndUsernameAreSame_true() {
    User testUser = new User("Colin", "Colinjb");
    testUser.save();
    User savedUser = User.all().get(0);
    assertTrue(savedUser.equals(testUser));
  }

}
