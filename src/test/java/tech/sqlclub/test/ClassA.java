package tech.sqlclub.test;

/**
 * Created by songgr on 2020/10/25.
 */

public class ClassA {
    public static String ss = "ss111";
    public String s = "s";
    private String a;
    public ClassA(){
        System.out.println("ClassA_1");
    }
    public ClassA(String a){
        System.out.println("ClassA_2");
        this.a = a;
    }

    public static void testA1() {
        System.out.println("ClassA.testA1");
    }

    public void testA2() {
        System.out.println("ClassA.testA2");
    }

    public static void main(String[] args) throws Exception {
        Class.forName("tech.sqlclub.test.ClassA").getMethod("testA1").invoke(new ClassA());
    }

}


