import java.util.concurrent.SynchronousQueue;

/**
 * Created by yeqing on 9/3/2017.
 */
// Difference between SynchronousQueue and LinkedBlockingQueue of size 1:
// The difference being that the put() call to a SynchronousQueue will not return until there is a corresponding take() call,
// but with a LinkedBlockingQueue of size 1, the put() call (to an empty queue) will return immediately.
public class FooBarDemo2 {
    // each insert operation must wait for a corresponding remove operation by another thread, and vice versa.
    // which means insert only executed when another thread is ready to read
    SynchronousQueue<Integer> sq = new SynchronousQueue<>();

    void printFoo(int max) {
        try {
            for (int i = 0; i < max; i++) {
                System.out.print("Foo");
                sq.put(0); // insert, wait until other thread's take is ready
//                sq.take();
                sq.put(0);
            }
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
    void printBar(int max) {
        try {
            for (int i = 0; i < max; i++) {
                sq.take(); // wait until other thread's put is ready
                System.out.println("Bar");
//                sq.put(0);
                sq.take();
            }
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        FooBarDemo2 fb = new FooBarDemo2();
        Thread foo = new Thread(){
            public void run(){
                fb.printFoo(10);
            }
        };
        Thread bar = new Thread() {
            public void run() {
                fb.printBar(10);
            }
        };
        foo.start();
        bar.start();
    }
}
