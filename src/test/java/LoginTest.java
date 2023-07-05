import Base.KeywordEngine;
import org.testng.annotations.Test;

import java.io.IOException;

public class LoginTest {

    KeywordEngine keywordEngine = new KeywordEngine();
    @Test
    public void loginTest() throws IOException {
        keywordEngine.startEngine("login");
    }
}
