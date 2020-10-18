public class Hello {

    public void hello(int a) {
        a = a + 2000;

        a = a * 10000;

        a = a / 10;

        if (a > 10) {
            System.out.println("hello" + a);
        }

        for (int i = 0; i < 3; i++) {
            a = a / 10;
            System.out.println("hello" + a);
        }
    }

    public static void main(String[] args) {
        int a = 1;
        new Hello().hello(a);

    }

}
