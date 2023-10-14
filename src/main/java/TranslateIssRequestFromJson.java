import com.google.gson.Gson;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TranslateIssRequestFromJson {
    IssOpenNotifyApi issOpenNotifyApi = new IssOpenNotifyApi();
    Gson gson = new Gson();
    String timeLocation = null;
    String longitudeLocation = null;
    String latitudeLocation = null;
    String messageLocation = null;

    String messageHumans = null;
    int numbersHumans = 0;
    List<String> nameHumans = new ArrayList<>();
    List<String> craftHumans = new ArrayList<>();

    void translateLocation() throws IOException {

        EntityClassISSDataJson issData = gson.fromJson(issOpenNotifyApi.whereIsNowIssRequest(), EntityClassISSDataJson.class);
        EntityClassISSDataJson.IssPosition issPosition = issData.getIss_position();

        longitudeLocation = issPosition.getLongitudeWithSerializedName();
        latitudeLocation = issPosition.getLatitudeWithSerializedName();
        messageLocation = issData.getMessage();
        long timestamp = issData.getTimestamp();

        Date date = new Date(timestamp * 1000);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        timeLocation = dateFormat.format(date);
    }
    void translateHumans() throws IOException {

        EntityClassHowManyHumans humansData = gson.fromJson(issOpenNotifyApi.howManyHumans(), EntityClassHowManyHumans.class);
        List<EntityClassHowManyHumans.HumanCraft> humanCrafts = humansData.getPeople();
        nameHumans.clear();
        craftHumans.clear();
        for (EntityClassHowManyHumans.HumanCraft humanCraft : humanCrafts) {
             nameHumans.add(humanCraft.getName()) ;
             craftHumans.add(humanCraft.getCraft());
        }
        messageHumans = humansData.getMessage();
        numbersHumans = humansData.getNumber();
    }
}
