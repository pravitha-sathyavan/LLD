package retry;

import com.google.inject.Inject;
import com.google.inject.name.Named;

public class PeriodicRetry<P, R> extends RetryAlgorithm<P, R> {

    @Inject
    public PeriodicRetry(@Named("periodic-retry-attempts") final int maxAttempts,
                          @Named("periodic-retry-wait") final long waitTimeInMillis) {
        super(maxAttempts, (__) -> waitTimeInMillis);
    }

    /*
    @Inject indicating that the dependencies for this class should be provided automatically by the framework.
    @Named:
    Used with dependency injection to identify specific configuration values by name (e.g., "periodic-retry-attempts" and "periodic-retry-wait").
    These names map to configuration entries or environment variables provided to the DI framework.

    super(maxAttempts, (__) -> waitTimeInMillis);
    Calls the RetryAlgorithm constructor with:
    maxAttempts: Sets the maximum retry attempts.
    A lambda function (__) -> waitTimeInMillis: Defines a constant delay for retries.
    The __ is a convention to indicate an unused parameter.
    */
}
