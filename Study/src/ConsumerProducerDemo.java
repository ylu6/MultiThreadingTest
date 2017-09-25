import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by yeqing on 9/2/2017.
 */

public class ConsumerProducerDemo {
    public static void main(String[] args) {
        BlockingQueue<Integer> q = new LinkedBlockingQueue<>(3);
        Consumer c1 = new Consumer(q);
        Consumer c2 = new Consumer(q);
        Producer p1 = new Producer(q);
        Thread t1 = new Thread(c1);
        Thread t2 = new Thread(c2);
        Thread t3 = new Thread(p1);
        t1.start();
        t2.start();
        t3.start();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        t1.interrupt();
        t2.interrupt();
        t3.interrupt();
    }
}

class Producer implements  Runnable {
    private BlockingQueue<Integer> queue;
    public Producer(BlockingQueue<Integer> q)   { queue = q; }
    public void run(){
        try {
            for(int i = 1; i < 10; i++) {
                queue.put(i);
                System.out.println("put" + i);
                Thread.sleep((int)(Math.random()*100));
            }
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
}

class Consumer implements  Runnable {
    private BlockingQueue<Integer> queue;

    public Consumer(BlockingQueue<Integer> q)   { queue = q; }
    public void run() {
        try {
            for (int i = 1; i < 10; i++) {
                System.out.println("got " + queue.take());
                Thread.sleep((int)(Math.random()*100));
            }
        } catch (InterruptedException e) {
            System.out.println("Consumer Thread Was Interrupted.");
        }
    }
}

