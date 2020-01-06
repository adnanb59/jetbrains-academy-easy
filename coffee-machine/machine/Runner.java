package machine;

public class Runner {
    public static void main(String[] args) {
        String a = "hello yellow jello";
        String b = "ll";
        int start = 0;
        System.out.println(a.substring(start).indexOf(b));
        start = a.substring(start).indexOf(b);
        if (start != -1) {
            start += b.length();
            System.out.println(start);
        }
        System.out.println(a.substring(start).indexOf(b));
        /* while (start >= 0 && start < a.length()) {
            start = a.substring(start).indexOf(b);
            if (start != -1) {
                c++;
                start = start + b.length();
            }
            System.out.println(start);
        }
        System.out.println(c); */
    }
}