


import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;


public class InputValidator {

    private Scanner input;
    private Set<String> usedProcessNames;


    public InputValidator() {

        input = new Scanner(System.in);
        usedProcessNames = new HashSet<>();

    }

    // Validate process name
    public String getValidProcessName() {

         while (true) {
            System.out.print("Enter process name: ");

            String name = input.nextLine().trim();

            if (name.isEmpty()) {
                System.out.println("Process name cannot be empty.");
                continue;
            }

            if (usedProcessNames.contains(name)) {
                System.out.println("Process name already exists. Please enter a unique name.");
                continue;
            }

            usedProcessNames.add(name);
            return name;
        }
    }

    // Validate arrival time
    public int getValidArrivalTime() {

        int arrivalTime;

        while (true) {

            System.out.print("Enter arrival time: ");

            if (input.hasNextInt()) {

                arrivalTime = input.nextInt();

                if (arrivalTime >= 0) {

                    input.nextLine();
                    return arrivalTime;
                }

                System.out.println(
                    "Arrival time cannot be negative."
                );

            } else {

                System.out.println(
                    "Invalid input. Enter a valid integer."
                );

                input.next();
            }
        }
    }

    // Validate burst time
    public int getValidBurstTime() {

        int burstTime;

        while (true) {

            System.out.print("Enter burst time: ");

            if (input.hasNextInt()) {

                burstTime = input.nextInt();

                if (burstTime > 0) {

                    input.nextLine();
                    return burstTime;
                }

                System.out.println(
                    "Burst time must be greater than 0."
                );

            } else {

                System.out.println(
                    "Invalid input. Enter a valid integer."
                );

                input.next();
            }
        }
    }

    // Validate number of processes
    public int getValidNumberOfProcesses() {

        int number;

        while (true) {

            System.out.print("Enter number of processes: ");

            if (input.hasNextInt()) {

                number = input.nextInt();

                if (number > 0) {

                    input.nextLine();
                    return number;
                }

                System.out.println(
                    "Number must be greater than 0."
                );

            } else {

                System.out.println(
                        "Invalid input. Enter a valid integer."
                );

                input.next();
            }
        }
    }

    public int getValidQuantum() {
        while (true) {
            System.out.print("Enter Time Quantum: ");

            if (input.hasNextInt()) {
                int quantum = input.nextInt();
                input.nextLine(); // تنظيف السطر

                if (quantum > 0) {
                    return quantum;
                } else {
                    System.out.println("Time Quantum must be greater than 0.");
                }
            } else {
                System.out.println("Invalid input. Please enter a valid integer.");
                input.nextLine(); // حذف الإدخال غير الصحيح
            }
        }
    }

    public void resetProcessNames() {
        usedProcessNames.clear();
    }
}
