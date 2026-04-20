package studentactivitytracker;

/**
 * Test class for StudentActivityTracker.
 * Runs simple manual tests for the HW3 prototype.
 */
public class StudentActivityTrackerTest {

    public static void main(String[] args) {

        System.out.println("Running StudentActivityTracker tests...\n");

        testTotalActivity(2, 3, 5, "Normal activity");
        testTotalActivity(0, 0, 0, "No activity");
        testTotalActivity(100, 200, 300, "High activity");
        testTotalActivity(-1, 5, 0, "Invalid negative posts");
        testTotalActivity(4, -2, 0, "Invalid negative replies");

        testHasActivity(2, 1, true, "Has activity");
        testHasActivity(0, 0, false, "No activity");

        System.out.println("\nAll tests finished.");
    }

    /**
     * Tests getTotalActivity and prints pass/fail result.
     */
    public static void testTotalActivity(int posts, int replies, int expected, String testName) {
        int actual = StudentActivityTracker.getTotalActivity(posts, replies);

        if (actual == expected) {
            System.out.println("[PASS] " + testName +
                    " | Input: (" + posts + ", " + replies + ")" +
                    " | Expected: " + expected +
                    " | Actual: " + actual);
        } else {
            System.out.println("[FAIL] " + testName +
                    " | Input: (" + posts + ", " + replies + ")" +
                    " | Expected: " + expected +
                    " | Actual: " + actual);
        }
    }

    /**
     * Tests hasActivity and prints pass/fail result.
     */
    public static void testHasActivity(int posts, int replies, boolean expected, String testName) {
        boolean actual = StudentActivityTracker.hasActivity(posts, replies);

        if (actual == expected) {
            System.out.println("[PASS] " + testName +
                    " | Input: (" + posts + ", " + replies + ")" +
                    " | Expected: " + expected +
                    " | Actual: " + actual);
        } else {
            System.out.println("[FAIL] " + testName +
                    " | Input: (" + posts + ", " + replies + ")" +
                    " | Expected: " + expected +
                    " | Actual: " + actual);
        }
    }
}
