import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by yeqing on 9/4/2017.
 * This version is InCorrect!!!
 */
public class ConsumerProducerDemo3 {
    public static void main(String[] args) {
        Queue<Integer> sharedQueue = new LinkedList<>();
        Thread t1 = new Thread(new Consumer3(sharedQueue), "consumer1");
        Thread t2 = new Thread(new Consumer3(sharedQueue), "consumer2");
        Thread t3 = new Thread(new Producer3(sharedQueue, 3), "producer1");
//        Thread t4 = new Thread(new Producer3(sharedQueue, 3), "producer2");
        t1.start();
        t2.start();
        t3.start();
//        t4.start();
    }
}

class Consumer3 implements Runnable{
    private Queue<Integer> sharedQueue;

    public Consumer3(Queue sharedQueue) {
        this.sharedQueue = sharedQueue;
    }
    public void run(){
        for (int i = 0; i < 5; i++) {
            try {
                Thread.sleep((int) (Math.random() * 100));
            }catch (InterruptedException e) {}

            synchronized (sharedQueue) {
                while (sharedQueue.isEmpty()) {
                    try {
                        sharedQueue.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(Thread.currentThread().getName() + " got " + sharedQueue.poll());
                sharedQueue.notifyAll();
            }
        }
    }
}

class Producer3 implements Runnable{
    private Queue<Integer> sharedQueue;
    int MaxSize;
    public Producer3(Queue sharedQueue, int MaxSize){
        this.sharedQueue = sharedQueue;
        this.MaxSize = MaxSize;
    }
    public void run(){
        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep((int) (Math.random() * 50));
            }catch (InterruptedException e) {}

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
                sharedQueue.notifyAll();
            }
        }
    }

}
