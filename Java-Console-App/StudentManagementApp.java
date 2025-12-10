import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class StudentManagementApp {

    private static ArrayList<Student> students = new ArrayList<>();
    private static ArrayList<Course> courses = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        seedCourses();

        boolean running = true;
        while (running) {
            System.out.println("======= Student Management System =======");
            System.out.println("1. Add new student");
            System.out.println("2. Register courses for a student");
            System.out.println("3. Print report for one student");
            System.out.println("4. Print reports for all students");
            System.out.println("0. Exit");
            System.out.print("Choose option: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    addStudent();
                    break;
                case "2":
                    registerCoursesForStudent();
                    break;
                case "3":
                    printSingleStudentReport();
                    break;
                case "4":
                    printAllReports();
                    break;
                case "0":
                    running = false;
                    System.out.println("Exiting... Bye!");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.\n");
            }
        }
    }

    private static void seedCourses() {
        courses.add(new Course(101, "OOP", 3));
        courses.add(new Course(102, "Data Structures", 4));
        courses.add(new Course(103, "Databases", 3));
        courses.add(new Course(104, "Algorithms", 3));
    }

    private static void listCourses() {
        System.out.println("Available Courses:");
        for (Course c : courses) {
            System.out.println("  " + c);
        }
        System.out.println();
    }

    private static void addStudent() {
        System.out.print("Enter student ID (integer): ");
        String idStr = scanner.nextLine().trim();

        System.out.print("Enter student name: ");
        String name = scanner.nextLine().trim();

        try {
            int idPrimitive = Integer.parseInt(idStr);
            Integer id = idPrimitive; // Autoboxing

            Student s = new Student(id, name);
            students.add(s);

            System.out.println("Student added successfully.\n");
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID. Student not added.\n");
        }
    }

    private static Student findStudentById(Integer id) {
        for (Student s : students) {
            if (s.getStudentId().equals(id)) {
                return s;
            }
        }
        return null;
    }

    private static Course findCourseById(Integer id) {
        for (Course c : courses) {
            if (c.getCourseId().equals(id)) {
                return c;
            }
        }
        return null;
    }

    private static void registerCoursesForStudent() {
        if (students.isEmpty()) {
            System.out.println("No students in the system.\n");
            return;
        }

        System.out.print("Enter student ID: ");
        String idStr = scanner.nextLine().trim();

        try {
            Integer id = Integer.parseInt(idStr);
            Student s = findStudentById(id);
            if (s == null) {
                System.out.println("Student not found.\n");
                return;
            }

            listCourses();
            System.out.println("Enter registrations as: courseId:grade,courseId:grade...");
            System.out.println("Example: 101:95.5,102:88,103:-  (use - if no grade yet)");
            System.out.print("Input: ");
            String line = scanner.nextLine().trim();

            if (line.isEmpty()) {
                System.out.println("No input.\n");
                return;
            }

            StringTokenizer pairTokenizer = new StringTokenizer(line, ",");

            while (pairTokenizer.hasMoreTokens()) {
                String pair = pairTokenizer.nextToken().trim();

                StringTokenizer valueTokenizer = new StringTokenizer(pair, ":");
                if (!valueTokenizer.hasMoreTokens()) continue;

                String courseIdStr = valueTokenizer.nextToken().trim();
                Integer courseId = Integer.parseInt(courseIdStr);

                Course course = findCourseById(courseId);
                if (course == null) {
                    System.out.println("Course with ID " + courseId + " not found. Skipping.");
                    continue;
                }

                Double grade = null;
                if (valueTokenizer.hasMoreTokens()) {
                    String gradeStr = valueTokenizer.nextToken().trim();
                    if (!gradeStr.equals("-") && !gradeStr.isEmpty()) {
                        double gPrimitive = Double.parseDouble(gradeStr);
                        grade = gPrimitive; // Autoboxing
                    }
                }

                s.registerCourse(course, grade);
            }

            System.out.println("Courses registered successfully for student " + s.getName() + ".\n");

        } catch (NumberFormatException e) {
            System.out.println("Invalid number format.\n");
        }
    }

    private static void printSingleStudentReport() {
        if (students.isEmpty()) {
            System.out.println("No students in the system.\n");
            return;
        }

        System.out.print("Enter student ID: ");
        String idStr = scanner.nextLine().trim();

        try {
            Integer id = Integer.parseInt(idStr);
            Student s = findStudentById(id);
            if (s == null) {
                System.out.println("Student not found.\n");
                return;
            }

            s.printReport();
            System.out.println();

        } catch (NumberFormatException e) {
            System.out.println("Invalid ID.\n");
        }
    }

    private static void printAllReports() {
        if (students.isEmpty()) {
            System.out.println("No students in the system.\n");
            return;
        }

        for (Student s : students) {
            s.printReport();
            System.out.println("-----------------------------");
        }
        System.out.println();
    }
}
