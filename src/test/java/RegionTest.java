import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;

public class RegionTest {

  @Before
  public void setUp() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/rockhopper_test", null, null);
  }

  @After
  public void tearDown() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM location *;";
      con.createQuery(sql).executeUpdate();
    }
  }

  @Test
  public void region_instantiatesCorrectly() {
    Region region = new Region("Coast", "Oregon");
    assertTrue(region instanceof Region);

  }

  @Test
  public void getName_returnsRegionName_String() {
    Region region = new Region("Coast", "Oregon");
    assertEquals("Coast", region.getName());
  }

  @Test
  public void savesEntriesToDatabase(){
    Region newRegion = new Region("Coast", "Oregon");
    newRegion.save();
    assertTrue(Region.all().get(0).equals(newRegion));
  }



}
