import java.util.Scanner;
public class MovieTicketBookingApp {
//here enum is used because it has fixed values like seats are available or booked 
enum SeatStatus {
    AVAILABLE, BOOKED
}
    public static void main(String[] args) {
        // here we are  taking input from user
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter no.of screens: ");
        String Screens = sc.nextLine();
        System.out.print("Enter Screen Name: ");
        String screenName = sc.nextLine();
        System.out.print("Enter Movie Name: ");
        String movieName = sc.nextLine();
        System.out.print("Enter Movie Timing: ");
        String timing = sc.nextLine();
        //asking no.of rows
        System.out.print("Enter Number of Rows: ");
        int rows = sc.nextInt();
        System.out.print("Enter Seats per Row: ");
        int seatsPerRow = sc.nextInt();
        // here 2Darray i had used because for seatinng arragngement as per row insted od 1d array
        SeatStatus[][] seats = new SeatStatus[rows][seatsPerRow];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < seatsPerRow; j++) {
                seats[i][j] = SeatStatus.AVAILABLE;
            }
        }
        System.out.println("\nSeat Layout (A = Available, B = Booked)");
        for (int i = 0; i < rows; i++) {
            System.out.print("Row " + (i + 1) + ": ");
            for (int j = 0; j < seatsPerRow; j++) {
                System.out.print(seats[i][j] == SeatStatus.AVAILABLE ? "A " : "B ");
            }
            System.out.println();
        }
        // here we are asking for coustumer to book seats how many they want
        System.out.print("\nHow many seats do you want to book? ");
        int count = sc.nextInt();
        String bookedSeats = "";
        for (int i = 0; i < count; i++) {
            System.out.print("Enter Row Number: ");
            int r = sc.nextInt() - 1; 
            System.out.print("Enter Seat Number: ");
            int s = sc.nextInt() - 1; 
            // here we are checkinmg either the seats are booked or not?
            if (seats[r][s] == SeatStatus.BOOKED) {
                System.out.println("Seat already booked! Choose another.");
                i--; 
            } else {
                seats[r][s] = SeatStatus.BOOKED;
                bookedSeats += "Row " + (r + 1) + " Seat " + (s + 1) + ", ";
                System.out.println("Seat booked successfully!");
            }
        }
        // here we are printng booked recipts
        System.out.println("\n BOOKED RECEPTS ");
        System.out.println("Screen Name : " + screenName);
        System.out.println("Movie Name : " + movieName);
        System.out.println("Timing : " + timing);
        System.out.println("Seats Booked: " + bookedSeats);
        System.out.println(" ");
    }
}
        String Screens = sc.nextLine();
        System.out.print("Enter Screen Name: ");
        String screenName = sc.nextLine();
        System.out.print("Enter Movie Name: ");
        String movieName = sc.nextLine();
        System.out.print("Enter Movie Timing: ");
        String timing = sc.nextLine();
        //asking no.of rows
        System.out.print("Enter Number of Rows: ");
        int rows = sc.nextInt();
        System.out.print("Enter Seats per Row: ");
        int seatsPerRow = sc.nextInt();
        // here 2Darray i had used because for seatinng arragngement as per row insted od 1d array
        SeatStatus[][] seats = new SeatStatus[rows][seatsPerRow];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < seatsPerRow; j++) {
                seats[i][j] = SeatStatus.AVAILABLE;
            }
        }
        System.out.println("\nSeat Layout (A = Available, B = Booked)");
        for (int i = 0; i < rows; i++) {
            System.out.print("Row " + (i + 1) + ": ");
            for (int j = 0; j < seatsPerRow; j++) {
                System.out.print(seats[i][j] == SeatStatus.AVAILABLE ? "A " : "B ");
            }
            System.out.println();
        }
        // here we are asking for coustumer to book seats how many they want
        System.out.print("\nHow many seats do you want to book? ");
        int count = sc.nextInt();
        String bookedSeats = "";
        for (int i = 0; i < count; i++) {
            System.out.print("Enter Row Number: ");
            int r = sc.nextInt() - 1; 
            System.out.print("Enter Seat Number: ");
            int s = sc.nextInt() - 1; 
            // here we are checkinmg either the seats are booked or not?
            if (seats[r][s] == SeatStatus.BOOKED) {
                System.out.println("Seat already booked! Choose another.");
                i--; 
            } else {
                seats[r][s] = SeatStatus.BOOKED;
                bookedSeats += "Row " + (r + 1) + " Seat " + (s + 1) + ", ";
                System.out.println("Seat booked successfully!");
            }
        }
        // here we are printng booked recipts
        System.out.println("\n BOOKED RECEPTS ");
        System.out.println("Screen Name : " + screenName);
        System.out.println("Movie Name : " + movieName);
        System.out.println("Timing : " + timing);
        System.out.println("Seats Booked: " + bookedSeats);
        System.out.println(" ");
    }
}