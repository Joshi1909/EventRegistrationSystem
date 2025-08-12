# Event Registration System

A **Java-based console application** to manage events, register attendees, and track event statistics.  
The system uses a database connection to store and retrieve event details.

---

## 📌 Features

- **Create Event** – Add a new event with name, date, and capacity.
- **List Events** – Display all events stored in the database.
- **Register Attendee** – Add participants to an event.
- **Cancel Event** – Remove an event from the system.
- **View Event Stats** – See attendee count and available seats for an event.
- **Database Integration** – Persistent storage using a database connection.

---

## 📂 Project Structure

EventRegistrationSystem/
│
├── src/
│ ├── Main.java # Application entry point
│ ├── db/
│ │ └── DBConnection.java # Handles database initialization and connection
│ ├── service/
│ │ └── EventService.java # Business logic for events and attendees
│ └── model/ # (Optional) Model classes for Event and Attendee
│
├── README.md # Project documentation

**Usage Example**
=== Event Registration System ===
1. Create Event
2. List Events
3. Register Attendee
4. Cancel Event
5. View Event Stats
0. Exit
Enter choice: 1
Event Name: Tech Conference
Date (YYYY-MM-DD): 2025-09-13
Capacity: 100
