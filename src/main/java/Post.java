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
  private List<Tag> tags;
  private List<Comment> comments;

  public Post(String title, int user_id) {
    this.title = title;
    this.user_id = user_id;
    created = new Timestamp(new Date().getTime());
  }

  public List<Tag> getTags(){
    return tags;
  }

  public List<Comment> getComments(){
    return comments;
  }

}
