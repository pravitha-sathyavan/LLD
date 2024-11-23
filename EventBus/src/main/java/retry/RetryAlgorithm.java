package retry;


import exception.RetryAbleException;
import exception.RetryLimitExceededException;

import java.util.function.Function;

/*
The class allows you to execute a task, retrying in case of recoverable exceptions,
while adhering to a maximum retry limit and incorporating a configurable delay between attempts.
*/

public abstract class RetryAlgorithm<PARAMETER, RESULT> {
    // input parameter (PARAMETER) and produces a result (RESULT)

    private final int maxAttempts;
    private final Function<Integer, Long> retryTimeCalculator;
    // A function that determines the wait time (in milliseconds) between retries, based on the current attempt number.

    public RetryAlgorithm(final int maxAttempts,
                          final Function<Integer, Long> retryTimeCalculator) {
        this.maxAttempts = maxAttempts;
        this.retryTimeCalculator = retryTimeCalculator;
    }

    /*
    Example of retryTimeCalculator which takes Integer input and Long output
    Function<Integer, Long> retryTimeCalculator = attempt -> attempt * 1000L; // 1st attempt: 1s, 2nd: 2s, 3rd: 3s, etc.
    Function<Integer, Long> retryTimeCalculator = attempt -> (long) Math.pow(2, attempt) * 100L;
    */

    public RESULT attempt(Function<PARAMETER, RESULT> task, PARAMETER parameter, int attempts) {
        try {
            return task.apply(parameter);
        } catch (Exception e) {
            if (e.getCause() instanceof RetryAbleException) {
                if (attempts == maxAttempts) {
                    throw new RetryLimitExceededException();
                } else {
                    final RESULT result = attempt(task, parameter, attempts + 1);
                    try {
                        Thread.sleep(retryTimeCalculator.apply(attempts));
                        return result;
                    } catch (InterruptedException interrupt) {
                        throw new RuntimeException(interrupt);
                    }
                }
            } else {
                throw new RuntimeException(e);
            }
        }
    }
    // It calls the apply method of the Function interface, which is a functional interface in Java.
    // This method is used to execute the logic of the task that has been provided as input.

}
