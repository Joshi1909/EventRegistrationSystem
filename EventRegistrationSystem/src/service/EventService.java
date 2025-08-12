package service;

import db.DBConnection;
import java.sql.*;

public class EventService {

    public void createEvent(String name, String date, int capacity) {
        String sql = "INSERT INTO events(name, date, capacity) VALUES(?, ?, ?)";
        try (Connection conn = DBConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, date);
            pstmt.setInt(3, capacity);
            pstmt.executeUpdate();
            System.out.println("✅ Event created successfully.");
        } catch (SQLException e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    public void listEvents() {
        String sql = "SELECT * FROM events";
        try (Connection conn = DBConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("\n=== Event List ===");
            while (rs.next()) {
                System.out.printf("ID: %d | Name: %s | Date: %s | Capacity: %d | Status: %s%n",
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("date"),
                        rs.getInt("capacity"),
                        rs.getString("status"));
            }
        } catch (SQLException e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    public void registerAttendee(int eventId, String name, String email) {
        String checkEvent = "SELECT capacity, status FROM events WHERE id = ?";
        String countAttendees = "SELECT COUNT(*) AS total FROM attendees WHERE event_id = ?";
        String insertAttendee = "INSERT INTO attendees(event_id, name, email) VALUES(?, ?, ?)";

        try (Connection conn = DBConnection.connect()) {
            // Check if event exists and active
            PreparedStatement eventStmt = conn.prepareStatement(checkEvent);
            eventStmt.setInt(1, eventId);
            ResultSet eventRs = eventStmt.executeQuery();
            if (!eventRs.next()) {
                System.out.println("❌ Event not found.");
                return;
            }
            if (!"ACTIVE".equalsIgnoreCase(eventRs.getString("status"))) {
                System.out.println("❌ Event is cancelled.");
                return;
            }

            int capacity = eventRs.getInt("capacity");

            // Check capacity
            PreparedStatement countStmt = conn.prepareStatement(countAttendees);
            countStmt.setInt(1, eventId);
            ResultSet countRs = countStmt.executeQuery();
            if (countRs.getInt("total") >= capacity) {
                System.out.println("❌ Event is full.");
                return;
            }

            // Insert attendee
            PreparedStatement insertStmt = conn.prepareStatement(insertAttendee);
            insertStmt.setInt(1, eventId);
            insertStmt.setString(2, name);
            insertStmt.setString(3, email);
            insertStmt.executeUpdate();
            System.out.println("✅ Registration successful.");
        } catch (SQLException e) {
            if (e.getMessage().contains("UNIQUE")) {
                System.out.println("❌ Attendee with this email already registered.");
            } else {
                System.out.println("❌ Error: " + e.getMessage());
            }
        }
    }

    public void cancelEvent(int eventId) {
        String sql = "UPDATE events SET status = 'CANCELLED' WHERE id = ?";
        try (Connection conn = DBConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, eventId);
            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("✅ Event cancelled.");
            } else {
                System.out.println("❌ Event not found.");
            }
        } catch (SQLException e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    public void viewStats(int eventId) {
        String totalAttendees = "SELECT COUNT(*) AS total FROM attendees WHERE event_id = ?";
        String capacityQuery = "SELECT capacity FROM events WHERE id = ?";

        try (Connection conn = DBConnection.connect()) {
            PreparedStatement capStmt = conn.prepareStatement(capacityQuery);
            capStmt.setInt(1, eventId);
            ResultSet capRs = capStmt.executeQuery();
            if (!capRs.next()) {
                System.out.println("❌ Event not found.");
                return;
            }
            int capacity = capRs.getInt("capacity");

            PreparedStatement totalStmt = conn.prepareStatement(totalAttendees);
            totalStmt.setInt(1, eventId);
            ResultSet totalRs = totalStmt.executeQuery();

            int registered = totalRs.getInt("total");
            int remaining = capacity - registered;

            System.out.println("=== Event Stats ===");
            System.out.println("Registered: " + registered);
            System.out.println("Remaining: " + remaining);
        } catch (SQLException e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }
}
