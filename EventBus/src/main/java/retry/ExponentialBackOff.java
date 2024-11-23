package retry;

import com.google.inject.Inject;
import com.google.inject.name.Named;

public class ExponentialBackOff<P, R> extends RetryAlgorithm<P, R> {

    @Inject
    public ExponentialBackOff(@Named("exponential-retry-attempts") final int maxAttempts) {
        super(maxAttempts, (attempts) -> (long) Math.pow(2, attempts - 1) * 1000);
    }
}
