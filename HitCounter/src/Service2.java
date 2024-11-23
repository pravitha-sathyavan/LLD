public class Service2  implements Runnable{
    HitCounter counter;

    public Service2(HitCounter counter) {
        this.counter = counter;
    }

    @Override
    public void run() {
        counter.recordHit();
    }
}
