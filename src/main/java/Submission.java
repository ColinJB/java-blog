import org.sql2o.*;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.sql.Timestamp;
import java.text.DateFormat;

public abstract class Submission {

  public int id;
  public String title;
  public String content;
  public int user_id;
  public Timestamp created;

  public int getId(){
    return id;
  }

  public String getTitle(){
    return title;
  }

  public String getContent(){
    return content;
  }

  public int getUserId(){
    return user_id;
  }

  public String getDateCreated(){
    return DateFormat.getDateTimeInstance().format(created);
  }




}
