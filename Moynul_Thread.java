
import java.util.concurrent.atomic.AtomicLong;

public class Moynul_Thread {

    private static final AtomicLong safeStaticCounter =
            new AtomicLong(0);

    private static long unsafeStaticCounter = 0;

    private long instanceCounter = 0;
    private final int increments;
    private final boolean threadSafe;

    public Moynul_Thread(int increments, boolean threadSafe) {
        this.increments = increments;
        this.threadSafe = threadSafe;
    }

    public void runTask() {
        for (int i = 0; i < increments; i++) {
            if (threadSafe) {
                safeStaticCounter.incrementAndGet();
            } else {
                unsafeStaticCounter++;
            }

            instanceCounter++;
        }
    }

    public long getInstanceCounter() {
        return instanceCounter;
    }

    public static void main(String[] args)
            throws InterruptedException {

        if (args.length != 3) {
            System.out.println(
                "Usage: java Moynul_Thread <threads> "
                + "<increments> <true|false>");
            return;
        }

        int numberOfThreads = Integer.parseInt(args[0]);
        int increments = Integer.parseInt(args[1]);

        if (numberOfThreads <= 0 || increments < 0
                || !(args[2].equalsIgnoreCase("true")
                || args[2].equalsIgnoreCase("false"))) {
            System.out.println("Invalid input arguments.");
            return;
        }

        boolean threadSafe =
                Boolean.parseBoolean(args[2]);

        safeStaticCounter.set(0);
        unsafeStaticCounter = 0;

        Thread[] threads = new Thread[numberOfThreads];
        Moynul_Thread[] tasks =
                new Moynul_Thread[numberOfThreads];

        for (int i = 0; i < numberOfThreads; i++) {
            tasks[i] = new Moynul_Thread(
                    increments, threadSafe);

            Moynul_Thread task = tasks[i];
            threads[i] = new Thread(task::runTask);
        }

        for (int i = 0; i < numberOfThreads; i++) {
            threads[i].start();
        }

        for (int i = 0; i < numberOfThreads; i++) {
            threads[i].join();
        }

        long staticCount = threadSafe
                ? safeStaticCounter.get()
                : unsafeStaticCounter;

        long nonStaticTotal = 0;

        for (int i = 0; i < numberOfThreads; i++) {
            nonStaticTotal += tasks[i].getInstanceCounter();
        }

        long expectedCount =
                (long) numberOfThreads * increments;

        long absoluteDifference =
                Math.abs(staticCount - nonStaticTotal);

        double percentageDifference;

        if (nonStaticTotal != 0) {
            percentageDifference =
                    absoluteDifference * 100.0 / nonStaticTotal;
        } else if (staticCount == 0) {
            percentageDifference = 0.0;
        } else {
            percentageDifference = Double.NaN;
        }

        System.out.println("\n===== THREAD COUNTER RESULTS =====");
        System.out.println("Number of threads: " + numberOfThreads);
        System.out.println("Increments per thread: " + increments);
        System.out.println("Mode: "
                + (threadSafe ? "THREAD-SAFE" : "UNSAFE"));
        System.out.println("Expected count: " + expectedCount);
        System.out.println("Static count: " + staticCount);
        System.out.println("Non-static total: " + nonStaticTotal);
        System.out.println("Absolute difference: "
                + absoluteDifference);

        if (Double.isNaN(percentageDifference)) {
            System.out.println(
                    "Percentage difference: Undefined");
        } else {
            System.out.printf(
                    "Percentage difference: %.4f%%%n",
                    percentageDifference);
        }

        System.out.println("==================================");
    }
}
