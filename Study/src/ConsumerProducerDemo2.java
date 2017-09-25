import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by yeqing on 9/4/2017.
 * Implemented a SharedQueue data structure, which has synchronized take and put method
 */

public class ConsumerProducerDemo2 {
    public static void main(String[] args) {

        SharedQueue<Integer> sq = new SharedQueue<>(3);
        Thread t1 = new Thread("Producer1") {
            public void run() {
                for (int i = 0; i < 10; i++) {
                    sq.put(i);
                    try {
                        Thread.sleep((int)(Math.random()*100));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        Thread t2 = new Thread("Consumer1") {
            public void run() {
                for (int i = 0; i < 5; i++) {
                    sq.take();
                    try {
                        Thread.sleep((int)(Math.random()*100));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        Thread t3 = new Thread("Consumer2") {
            public void run() {
                for (int i = 0; i < 5; i++) {
                    sq.take();
                    try {
                        Thread.sleep((int)(Math.random()*100));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        t1.start();
        t2.start();
        t3.start();
    }
}

class SharedQueue<E> {
    Queue<E> q;
    private final int capacity;

    public SharedQueue() {
        q = new LinkedList<E>();
        capacity = Integer.MAX_VALUE;
    }

    public SharedQueue(int capacity){
        q = new LinkedList<E>();
        this.capacity = capacity;
    }

    public synchronized void put(E value) {
        while(q.size()==capacity) {
            try{
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        q.add(value);
        System.out.println(Thread.currentThread().getName() + " added: " + value);
        notifyAll();
    }

    public synchronized E take() {
        while(q.isEmpty()) {
            try{
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        E value = q.poll();
        System.out.println(Thread.currentThread().getName() + " got " + value);
        notifyAll();
        return value;
    }
}
