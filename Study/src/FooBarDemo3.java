/**
 * Created by yeqing on 9/4/2017.
 */
public class FooBarDemo3 {
    Object lock1 = new Object();
    Object lock2 = new Object();
    boolean FooPrinted = false;

    synchronized void printFoo(int n) {
        for (int i = 0; i < n; i++) {
            while(FooPrinted) {
                try {
                    wait();
//                    Thread.sleep((int) (Math.random() * 500));
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
            }
            System.out.print("Foo");
            FooPrinted = true;
            notifyAll(); // if other thread is not waiting, nothing happends. But it is fine, no deadlock here
        }
    }

    synchronized void printBar(int n) {
        for (int i = 0; i < n; i++) {
            while (!FooPrinted) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
            }
            System.out.println("Bar");
            FooPrinted = false;
            notifyAll();
        }
    }

    public static void main(String[] args) {
        FooBarDemo3 demo = new FooBarDemo3();

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
