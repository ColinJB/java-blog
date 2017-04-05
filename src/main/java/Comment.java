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

  public Comment(String title, int user_id) {
    this.title = title;
    this.user_id = user_id;
    created = new Timestamp(new Date().getTime());
  }

  public int getPostId(){
    return post_id;
  }



}
