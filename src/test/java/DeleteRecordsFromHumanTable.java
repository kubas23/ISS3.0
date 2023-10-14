import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

public class DeleteRecordsFromHumanTable {
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/your_database";
    private static final String DATABASE_USERNAME = "your_username";
    private static final String DATABASE_PASSWORD = "your_password";

    private IssSqlTable sqlTable;

    @BeforeEach
    public void setup() {
        sqlTable = new IssSqlTable();
    }

    @Test
    public void testDeleteRecordsFromHumanTable() throws SQLException {
        // Vytvoreni testovaciho zaznamu v tabulce Humans_data
        String testName = "TestUser";
        String testCraft = "TestCraft";
        sqlTable.insertHumansTable(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD, testName, testCraft);

        // Kontrola, ze byl testovaci zaznam vytvoren
        int initialCount = sqlTable.countOfRecordsLocationTable(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD, "Humans_data");
        assertEquals(1, initialCount);

        // Odstraneni testovaciho zaznamu
        sqlTable.deleteRecordsFromHumansTable(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD, testName);

        // Zkontroluje, ze zaznam byl smazan
        int afterDeleteCount = sqlTable.countOfRecordsLocationTable(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD, "Humans_data");
        assertEquals(0, afterDeleteCount);
    }

}

