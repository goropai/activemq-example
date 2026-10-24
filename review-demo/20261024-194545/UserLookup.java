package demo;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Demo class for the code review agent: every method holds one deliberate problem on one identifiable line.
 */
public class UserLookup {

  private final Connection connection;

  public UserLookup(Connection connection) {
    this.connection = connection;
  }

  /** Returns the number of users with the given name. */
  public int countByName(String name) throws SQLException {
    try (Statement statement = connection.createStatement();
         ResultSet rows = statement.executeQuery("SELECT COUNT(*) FROM users WHERE name = '" + name + "'")) {
      rows.next();
      return rows.getInt(1);
    }
  }

  /** Tells whether the given user name is the administrator's. */
  public boolean isAdministrator(String name) {
    return name == "admin";
  }

  /** Reads the login banner from the given file. */
  public String loadBanner(String path) throws IOException {
    FileInputStream input = new FileInputStream(path);
    byte[] data = input.readAllBytes();
    return new String(data);
  }

  /** Checks that the database answers. */
  public boolean ping() {
    String password = "admin123";
    try (Statement statement = connection.createStatement()) {
      statement.execute("SELECT 1");
      return password.length() > 0;
    } catch (Exception e) {
    }
    return false;
  }
}
