package reflection;

/**
 * @ClassName Student
 * @Description TODO
 * @Author c.b.zhou
 * @Date 11/7/2019 5:50 PM
 * @Version 1.0
 **/
public class Student {

    private String studentName;
    public int studentAge;

    public Student() {
    }

    private Student(String studentName) {
        this.studentName = studentName;
    }

    public void setStudentAge(int studentAge) {
        this.studentAge = studentAge;
    }

    private String show(String message) {
        System.out.println("show: " + studentName + "," + studentAge + "," + message);
        return "testReturnValue";
    }

}
