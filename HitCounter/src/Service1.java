public class Service1 implements Runnable {
    HitCounter counter;

    public Service1(HitCounter counter) {
        this.counter = counter;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            counter.recordHit();
        }
    }
}
