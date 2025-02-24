import java.util.Scanner;

public class ManagerMenu {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Manager manager = null;

        while (true) {
            System.out.println("1. Create Manager");
            System.out.println("2. Add Hotel");
            System.out.println("3. Add Free Dates");
            System.out.println("4. Show Hotels");
            System.out.println("5. Area Bookings");
            System.out.println("6. Add Hotels From JSON");
            System.out.println("7. Exit");
            
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter manager name: ");
                    String managerName = scanner.nextLine();
                    System.out.print("Enter manager ID: ");
                    String managerId = scanner.nextLine();
                    manager = new Manager(managerName, managerId);
                    break;
                case 2:
                    if (manager == null) {
                        System.out.println("Create a manager first!");
                        break;
                    }
                    System.out.print("Enter hotel name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter number of persons: ");
                    int noOfPersons = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter area: ");
                    String area = scanner.nextLine();
                    System.out.print("Enter stars: ");
                    float stars = scanner.nextFloat();
                    System.out.print("Enter number of reviews: ");
                    int noOfReviews = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter room image: ");
                    String roomImage = scanner.nextLine();
                    manager.addHotel(name, noOfPersons, area, stars, noOfReviews, roomImage);
                    break;
                case 3:
                    if (manager == null) {
                        System.out.println("Create a manager first!");
                        break;
                    }
                    System.out.print("Enter hotel name: ");
                    String hotelName = scanner.nextLine();
                    System.out.print("Enter start year: ");
                    int yearStart = scanner.nextInt();
                    System.out.print("Enter start month: ");
                    int monthStart = scanner.nextInt();
                    System.out.print("Enter start day: ");
                    int dateStart = scanner.nextInt();
                    System.out.print("Enter end year: ");
                    int yearEnd = scanner.nextInt();
                    System.out.print("Enter end month: ");
                    int monthEnd = scanner.nextInt();
                    System.out.print("Enter end day: ");
                    int dateEnd = scanner.nextInt();
                    manager.addFreeDates(hotelName, yearStart, monthStart, dateStart, yearEnd, monthEnd, dateEnd);
                    break;
                case 4:
                    if (manager == null) {
                        System.out.println("Create a manager first!");
                        break;
                    }
                    manager.showHotels();
                    break;
                case 5:
                    if (manager == null) {
                        System.out.println("Create a manager first!");
                        break;
                    }
                    System.out.print("Enter start year: ");
                    yearStart = scanner.nextInt();
                    System.out.print("Enter start month: ");
                    monthStart = scanner.nextInt();
                    System.out.print("Enter start day: ");
                    dateStart = scanner.nextInt();
                    System.out.print("Enter end year: ");
                    yearEnd = scanner.nextInt();
                    System.out.print("Enter end month: ");
                    monthEnd = scanner.nextInt();
                    System.out.print("Enter end day: ");
                    dateEnd = scanner.nextInt();

                    manager.areaBookings(yearStart, monthStart, dateStart, yearEnd, monthEnd, dateEnd);
                    break;
                case 6:
                    if (manager == null) {
                        System.out.println("Create a manager first!");
                        break;
                    }
                    System.out.print("Enter the file path of the JSON file: ");
                    String filePath = scanner.nextLine();
                    manager.addHotelsFromJSON(filePath);
                    String[] roomNames = {
                        "Athens Modern Stay",
                        "Patra Marina Hotel",
                        "Thessaloniki Royal Suites",
                        "Kalamata Bay Inn",
                        "Volos View Hotel",
                        "Capital Retreat Athens",
                        "Peloponnese Gateway",
                        "Thessaloniki Grand Palace",
                        "Olive Grove Resort",
                        "Volos Nautical Rooms",
                        "Athens Urban Villa",
                        "Patra Portside Hotel",
                        "Thessaloniki City Hotel",
                        "Kalamata Sunset Resort",
                        "Volos Mountain View Hotel",
                        "Acropolis View Luxury",
                        "Patra Comfort Inn",
                        "Northern Lights Hotel",
                        "Messenian Manor",
                        "Pelion Seaside Resort"
                    };
            
                    for(int i = 0; i<20; i++){
                        manager.addFreeDates(roomNames[i], 2024, 1, 1, 2024, 1, 31);
                    }
                    break;
                case 7:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}
