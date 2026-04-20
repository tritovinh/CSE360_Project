/**
 * Module contains the JavaFX application for TP3.
 */
module FoundationsF25 {
	requires javafx.controls;
	requires javafx.fxml;
	requires java.sql;
	requires com.h2database;
	requires org.junit.jupiter.api;

	opens applicationMain to javafx.graphics, javafx.fxml;
}
