/**
 * Module contains the JavaFX application for TP1.
 */
module FoundationsF25 {
	requires javafx.controls;
	requires java.sql;
	requires org.junit.jupiter.api;

	opens applicationMain to javafx.graphics, javafx.fxml;
}
