import org.sql2o.*;
import java.util.List;
import java.time.*;
import java.sql.Date;

public class Comment {
  private String statement;
  private int rating;
  private int userId;
  private int hikeId;
  private int id;
  private LocalDate date;

  public Comment(String statement, int rating, int userId, int hikeId) {
    this.statement = statement;
    this.rating = rating;
    this.userId = userId;
    this.date = LocalDate.now();
    this.hikeId = hikeId;
  }

  public String getStatement() {
    return statement;
  }
  public int getUserId(){
    return userId;
  }

  public int getHikeId() {
    return hikeId;
  }

public int getRating(){
  return rating;
}

public LocalDate getDate(){
  return date;
}

public void save(){
try (Connection con = DB.sql2o.open()) {
  String sql = "INSERT INTO comments (statement, rating, userid, hikeid, commentdate) VALUES (:statement, :userid, :hikeid, :date, :rating)";
  this.id = (int) con.createQuery(sql,true)
  .addParameter("statement", this.statement)
  .addParameter("rating", this.rating)
  .addParameter("userid", this.userId)
  .addParameter("hikeid", this.hikeId)
  .addParameter("date", this.date)
  .executeUpdate()
  .getKey();
  }
}

public static Comment find(int id) {
  try(Connection con = DB.sql2o.open()) {
    String sql = "SELECT * FROM comments WHERE id = :id";
    return con.createQuery(sql)
      .addParameter("id", id)
      .executeAndFetchFirst(Comment.class);
  }
}

public static List<Comment> all(){
  try(Connection con = DB.sql2o.open()){
    String sql = "SELECT * FROM Comments";
    return con.createQuery(sql).executeAndFetch(Comment.class);
    }
  }
}
