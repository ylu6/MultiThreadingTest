import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by yeqing on 9/4/2017.
 */
public class FooBarDemo4 {
    AtomicBoolean printFoo = new AtomicBoolean(true);

    void printFoo(int n) {
        for (int i = 0; i < n; i++) {
            synchronized (printFoo) {
                while (!printFoo.get()) { // time to print Bar, the thread running printFoo should wait
                    try {
                        printFoo.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.print("Foo");
                printFoo.set(false);
                printFoo.notify();
            }
        }
    }

    void printBar(int n) {
        for (int i = 0; i < n; i++) {
            synchronized (printFoo) {
                while (printFoo.get()) { // time to print Foo, the thread running printBar should wait
                    try {
                        printFoo.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("Bar"); // printFoo is false, and this thread is active: either was not in wait or other thread called notify()
                printFoo.set(true);
                printFoo.notify();
            }
        }
    }

    public static void main(String[] args) {
        FooBarDemo4 demo = new FooBarDemo4();

        Thread t1 = new Thread(){
            public void run(){
                demo.printFoo(10);
            }
        };

        Thread t2 = new Thread(){
            public void run() {
                demo.printBar(10);
            }
        };

        t1.start();
        t2.start();
    }
}
