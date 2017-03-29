import org.sql2o.*;
import java.util.List;

public class Region {
  private String regionName;
  private String state;
  private int id;

  public Region(String name, String state) {
    this.regionName = name;
    this.state = state;
  }

  public String getName() {
    return regionName;
  }
  public int getId(){
    return this.id;
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
      String sql = "INSERT INTO location (state, region) VALUES (:state, :region)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("state", this.state)
        .addParameter("region", this.regionName)
        .executeUpdate()
        .getKey();
    }
  }

  public static Region find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM location WHERE id = :id";
      return con.createQuery(sql)
        .addParameter("id", this.id)
        .executeAndFetchFirst(Region.class);
    }
  }

  public static List<Region> all(){
    try(Connection con = DB.sql2o.open()){
      String sql = "SELECT * FROM loction";
      return con.createQuery(sql).executeAndFetch(Region.class);
  }
}// end region
} // end Region class
