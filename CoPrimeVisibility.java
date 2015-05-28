import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import static java.lang.Runtime.getRuntime;
import static java.lang.String.format;
import static java.lang.System.currentTimeMillis;
import static java.lang.System.out;
import static java.util.concurrent.Executors.newFixedThreadPool;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

/**
 *
 * Please see the description details of the solution in the solution.README file
 *
 * @author erusak.
 */
public class CoPrimeVisibility {

    public static final int MAX_N_OF_TEST_CASES = 7;
    public static final int MIN_N_DIMENSION = 1;
    public static final int MAX_N_DIMENSION = 10000;

    public static void main(String[] args) throws Exception {

        final Scanner scanner = new Scanner(System.in);

        out.println(format("Please input number of test cases. Should be within [1, %s]:", MAX_N_OF_TEST_CASES));

        //getting user input for number of test cases
        int numOfTestCases = userInput(scanner, 1, MAX_N_OF_TEST_CASES);

        //initializing test cases
        int[] inputs = initializeTestCases(scanner, numOfTestCases);

        //run algorithm for each test case and display the results
        for (final int input : inputs) {
            //first sequential code
            long startS = currentTimeMillis();
            out.println("Single-threaded execution results... Please wait...");
            out.println(
                format("[%s X %s matrix] -> number of visible dots: %s, time spent: %s millis",
                    input + 1, input + 1,
                    // computation
                    dots(input),
                    MILLISECONDS.convert(currentTimeMillis() - startS , MILLISECONDS)
                )
            );

            //and then try multi-threaded for comparison
            long startPar = currentTimeMillis();
            out.println("Multi-threaded execution results... Please wait...");
            out.println(
                    format("[%s X %s matrix] -> number of visible dots: %s, time spent: %s millis",
                            input + 1, input + 1,
                            // computation
                            dotsPar(input),
                            MILLISECONDS.convert(currentTimeMillis() - startPar , MILLISECONDS)
                    )
            );
        }

        scanner.close();

    }

    //stack safe call (tail recursion)
    private static int gcd(int a, int b) {
        return (b == 0) ? a : gcd(b, a % b);
    }

    // sequential/core algorithm
    private static long dots(int n) {
        //symmetric and skip initial results for readability
        long result = 0;
        for(int step = 2; step < n; step++) {
            // get number of all co-primes to step less then n
            result = result + coPrimes(step, n);
        }
        return result * 2 + 2 * n + 1;
    }

    private static int coPrimes(int step, int n) {
        int numOfCoPrimes = 0; boolean isEvenStep = (step & 1) == 0;
        for(int n1 = step + 1; n1 <= n; n1++) {
            if( !(isEvenStep && (n1 & 1) == 0) // do not check evens if step is even
                    && (gcd(step, n1) == 1) ){  // gcd should be 1
                numOfCoPrimes++;
            }
        }
        return numOfCoPrimes;
    }

    // aforementioned core algorithm that computes independent parts in parallel
    private static long dotsPar(final int n) throws Exception {
        //defining the pool of threads depending on the current system configuration
        ExecutorService executor = newFixedThreadPool(getRuntime().availableProcessors());
        //preparing the collection to the define the tasks
        Collection<Callable<Integer>> tasks = new ArrayList<>(n - 2);
        for(int step = 2; step < n;  step++) {
            final int _step = step;
            //adding task to the list
            tasks.add(new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    return coPrimes(_step, n);
                }
            });
        }
        // submit the defined tasks to the pool with cores equals to processors number
        // and once all the tasks done collect the results and sum them together
        long finalResult = 0;
        for(Future<Integer> future : executor.invokeAll(tasks)) {
            finalResult += future.get();
        }
        executor.shutdown();
        return finalResult * 2 + 2 * n + 1;
    }

    private static int userInput(Scanner scanner, int min, int max) {
        int result = 0;
        while(result == 0) {
            try {
                result = scanner.nextInt();
                if(result < min || result > max) throw new IllegalArgumentException();
            } catch (Exception any) {
                out.println(format("Input is not integer or constraints violated [%s,%s]. Please try again", min, max));
                result = 0;
            }
        }
        return result;
    }

    private static int[] initializeTestCases(Scanner scanner, int numberOfTestCases) {
        out.println(format("Dimension should be within range [%s, %s]", MIN_N_DIMENSION, MAX_N_DIMENSION));
        final int[] inputs = new int[numberOfTestCases];
        int input = 0;
        while (input < numberOfTestCases) {
            out.println(format("Enter dimension for test case %s]", input + 1));
            inputs[input++] = userInput(scanner, MIN_N_DIMENSION, MAX_N_DIMENSION);
        }
        return inputs;
    }

}
