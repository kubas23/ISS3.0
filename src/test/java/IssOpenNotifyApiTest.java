import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IssOpenNotifyApiTest {
    @Test
    void testIssOpenNotifyApi() throws IOException {
        IssOpenNotifyApi openNotifyApi = new IssOpenNotifyApi();
        String response = openNotifyApi.howManyHumans();

        // Ocekavany pocet astronautu
        assertEquals("4", response);
    }
}
