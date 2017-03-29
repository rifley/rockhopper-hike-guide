import org.sql2o.*;
import java.util.List;

public class Hike {
  private String hikeName;
  private String region;
  private int difficulty;
  private int distance;
  private boolean dogs;
  private boolean waterfalls;
  private int elevation;
  private int id;

  public Hike(String name, String state, int difficulty, int distance, boolean dogs, boolean waterfalls, int elevation) {
    this.hikeName = name;
    this.region = region;
    this.difficulty = difficulty;
    this.distance = distance;
    this.dogs = dogs;
    this.waterfalls = waterfalls;
    this.elevation = elevation;
    }

  public String getName() {
    return hikeName;
  }
  public int getId(){
    return this.id;
  }

@Override
public boolean equals(Object otherHike) {
  if(!(otherHike instanceof Hike)) {
    return false;
  } else {
    Hike newHike = (Hike) otherHike;
    return this.getName().equals(newHike.getName()) &&
    this.getId() == newHike.getId();
  }
}

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO hikes (hikeName, region, difficulty, distance, dogs, waterfalls, elevation) VALUES (:hikename, :region, :difficulty, :distance, :dogs, :waterfalls, :elevation)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("hikename", this.hikeName)
        .addParameter("region", this.region)
        .addParameter("difficulty", this.difficulty)
        .addParameter("distance", this.distance)
        .addParameter("dogs", this.dogs)
        .addParameter("waterfalls", this.waterfalls)
        .addParameter("elevation", this.elevation)
        .executeUpdate()
        .getKey();
    }
  }

  public static Hike find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM hikes WHERE id = :id";
      return con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Hike.class);
    }
  }

  public static List<Hike> all(){
    try(Connection con = DB.sql2o.open()){
      String sql = "SELECT * FROM hikes";
      return con.createQuery(sql).executeAndFetch(Hike.class);
  }
}// end hike
} // end Hike class
