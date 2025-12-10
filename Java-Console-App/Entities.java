import java.util.ArrayList;

// ===== Interface =====
interface Registrable {
    void registerCourse(Course course, Double grade);
}

// ===== Course Class =====
class Course {

    private Integer courseId;      // Wrapper
    private String courseName;
    private Integer creditHours;   // Wrapper

    public Course(Integer courseId, String courseName, Integer creditHours) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.creditHours = creditHours;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public Integer getCreditHours() {
        return creditHours;
    }

    @Override
    public String toString() {
        return courseId + " - " + courseName + " (" + creditHours + " CH)";
    }
}

// ===== Student Class (with Inner Class) =====
class Student implements Registrable {

    private Integer studentId;
    private String name;
    private ArrayList<CourseRegistration> registrations;

    public Student(Integer studentId, String name) {
        this.studentId = studentId;
        this.name = name;
        this.registrations = new ArrayList<>();
    }

    @Override
    public void registerCourse(Course course, Double grade) {
        CourseRegistration reg = new CourseRegistration(course, grade);
        registrations.add(reg);
    }

    public void printReport() {
        StringBuilder sb = new StringBuilder();

        sb.append("===== Student Report =====\n");
        sb.append("Name: ").append(name).append("\n");
        sb.append("ID  : ").append(studentId).append("\n\n");

        if (registrations.isEmpty()) {
            sb.append("No course registrations found.\n");
        } else {
            sb.append("Courses:\n");
            for (CourseRegistration reg : registrations) {
                sb.append("- ")
                  .append(reg.getCourse().getCourseName())
                  .append(" [ID: ").append(reg.getCourse().getCourseId()).append("]")
                  .append(", Credit Hours: ").append(reg.getCourse().getCreditHours())
                  .append(", Grade: ");

                Double g = reg.getGrade();
                if (g == null) {
                    sb.append("N/A");
                } else {
                    sb.append(g);
                }
                sb.append("\n");
            }
        }

        System.out.println(sb.toString());
    }

    public Integer getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    public ArrayList<CourseRegistration> getRegistrations() {
        return registrations;
    }

    // ===== Inner Class =====
    class CourseRegistration {
        private Course course;
        private Double grade;

        public CourseRegistration(Course course, Double grade) {
            this.course = course;
            this.grade = grade;
        }

        public Course getCourse() {
            return course;
        }

        public Double getGrade() {
            return grade;
        }
    }
}
