

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
public class FirstTest {
    @Test
    public void isTrue() {
        assertThat(true, is(true));
    }
}