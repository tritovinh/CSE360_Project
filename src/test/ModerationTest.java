package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.SQLException;

import database.Database;
import entityClasses.User;

/**
 * Test class for the instructional team moderation features, specifically 
 * testing the TA approve replies backend functionality
 */
public class ModerationTest {

    private Database db;

    /**
     * Sets up a fresh Database instance and connects to it before each test 
     * to ensure test isolation.
     */
    @BeforeEach
    public void setUp() {
        db = new Database(); 
        try {
            // This initializes H2 and creates the tables
            db.connectToDatabase();
        } catch (SQLException e) {
            fail("Database connection failed: " + e.getMessage());
        }
    }

    /**
     * Test Case 1: Backend Database Update (Positive Test)
     * Tests the "happy path" where an authorized Admin approves a valid reply.
     */
    @Test
    public void testApproveReply_Positive() {
        try {
            setupDummyAdmin(); // Log in as an authorized Admin (admin role)

            int validPostId = 5;
            db.createDummyReply(validPostId);

            boolean result = db.approveReply(validPostId);
            
            assertTrue(result, "Approving a valid post ID by an Admin should return true.");
            
            // To fully satisfy "Database State Verification", you will eventually want to query 
            // the database here to assert that is_TA_approved is exactly TRUE for ID 5.

        } catch (SQLException e) {
            fail("Exception should not be thrown for valid approval: " + e.getMessage());
        }
    }

    /**
     * Test Case 2: Negative Test (Invalid ID Boundary)
     * Tests that passing an impossible negative ID is handled gracefully 
     * and returns false, without crashing the application.
     */
    @Test
    public void testApproveReply_InvalidId_Negative() {
        try {
            setupDummyAdmin();
            
            int invalidPostId = -999;
            boolean result = db.approveReply(invalidPostId);

            assertFalse(result, "Approving a negative/invalid post ID should return false.");

        } catch (SQLException e) {
            fail("Exception should not be thrown for invalid ID: " + e.getMessage());
        }
    }

    /**
     * Test Case 3: Valid Format but Non-Existent ID (Negative Test)
     * Tests that an ID formatted correctly but absent from the database is handled gracefully.
     */
    @Test
    public void testApproveReply_NonExistentId() {
        try {
            setupDummyAdmin();
            
            int nonExistentPostId = 9999999;
            boolean result = db.approveReply(nonExistentPostId);

            assertFalse(result, "Approving a non-existent post ID should return false.");

        } catch (SQLException e) {
            fail("Exception should not be thrown for non-existent ID: " + e.getMessage());
        }
    }
    
    /**
     * Test Case 4: Broken Access Control / Unauthorized Use (Negative Test)
     * Tests that a user authorized only as a Student (Role 1) is blocked 
     * from approving a post.
     */
    @Test
    public void testApproveReply_Unauthorized() {
        try {
            setupDummyStudent(); // Log in as a standard Student (Role 1)
            
            int validPostId = 5;
            boolean result = db.approveReply(validPostId);
            
            assertFalse(result, "Unauthorized Student users should not be able to approve a reply.");
            
        } catch (SQLException e) {
            fail("Exception should not be thrown for unauthorized access: " + e.getMessage());
        }
    }
    
    /**
     * Helper method to bypass login screens and mock an Admin session for backend testing.
     */
    private void setupDummyAdmin() throws SQLException {
        // AdminRole is true, Role1 is false, Role2 is false
        User admin = new User("testAdmin", "Pass123!", "First", "M", "Last", "Pref", "admin@test.com", true, false, false);
        if (!db.doesUserExist("testAdmin")) {
            db.register(admin);
        }
        db.loginAdmin(admin); // Authenticate the session
        db.getUserAccountDetails("testAdmin"); 
    }

    /**
     * Helper method to bypass login screens and mock a Student session for backend testing.
     */
    private void setupDummyStudent() throws SQLException {
        // AdminRole is false, Role1 is true (Student), Role2 is false
        User student = new User("testStudent", "Pass123!", "First", "M", "Last", "Pref", "student@test.com", false, true, false);
        if (!db.doesUserExist("testStudent")) {
            db.register(student);
        }
        db.loginRole1(student); // Authenticate the session
        db.getUserAccountDetails("testStudent"); 
    }
}