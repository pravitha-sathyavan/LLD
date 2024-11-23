package retry;

import exception.RetryAbleException;

import java.util.function.Function;

public class RetryClassInputExamples {

    //Example 1
    Function<String, String> successfulTask = input -> "Processed: " + input;
    String result1 = retryAlgorithm.attempt(successfulTask, "Task1", 1);
    //  Processed: Task1


    //Example 2
    Function<String, String> delayedTask = new Function<>() {
    private int attemptCount = 0;

    @Override
    public String apply(String input) {
        attemptCount++;
        if (attemptCount < 4) {
            throw new RetryAbleException("Temporary failure on attempt " + attemptCount);
        }
        return "Success after " + attemptCount + " attempts";
        }
    };

    RetryAlgorithm<String, String> retryAlgorithm = new RetryAlgorithm<>(5, attempt -> (long) Math.pow(2, attempt) * 100L);
    String result2 = retryAlgorithm.attempt(delayedTask, "Task5", 1);
    //=> Success after 4 attempts

   //Example 3
   class TaskInput {
    String data;
    int threshold;

    public TaskInput(String data, int threshold) {
        this.data = data;
        this.threshold = threshold;
    }

    @Override
    public String toString() {
        return "TaskInput{data='" + data + "', threshold=" + threshold + "}";
        }
    }
    // Task that processes the input and succeeds if the threshold is valid.
    Function<TaskInput, String> processComplexInputTask = input -> {
        if (input.threshold >= 10) {
            return "Successfully processed input: " + input;
        } else {
            throw new RetryAbleException("Threshold too low: " + input.threshold);
    }
    };

    // Initialize RetryAlgorithm with 3 attempts and a retry time of 500ms.
    RetryAlgorithm<TaskInput, String> retryAlgorithm = new RetryAlgorithm<>(3, attempt -> attempt * 500L);

    // Complex input with valid data and threshold.
    TaskInput input = new TaskInput("ImportantData", 15);

    // Call the attempt function.
    String result = retryAlgorithm.attempt(processComplexInputTask, input, 1);

    // Output the result.
    //=> Successfully processed input: TaskInput{data='ImportantData', threshold=15}


    //Example 4

    // Input class to represent the task's input.
    class TaskInput1 {
        String name;
        int priority;

        public TaskInput1(String name, int priority) {
            this.name = name;
            this.priority = priority;
        }

        @Override
        public String toString() {
            return "TaskInput{name='" + name + "', priority=" + priority + "}";
        }
    }

    // Output class to represent the task's result.
    class TaskResult {
        String status;
        TaskInput1 input;

        public TaskResult(String status, TaskInput1 input) {
            this.status = status;
            this.input = input;
        }

        @Override
        public String toString() {
            return "TaskResult{status='" + status + "', input=" + input + "}";
        }
    }

    // Task that processes a complex input and returns a TaskResult object.
    Function<TaskInput1, TaskResult> processTask = input -> {
        if (input.priority < 5) {
            throw new RetryAbleException("Priority too low for input: " + input);
        }
        return new TaskResult("Success", input);
    };

    // Initialize RetryAlgorithm with 3 attempts and exponential backoff retry logic.
    RetryAlgorithm<TaskInput, TaskResult> retryAlgorithm = new RetryAlgorithm<>(3, attempt -> attempt * 1000L);

    // Complex input with valid data.
    TaskInput input2 = new TaskInput("SampleTask", 7);

    // Call the attempt function.
    TaskResult result3 = retryAlgorithm.attempt(processTask, input, 1);

   // TaskResult{status='Success', input=TaskInput{name='SampleTask', priority=7}}

}
