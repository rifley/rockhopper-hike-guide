import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;

public class HikeTest {

  @Before
  public void setUp() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/rockhopper_test", null, null);
  }

  @After
  public void tearDown() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM hikes *;";
      con.createQuery(sql).executeUpdate();
    }
  }

  @Test
  public void hike_instantiatesCorrectly() {
    Hike hike = new Hike("Coast", "Timberline", 4, 10, true, false, 500);
    assertTrue(hike instanceof Hike);

  }

  @Test
  public void getName_returnsHikeName_String() {
    Hike hike = new Hike("Coast", "Timberline", 4, 10, true, false, 500);
    assertEquals("Coast", hike.getName());
  }

  @Test
  public void savesEntriesToDatabase(){
    Hike newHike = new Hike("Coast", "Timberline", 4, 10, true, false, 500);
    newHike.save();
    assertTrue(Hike.all().get(0).equals(newHike));
  }

  @Test
  public void all_returnsAllFromDatabase(){
    Hike hike1 = new Hike ("trail1", "Timberline", 4, 10, true, false, 500);
    hike1.save();
    Hike hike2 = new Hike ("trail2", "Timberline", 4, 10, true, false, 500);
    hike2.save();
    assertTrue(Hike.all().get(0).equals(hike1));
    assertTrue(Hike.all().get(1).equals(hike2));
    }

}
