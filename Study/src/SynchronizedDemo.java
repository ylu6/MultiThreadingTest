/**
 * Created by yeqing on 9/4/2017.
 */
public class SynchronizedDemo {
    synchronized void print1(int n) {
        try {
            for (int i = 1; i < n; i++) {
                System.out.println("print1: " + i);
                Thread.sleep((int)(Math.random()*500));
            }
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
    synchronized  void print2(int n) {
        try {
            for (int i = 1; i < n; i++) {
                System.out.println("print2: " + i);
                Thread.sleep((int)(Math.random()*500));
            }
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        SynchronizedDemo demo = new SynchronizedDemo();

        Thread t1 = new Thread() {
            public void run() {
                demo.print1(10);
            }
        };

        Thread t2 = new Thread() {
            public void run() {
                demo.print2(10);
            }
        };
        t1.start();
        t2.start();
    }
}
