package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {
    // Store database in current working directory so you can find it easily
    private static final String URL = "jdbc:sqlite:" + System.getProperty("user.dir") + "/database.db";

    // Connect to database
    public static Connection connect() {
        try {
            return DriverManager.getConnection(URL);
        } catch (SQLException e) {
            System.out.println("❌ Database connection failed: " + e.getMessage());
            return null;
        }
    }

    
    public static void initializeDatabase() {
        String createEventsTable = "CREATE TABLE IF NOT EXISTS events (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT NOT NULL," +
                "date TEXT NOT NULL," +
                "capacity INTEGER NOT NULL," +
                "status TEXT NOT NULL DEFAULT 'ACTIVE'" +
                ");";

        String createAttendeesTable = "CREATE TABLE IF NOT EXISTS attendees (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "event_id INTEGER NOT NULL," +
                "name TEXT NOT NULL," +
                "email TEXT NOT NULL," +
                "UNIQUE(event_id, email)," +
                "FOREIGN KEY (event_id) REFERENCES events(id)" +
                ");";

        try (Connection conn = connect()) {
            if (conn != null) {
                try (Statement stmt = conn.createStatement()) {
                    stmt.execute(createEventsTable);
                    stmt.execute(createAttendeesTable);
                    System.out.println("✅ Database initialized successfully!");
                }
            } else {
                System.out.println("❌ Failed to connect to the database. Tables not created.");
            }
        } catch (SQLException e) {
            System.out.println("❌ Error initializing database: " + e.getMessage());
        }
    }
}
