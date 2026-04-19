JUnit 5.10.2 and supporting JARs are vendored here so Eclipse can compile `module-info.java` with `requires org.junit.jupiter.api` without extra Maven setup.

If your team already uses the built-in "JUnit 5" library in Eclipse, you may remove these entries from .classpath and add the container instead — keep `requires org.junit.jupiter.api` in module-info.java.
