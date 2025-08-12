import db.DBConnection;
import service.EventService;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        DBConnection.initializeDatabase();
        EventService service = new EventService();
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n=== Event Registration System ===");
            System.out.println("1. Create Event");
            System.out.println("2. List Events");
            System.out.println("3. Register Attendee");
            System.out.println("4. Cancel Event");
            System.out.println("5. View Event Stats");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Event Name: ");
                    String name = sc.nextLine();
                    System.out.print("Date (YYYY-MM-DD): ");
                    String date = sc.nextLine();
                    System.out.print("Capacity: ");
                    int capacity = sc.nextInt();
                    sc.nextLine();
                    service.createEvent(name, date, capacity);
                    break;
                case 2:
                    service.listEvents();
                    break;
                case 3:
                    System.out.print("Event ID: ");
                    int eId = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Attendee Name: ");
                    String aName = sc.nextLine();
                    System.out.print("Email: ");
                    String email = sc.nextLine();
                    service.registerAttendee(eId, aName, email);
                    break;
                case 4:
                    System.out.print("Event ID: ");
                    int cId = sc.nextInt();
                    sc.nextLine();
                    service.cancelEvent(cId);
                    break;
                case 5:
                    System.out.print("Event ID: ");
                    int sId = sc.nextInt();
                    sc.nextLine();
                    service.viewStats(sId);
                    break;
                case 0:
                    System.out.println("Exiting...");
                    break;
            }
        } while (choice != 0);

        sc.close();
    }
}
