import org.sql2o.*;
import java.util.List;

public class User {
  private String userName;
  private String email;
  private int level;
  private int id;

  public User(String name, String email, int level) {
    this.userName = name;
    this.email = email;
    this.level = level;
  }

  public String getName() {
    return userName;
  }
  public int getId(){
    return this.id;
  }

@Override
public boolean equals(Object otherUser) {
  if(!(otherUser instanceof User)) {
    return false;
  } else {
    User newUser = (User) otherUser;
    return this.getName().equals(newUser.getName()) &&
    this.getId() == newUser.getId();
  }
}

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO users (username, email, level) VALUES (:username, :email, :level)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("username", this.userName)
        .addParameter("email", this.email)
        .addParameter("level", this.level)
        .executeUpdate()
        .getKey();
    }
  }

  public static User find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM users WHERE id = :id";
      return con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(User.class);
    }
  }

  public static List<User> all(){
    try(Connection con = DB.sql2o.open()){
      String sql = "SELECT * FROM users";
      return con.createQuery(sql).executeAndFetch(User.class);
  }
}// end user
} // end User class
