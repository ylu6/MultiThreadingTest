import java.util.concurrent.Semaphore;

/**
 * Created by yeqing on 9/2/2017.
 * Two functions, one print Foo and the other print Bar
 * The two function are called in to thread, make sure print FooBar was printed on one line, and print X lines
 * VERSION 1: use Semaphore Synchronization
 */

public class FoobarDemo {
    Semaphore semFoo = new Semaphore(1);
    Semaphore semBar = new Semaphore(0);
    Semaphore semXXX = new Semaphore(0);

    void printFoo() {
        try {
            for (int i = 0; i < 10; i++) {
                semFoo.acquire();
                System.out.print("Foo");
                Thread.sleep((int) (Math.random()*100));
                semBar.release();
            }
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    void printBar() {
        try {
            for (int i = 0; i < 10; i++) {
                semBar.acquire();
                System.out.println("Bar");
                Thread.sleep((int) (Math.random()*100));
                semXXX.release();
            }
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    void printXXX() {
        try {
            for (int i = 0; i < 10; i++) {
                semXXX.acquire();
                System.out.println("XXX");
                Thread.sleep((int) (Math.random()*100));
                semFoo.release();
            }
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
    public static void main(String[] args) {
        FoobarDemo demo = new FoobarDemo();

        Thread t1 = new Thread(){
            public void run() {
                demo.printFoo();
            }
        };
        Thread t2 = new Thread(){
            public void run() {
                demo.printBar();
            }
        };
        Thread t3 = new Thread(){
            public void run() {
                demo.printXXX();
            }
        };
        t1.start();
        t2.start();
        t3.start();
    }
}
