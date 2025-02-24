package com.example.hotel_book.User;
import java.util.Scanner;
public class UserMenu {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        User user = null;

        while (true) {
            System.out.println("1. Create User");
            System.out.println("2. Book Hotel");
            System.out.println("3. Search Hotel");
            System.out.println("4. Review Hotel");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    System.out.print("Enter name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter user id: ");
                    String userId = scanner.nextLine();
                    user = new User(name, userId);
                    break;
                case 2:
                    if (user == null) {
                        System.out.println("Create a user first!");
                        break;
                    }
                    System.out.print("Enter hotel name: ");
                    String hotelName = scanner.nextLine();
                    int[] bookingDates = getBookingDates(scanner);
                    user.book(hotelName, bookingDates);
                    break;
                case 3:
                    if (user == null) {
                        System.out.println("Create a user first!");
                        break;
                    }
                    bookingDates = getBookingDates(scanner);
                    scanner.nextLine();
                    System.out.print("Enter location (optional, press enter to skip): ");
                    String location = scanner.nextLine().trim();
                    if (location.isEmpty()) {
                        location = null;
                    }
                    System.out.print("Enter minimum rating (optional, press enter to skip): ");
                    String minRatingInput = scanner.nextLine().trim();
                    int minRating = 0;
                    if (!minRatingInput.isEmpty()) {
                        minRating = Integer.parseInt(minRatingInput);
                    }
                    System.out.print("Enter number of persons (optional, press enter to skip): ");
                    String minPersonsInput = scanner.nextLine().trim();
                    int minPersons = 0;
                    if (!minPersonsInput.isEmpty()) {
                        minPersons = Integer.parseInt(minPersonsInput);
                    }
                    user.search(new Filter(bookingDates, location, minPersons, minRating));
                    break;

                case 4:
                    if (user == null) {
                        System.out.println("Create a user first!");
                        break;
                    }
                    System.out.print("Enter hotel name: ");
                    hotelName = scanner.nextLine();
                    System.out.print("Enter review score: ");
                    int reviewScore = scanner.nextInt();
                    user.review(hotelName, reviewScore);
                    break;
                case 5:
                    System.out.println("Exiting...");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    static int[] getBookingDates(Scanner scanner) {
        int[] bookingDates = new int[6];
        System.out.print("Enter check-in year: ");
        bookingDates[0] = scanner.nextInt();
        System.out.print("Enter check-in month: ");
        bookingDates[1] = scanner.nextInt();
        System.out.print("Enter check-in day: ");
        bookingDates[2] = scanner.nextInt();
        System.out.print("Enter check-out year: ");
        bookingDates[3] = scanner.nextInt();
        System.out.print("Enter check-out month: ");
        bookingDates[4] = scanner.nextInt();
        System.out.print("Enter check-out day: ");
        bookingDates[5] = scanner.nextInt();
        return bookingDates;
    }
}
