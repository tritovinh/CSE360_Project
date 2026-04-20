package studentactivitytracker;

/**
 * Tracks student discussion activity for the HW3 prototype.
 * This prototype counts the total number of posts and replies
 * made by a student.
 */
public class StudentActivityTracker {

    /**
     * Returns the total activity for a student.
     *
     * @param posts number of posts made by the student
     * @param replies number of replies made by the student
     * @return total activity count, or 0 if input is invalid
     */
    public static int getTotalActivity(int posts, int replies) {
        if (posts < 0 || replies < 0) {
            return 0;
        }
        return posts + replies;
    }

    /**
     * Checks whether the student has any activity at all.
     *
     * @param posts number of posts
     * @param replies number of replies
     * @return true if total activity is greater than 0
     */
    public static boolean hasActivity(int posts, int replies) {
        return getTotalActivity(posts, replies) > 0;
    }
}
