import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class EntityClassISSDataJsonTest {
    @Test
    public void testEntityClassISSData() {
        EntityClassISSDataJson entityClassISSDataJson = new EntityClassISSDataJson();
        EntityClassISSDataJson.IssPosition issPosition = entityClassISSDataJson.new IssPosition();

        issPosition.setLatitudeWithSerializedName("50.075538");
        issPosition.setLongitudeWithSerializedName("14.437800");

        assertEquals("14.437800", issPosition.getLongitudeWithSerializedName());
        assertEquals("50.075538", issPosition.getLatitudeWithSerializedName());
    }
}
