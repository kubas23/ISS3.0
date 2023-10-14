import java.util.ArrayList;
import java.util.List;

public class EntityClassHowManyHumans {

    private String message;
    private int number;
    private List<HumanCraft> people = new ArrayList<>();

    public List<HumanCraft> getPeople() {
        return people;
    }

    public String getMessage() {
        return message;
    }

    public int getNumber() {
        return number;
    }

    public static class HumanCraft {
        private String name;
        private String craft;

        public String getName() {
            return name;
        }
        public String getCraft() {
            return craft;
        }
    }
}
