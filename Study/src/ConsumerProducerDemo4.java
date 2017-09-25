import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by yeqing on 9/4/2017.
 */
public class ConsumerProducerDemo4 {
    public static void main(String[] args) {
        Queue<Integer> sharedQueue = new LinkedList<>();
        int MaxSize = 5;

        Thread t1 = new Thread(){
            public void run() {
                for (int i = 0; i < 10; i++) {
                    synchronized (sharedQueue) {
                        while (sharedQueue.isEmpty()) {
                            try {
                                sharedQueue.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        System.out.println(Thread.currentThread().getName() + " got " + sharedQueue.poll());
                        sharedQueue.notify();
                    }
                }
            }
        };

        Thread t2 = new Thread(){
            public void run(){
                for (int i = 0; i < 10; i++) {
                    synchronized (sharedQueue) {
                        while (sharedQueue.size() == MaxSize) {
                            try {
                                sharedQueue.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        sharedQueue.add(i);
                        System.out.println(Thread.currentThread().getName() + " added " + i);
                        sharedQueue.notify();
                    }
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t2.start();
        t1.start();
    }
}
