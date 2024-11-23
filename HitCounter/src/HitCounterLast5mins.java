import java.util.Deque;
import java.util.LinkedList;

public class HitCounter {
    private static final long FIVE_MINUTES_IN_MILLIS = 5 * 60 * 1000; // 5 minutes in milliseconds
    private final Deque<Long> timestamps;
    private final Deque<Integer> counts;

    public HitCounter() {
        this.timestamps = new LinkedList<>();
        this.counts = new LinkedList<>();
    }

    // Record a hit
    public void recordHit() {
        long currentTime = System.currentTimeMillis();
        // Remove outdated hits (older than 5 minutes)
        while (!timestamps.isEmpty() && timestamps.peekFirst() < currentTime - FIVE_MINUTES_IN_MILLIS) {
            timestamps.pollFirst();
            counts.pollFirst();
        }
        // Add the current hit
        timestamps.offerLast(currentTime);
        counts.offerLast(1);  // Each hit is counted as 1
    }

    // Get total hits in the past 5 minutes
    public int getHitsInLast5Minutes() {
        int totalHits = 0;
        long currentTime = System.currentTimeMillis();
        for (int i = 0; i < timestamps.size(); i++) {
            if (timestamps.get(i) >= currentTime - FIVE_MINUTES_IN_MILLIS) {
                totalHits += counts.get(i);
            }
        }
        return totalHits;
    }

    // Test the counter
    public static void main(String[] args) throws InterruptedException {
        HitCounter counter = new HitCounter();

        // Simulate hits
        counter.recordHit();
        Thread.sleep(1000);  // Sleep for 1 second
        counter.recordHit();
        Thread.sleep(1000);  // Sleep for 1 second
        counter.recordHit();

        // Get total hits in the last 5 minutes
        System.out.println("Total hits in the last 5 minutes: " + counter.getHitsInLast5Minutes());

        // Wait for more than 5 minutes and see the result (simulate the passing of time)
        Thread.sleep(5000);  // Sleep for 5 seconds
        System.out.println("Total hits after waiting 5 seconds: " + counter.getHitsInLast5Minutes());
    }
}
