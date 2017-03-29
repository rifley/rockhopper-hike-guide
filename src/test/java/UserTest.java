import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;

public class UserTest {

  @Before
  public void setUp() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/rockhopper_test", null, null);
  }

  @After
  public void tearDown() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM users *;";
      con.createQuery(sql).executeUpdate();
    }
  }

  @Test
  public void user_instantiatesCorrectly() {
    User user = new User("Joe", "joe.knows@gmail.com", 2);
    assertTrue(user instanceof User);

  }

  @Test
  public void getName_returnsUserName_String() {
    User user = new User("Joe", "joe.knows@gmail.com", 2);
    assertEquals("Joe", user.getName());
  }

  @Test
  public void savesEntriesToDatabase(){
    User newUser = new User("Joe", "joe.knows@gmail.com", 2);
    newUser.save();
    assertTrue(User.all().get(0).equals(newUser));
  }

  @Test
  public void all_returnsAllFromDatabase(){
    User user1 = new User ("Joe", "joe.knows@gmail.com", 2);
    user1.save();
    User user2 = new User ("Jonie", "somelady@gmail.com", 3);
    user2.save();
    assertTrue(User.all().get(0).equals(user1));
    assertTrue(User.all().get(1).equals(user2));
    }

}
