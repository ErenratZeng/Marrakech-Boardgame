package comp1110.ass2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.concurrent.TimeUnit;

import static comp1110.ass2.model.base.utils.subStr2int;

@Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
public class UtilsTest {
    @Test
    public void checkSubStr2int() {
        int n = 10000000;
        for (int i = 0; i <= n; i++) {
            String string = String.valueOf(n);
            int len = string.length();
            for (int j = len; j > 0; j--) {
                Assertions.assertEquals(subStr2int(string, 0, j), n);
                n /= 10;
            }
        }
    }

}
