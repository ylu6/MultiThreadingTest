import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/**
 * Created by yeqing on 9/24/2017.
 */
public class ForkJoinTest {
    public static double parArraySum(double[] X) {
        SumArray t = new SumArray(X, 0, X.length);
        Runtime runtime = Runtime.getRuntime();
        System.out.println(runtime.availableProcessors());
//        ForkJoinPool forkJoinPool = new ForkJoinPool(4);
        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        long startTime = System.nanoTime();
//        ForkJoinPool.commonPool().invoke(t);
        forkJoinPool.invoke(t);
        double sum = t.ans;
        long timeInNanos = System.nanoTime() - startTime;
        System.out.format("parArraySum: %f %f \n",timeInNanos/1e6, sum);
        return sum;
    }

    private static class SumArray extends RecursiveAction {
        static int SEQUENTIAL_THRESHOLD = 200000;
        int lo, hi;
        double[] arr;
        double ans = 0;

        SumArray(double[] X, int lo, int hi) {
            this.lo = lo;
            this.hi = hi;
            arr = X;
        }

        protected  void compute(){
            if (hi - lo <= SEQUENTIAL_THRESHOLD) {
                for (int i = lo; i < hi; i++)
                    ans += 1 / arr[i];
            }
            else {
                int mid = lo + (hi-lo)/2;
                SumArray left = new SumArray(arr, lo, mid);
                SumArray right = new SumArray(arr, mid, hi);
//                left.fork();
//                right.compute();
//                left.join();
                invokeAll(left, right);
                ans = left.ans + right.ans;
            }
        }
    }

    public static double seqArraySum(double[] X) {
        long startTime = System.nanoTime();
        double sum = 0;
        for (double d : X)
            sum += 1/d;
        double timeInNanos = System.nanoTime() - startTime;
        System.out.format("seqArraySum: %f %f", timeInNanos/1e6, sum);
        return sum;
    }

    public static void main(String[] args) {
        double[] X = TestUtility.createArray(50000000);
        double parSum = parArraySum(X);
        seqArraySum(X);
    }
}



