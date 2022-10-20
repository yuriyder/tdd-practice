package stack;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StackTest {

    private LLStack sut;

    @BeforeEach
    void setUp() {
        sut = new LLStack();
    }

    @Test
    public void newStackIsEmptyTest() {
        boolean result = sut.isEmpty();

        assertTrue(result);
    }

    @Test
    public void afterOnePushIsNotEmptyTest() {
        sut.push(42);

        boolean result = sut.isEmpty();

        assertFalse(result);
    }

    @Test
    public void afterOnePushOnePopIsEmptyTest() {
        sut.push(42);
        sut.pop();

        boolean result = sut.isEmpty();

        assertTrue(result);
    }

    @Test
    public void afterTwoPushesOnePopIsNotEmptyTest() {
        sut.push(43);
        sut.push(14);
        sut.pop();

        assertFalse(sut.isEmpty());
    }

    @Test
    public void pushXPopXTest() {
        sut.push(42);

        int result = sut.pop();

        assertEquals(42, result);
    }

    @Test
    public void pushXYPopYXTest() {
        sut.push(42);
        sut.push(13);

        int first = sut.pop();
        int second = sut.pop();

        assertEquals(13, first);
        assertEquals(42, second);
    }
}
