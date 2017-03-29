import org.sql2o.*;
import java.util.List;

public class Region {
  private String region;
  private String state;
  private int id;

  public Region(String name, String state) {
    this.region = name;
    this.state = state;
  }

  public String getName() {
    return region;
  }
  public int getId(){
    return this.id;
  }

  public String getState() {
    return state;
  }

@Override
public boolean equals(Object otherRegion) {
  if(!(otherRegion instanceof Region)) {
    return false;
  } else {
    Region newRegion = (Region) otherRegion;
    return this.getName().equals(newRegion.getName()) &&
    this.getId() == newRegion.getId();
  }
}

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO region (state, region) VALUES (:state, :region)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("state", this.state)
        .addParameter("region", this.region)
        .executeUpdate()
        .getKey();
    }
  }

  public static Region find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM region WHERE id = :id";
      return con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Region.class);
    }
  }

  public static List<Region> all(){
    try(Connection con = DB.sql2o.open()){
      String sql = "SELECT * FROM region";
      return con.createQuery(sql).executeAndFetch(Region.class);
  }
}// end region
} // end Region class
