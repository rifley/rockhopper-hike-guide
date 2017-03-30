import org.sql2o.*;
import java.util.List;

public class State {
  private String name;
  private String initials;
  private int id;

  public String getName() {
    return name;
  }

  public int getId() {
    return this.id;
  }

  public String getInitials() {
    return initials;
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO states (name, initials) VALUES (:name, :initials)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .addParameter("initials", this.initials)
        .executeUpdate()
        .getKey();
    }
  }

  public static State find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM states WHERE id = :id";
      return con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(State.class);
    }
  }

  public static List<State> all() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM states";
      return con.createQuery(sql).executeAndFetch(State.class);
    }
  }

  public List<Region> getRegions() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM region WHERE state = :state";
      return con.createQuery(sql)
        .addParameter("state", this.name)
        .executeAndFetch(Region.class);
    }
  }

}
