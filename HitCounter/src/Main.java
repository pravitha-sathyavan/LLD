//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws InterruptedException {
        HitCounter counter = new HitCounter();

        Thread t1 = new Thread(new Service1(counter));
        t1.start();

        Thread t2 = new Thread(new Service2(counter));
        t2.start();
        t1.join();
        t2.join();

        System.out.println("Total Hits: " + counter.getHits()); // Should print 200

        // Reset the counter
        counter.reset();
        System.out.println("After Reset: " + counter.getHits()); // Should print 0
    }
}