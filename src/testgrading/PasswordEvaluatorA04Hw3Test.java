package testgrading;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import guiTools.PasswordEvaluator;

/**
 * Boundary-oriented checks for {@link PasswordEvaluator} tied to
 * <b>OWASP A04:2025 Cryptographic Failures</b> (team assignment for Aryan Desai in HW3/Top Defects.pdf)
 * via password strength rules before storage (HW3 Task 2).
 *
 * <p>Assumption: "valid passwords" are those {@code evaluatePassword} accepts with an empty result
 * string, matching {@code InputValidatorTestRunner} expectations.</p>
 */
@DisplayName("PasswordEvaluator — A04 / boundary (HW3)")
class PasswordEvaluatorA04Hw3Test {

	@Test
	@DisplayName("Empty password is rejected (minimum length boundary)")
	void emptyPasswordRejected() {
		String msg = PasswordEvaluator.evaluatePassword("");
		assertFalse(msg.isEmpty());
	}

	@Test
	@DisplayName("Seven characters: below minimum length (boundary n-1)")
	void sevenCharsRejected() {
		String msg = PasswordEvaluator.evaluatePassword("Ab1!567");
		assertFalse(msg.isEmpty());
	}

	@Test
	@DisplayName("Eight characters with all classes: accepted (boundary n)")
	void eightCharsAccepted() {
		String msg = PasswordEvaluator.evaluatePassword("Ab1!5678");
		assertTrue(msg.isEmpty(), "Expected empty success string, got: " + msg);
	}

	@Test
	@DisplayName("Long password exceeds policy (same family as InputValidatorTestRunner case 8)")
	void longPasswordRejectedAsTooLong() {
		String msg = PasswordEvaluator.evaluatePassword("Ab1!5678Ab1!5678Ab1!5678Ab1!567");
		assertFalse(msg.isEmpty());
	}

	@Test
	@DisplayName("Missing uppercase letter fails coverage branch")
	void missingUppercaseFails() {
		String msg = PasswordEvaluator.evaluatePassword("ab1!5678");
		assertFalse(msg.isEmpty());
	}

	@Test
	@DisplayName("Invalid character fails early (single invalid token)")
	void invalidCharacterFails() {
		String msg = PasswordEvaluator.evaluatePassword("Ab1!567 ");
		assertTrue(msg.toLowerCase().contains("invalid") || msg.contains("Error"));
	}
}
