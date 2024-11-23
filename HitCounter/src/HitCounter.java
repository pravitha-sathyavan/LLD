import java.util.concurrent.atomic.AtomicInteger;

public class HitCounter {
    private final AtomicInteger hits;

    public HitCounter() {
        this.hits = new AtomicInteger(0);
    }

    // Increment the hit count
    public void recordHit() {
        hits.incrementAndGet();
    }

    // Get the current hit count
    public int getHits() {
        return hits.get();
    }

    // Reset the counter, e.g., at specific intervals
    public void reset() {
        hits.set(0);
    }
}

