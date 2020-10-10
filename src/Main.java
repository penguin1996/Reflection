package src;

import src.reflection.Student;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Main {

    public static void main(String[] args) throws Exception {

        System.out.println("获取Class对象名：");
        getClassName();
        System.out.println("获取Class对象中的字段名：");
        getField();
        System.out.println("获取Class对象中的构造方法名：");
        getConstructor();
        System.out.println("获取Class对象中的方法名：");
        getMethod();
        System.out.println("通过反射机制实例化对象：");
        getInstance();
    }

    /**
     * 第一种方法是通过类的全路径字符串获取 Class 对象，这也是我们平时最常用的反射获取 Class 对象的方法；
     * 第二种方法有限制条件：需要导入类的包；
     * 第三种方法已经有了 Student 对象，不再需要反射；
     * 通过这三种方式获取到的 Class 对象是同一个，也就是说  Java 运行时，每一个类只会生成一个 Class 对象。
     */
    private static void getClassName() throws ClassNotFoundException {
        // 1.通过字符串获取Class对象，这个字符串必须带上完整路径名
        Class studentClass1 = Class.forName("src.reflection.Student");
        // 2.通过类的class属性
        Class studentClass2 = Student.class;
        // 3.通过对象的getClass()函数
        Student studentObject = new Student();
        Class studentClass3 = studentObject.getClass();

        System.out.println(
                "class1 = " + studentClass1 + "\n" +
                        "class2 = " + studentClass2 + "\n" +
                        "class3 = " + studentClass3 + "\n" +
                        "class1 == class2 ? " + (studentClass1 == studentClass2) + "\n" +
                        "class2 == class3 ? " + (studentClass2 == studentClass3));
    }

    /**
     *获取字段有两个 API：getDeclaredFields 和 getFields。
     * 他们的区别是:
     * getDeclaredFields 用于获取所有声明的字段，包括公有字段和私有字段；
     * getFields 仅用来获取公有字段。
     */
    private static void getField() throws ClassNotFoundException {
        // 1.通过字符串获取Class对象，这个字符串必须带上完整路径名
        Class studentClass = Class.forName("src.reflection.Student");
        // 1.获取所有声明的字段
        Field[] declaredFieldList = studentClass.getDeclaredFields();
        for (Field declaredField : declaredFieldList) {
            System.out.println("declared Field: " + declaredField);
        }
        // 2.获取所有公有的字段
        Field[] fieldList = studentClass.getFields();
        for (Field field : fieldList) {
            System.out.println("field: " + field);
        }
    }

    /**
     *获取构造方法同样包含了两个 API：用于获取所有构造方法的getDeclaredConstructors 和用于获取公有构造方法的 getConstructors
     */
    private  static void getConstructor() throws ClassNotFoundException {
        // 1.通过字符串获取Class对象，这个字符串必须带上完整路径名
        Class studentClass = Class.forName("src.reflection.Student");
        // 1.获取所有声明的构造方法
        Constructor[] declaredConstructorList = studentClass.getDeclaredConstructors();
        for (Constructor declaredConstructor : declaredConstructorList) {
            System.out.println("declared Constructor: " + declaredConstructor);
        }
        // 2.获取所有公有的构造方法
        Constructor[] constructorList = studentClass.getConstructors();
        for (Constructor constructor : constructorList) {
            System.out.println("constructor: " + constructor);
        }
    }

    /**
     * 同样地，获取非构造方法的两个 API 是：获取所有声明的非构造函数的 getDeclaredMethods 和仅获取公有非构造函数的 getMethods
     * getMethods 方法不仅获取到了我们声明的公有方法 setStudentAge，还获取到了很多 Object 类中的公有方法。这是因为我们前文已说到：
     * Object 是所有 Java 类的父类。所有对象都默认实现了 Object 类的方法。
     * 而 getDeclaredMethods 是无法获取到父类中的方法的。
     * @throws ClassNotFoundException
     */
    private static void getMethod() throws ClassNotFoundException {
        // 1.通过字符串获取Class对象，这个字符串必须带上完整路径名
        Class studentClass = Class.forName("src.reflection.Student");
        // 1.获取所有声明的函数
        Method[] declaredMethodList = studentClass.getDeclaredMethods();
        for (Method declaredMethod : declaredMethodList) {
            System.out.println("declared Method: " + declaredMethod);
        }
        // 2.获取所有公有的函数
        Method[] methodList = studentClass.getMethods();
        for (Method method : methodList) {
            System.out.println("method: " + method);
        }
    }

    /**
     * 先用第一种全路径获取 Class 的方法获取到了 Student 的 Class 对象
     * 然后反射调用它的私有构造方法 private Student(String studentName)，构建出 newInstance
     * 再将其公有字段 studentAge 设置为 10
     * 最后反射调用其私有方法 show，传入参数 “message”，并打印出这个方法的返回值。
     * 其中，setAccessible 函数用于动态获取访问权限，Constructor、Field、Method 都提供了此方法，让我们得以访问类中的私有成员。
     * @throws Exception
     */
    private static void getInstance() throws Exception {
        // 1.通过字符串获取Class对象，这个字符串必须带上完整路径名
        Class studentClass = Class.forName("src.reflection.Student");

        // 2.获取声明的构造方法，传入所需参数的类名，如果有多个参数，用','连接即可
        Constructor studentConstructor = studentClass.getDeclaredConstructor(String.class);

        // 如果是私有的构造方法，需要调用下面这一行代码使其可使用，公有的构造方法则不需要下面这一行代码
        studentConstructor.setAccessible(true);

        // 使用构造方法的newInstance方法创建对象，传入构造方法所需参数，如果有多个参数，用','连接即可
        Object student = studentConstructor.newInstance("NameA");

        // 3.获取声明的字段，传入字段名
        Field studentAgeField = studentClass.getDeclaredField("studentAge");

        /*如果是私有的字段，需要调用下面这一行代码使其可使用，公有的字段则不需要下面这一行代码
         studentAgeField.setAccessible(true);*/

        // 使用字段的set方法设置字段值，传入此对象以及参数值
        studentAgeField.set(student,10);

        // 4.获取声明的函数，传入所需参数的类名，如果有多个参数，用','连接即可
        Method studentShowMethod = studentClass.getDeclaredMethod("show",String.class);

        // 如果是私有的函数，需要调用下面这一行代码使其可使用，公有的函数则不需要下面这一行代码
        studentShowMethod.setAccessible(true);

        // 使用函数的invoke方法调用此函数，传入此对象以及函数所需参数，如果有多个参数，用','连接即可。函数会返回一个Object对象，使用强制类型转换转成实际类型即可
        Object result = studentShowMethod.invoke(student,"message");
        System.out.println("result: " + result);
    }
}
