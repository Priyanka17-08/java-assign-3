import java.util.*;

class InvalidMarksException extends Exception {
    public InvalidMarksException(String message) {
        super(message);
    }
}

class Student {
    private int rollNumber;
    private String studentName;
    private int[] marks = new int[3];

    public Student(int rollNumber, String studentName, int[] marks) throws InvalidMarksException {
        this.rollNumber = rollNumber;
        this.studentName = studentName;
        this.marks = marks;
        validateMarks();
    }

    public void validateMarks() throws InvalidMarksException {
        for (int i = 0; i < marks.length; i++) {
            if (marks[i] < 0 || marks[i] > 100) {
                throw new InvalidMarksException("Invalid marks for subject " + (i + 1) + ": " + marks[i]);
            }
        }
    }

    public double calculateAverage() {
        int total = 0;
        for (int m : marks) total += m;
        return total / 3.0;
    }
    public void displayResult() {
        System.out.println("Roll Number: " + rollNumber);
        System.out.println("Student Name: " + studentName);
        System.out.print("Marks: ");
        for (int m : marks) System.out.print(m + " ");
        System.out.println();
        double avg = calculateAverage();
        System.out.println("Average: " + avg);
        System.out.println("Result: " + (avg >= 40 ? "Pass" : "Fail"));
    }

    public int getRollNumber() {
        return rollNumber;
    }
}

public class ResultManager {
    private static Scanner sc = new Scanner(System.in);
    private static Student[] students = new Student[10];
    private static int count = 0;

    public static void addStudent() {
        try {
            System.out.print("Enter Roll Number: ");
            int roll = sc.nextInt();
            sc.nextLine(); 

            System.out.print("Enter Student Name: ");
            String name = sc.nextLine();

            int[] marks = new int[3];
            for (int i = 0; i < 3; i++) {
                System.out.print("Enter marks for subject " + (i + 1) + ": ");
                marks[i] = sc.nextInt();
            }
            Student s = new Student(roll, name, marks);
            students[count++] = s;
            System.out.println("Student added successfully. Returning to main menu...\n");

        } catch (InvalidMarksException e) {
            System.out.println("Error: " + e.getMessage() + "\nReturning to main menu...\n");
        } catch (InputMismatchException e) {
            System.out.println("Error: Invalid input type. Please enter valid numbers.\n");
            sc.nextLine();
        } catch (Exception e) {
            System.out.println("Unexpected Error: " + e.getMessage());
        }
    }
    public static void showStudentDetails() {
        try {
            System.out.print("Enter Roll Number to search: ");
            int roll = sc.nextInt();
            boolean found = false;

            for (int i = 0; i < count; i++) {
                if (students[i].getRollNumber() == roll) {
                    students[i].displayResult();
                    found = true;
                    break;
                }
            }

            if (!found) {
                System.out.println("Student with roll number " + roll + " not found.");
            }

            System.out.println("Search completed.\n");
        } catch (InputMismatchException e) {
            System.out.println("Error: Please enter a valid roll number.\n");
            sc.nextLine();
        }
    }

    public static void mainMenu() {
        try {
            while (true) {
                System.out.println("===== Student Result Management System =====");
                System.out.println("1. Add Student");
                System.out.println("2. Show Student Details");
                System.out.println("3. Exit");
                System.out.print("Enter your choice: ");
                int choice = sc.nextInt();

                switch (choice) {
                    case 1:
                        addStudent();
                        break;
                    case 2:
                        showStudentDetails();
                        break;
                    case 3:
                        System.out.println("Exiting program. Thank you!");
                        return;
                    default:
                        System.out.println("Invalid choice. Try again.\n");
                }
            }
        } catch (Exception e) {
            System.out.println("Error occurred: " + e.getMessage());
        } finally {
            sc.close();
            System.out.println("Scanner closed. Program terminated safely.");
        }
    }
    public static void main(String[] args) {
        mainMenu();
    }
}