import org.junit.rules.ExternalResource;
import org.sql2o.*;

public class DatabaseRule extends ExternalResource {

  @Override
  protected void before() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/java_blog_test", null, null);
  }

  @Override
  protected void after() {
    try(Connection con = DB.sql2o.open()) {
      String deleteCommentQuery = "DELETE FROM comments *;";
      con.createQuery(deleteCommentQuery).executeUpdate();
      String deletePostQuery = "DELETE FROM posts *;";
      con.createQuery(deletePostQuery).executeUpdate();
      String deleteTagQuery = "DELETE FROM tags *;";
      con.createQuery(deleteTagQuery).executeUpdate();
      String deleteUserQuery = "DELETE FROM users *;";
      con.createQuery(deleteUserQuery).executeUpdate();
      String deleteTagPostQuery = "DELETE FROM tags_posts *;";
      con.createQuery(deleteTagPostQuery).executeUpdate();
    }
  }
}
