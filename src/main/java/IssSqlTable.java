import java.sql.*;

public class IssSqlTable {
    double oldLong;
    double oldLat;
    double actLong;
    double actLat;
    String oldTime;
    String actTime;

    int countOfRecordsLocationTable(String url, String username, String password, String nameOfTable) throws SQLException {
        int countOfRecords = 0;
        Connection connection = DriverManager.getConnection(url, username, password);
        Statement statement = connection.createStatement();

        String columnNameForRequest = null;
        if (nameOfTable.equals("ISS_data")) {
            columnNameForRequest = "time";
        } else {
            columnNameForRequest = "name";
        }

        ResultSet resultSet = statement.executeQuery("SELECT COUNT(" + columnNameForRequest + ") AS countRecords FROM " + nameOfTable + ";");
        if (resultSet.next()) {
            countOfRecords = resultSet.getInt("countRecords");
        }
        return countOfRecords;
    }

    void createIfNotExistsLocationTable(String url, String username, String password) throws SQLException {

        Connection connection = DriverManager.getConnection(url, username, password);
        Statement statement = connection.createStatement();
        String sqlQuery = "CREATE TABLE IF NOT EXISTS ISS_data (" +
                "longitude DOUBLE," +
                "latitude DOUBLE," +
                "message VARCHAR(12)," +
                "time TIMESTAMP PRIMARY KEY" +
                ")";
        statement.executeUpdate(sqlQuery);
    }

    static void deleteAllTables(String url, String username, String password) throws SQLException {
        Connection connection = DriverManager.getConnection(url, username, password);
        Statement statement = connection.createStatement();
        String[] tables = {"ISS_data", "Humans_data"};
        for (String table : tables) {
            statement.executeUpdate("DROP TABLE IF EXISTS " + table);
            System.out.println("Table " + table + " has been deleted");
        }
    }

    void printHumansTable(String url, String username, String password) throws SQLException {
        Connection connection = DriverManager.getConnection(url, username, password);
        Statement statement = connection.createStatement();
        String sqlQuery = "SELECT * FROM Humans_data";
        ResultSet resultSet = statement.executeQuery(sqlQuery);
        while ((resultSet.next())) {
            System.out.print("name: " + resultSet.getString("name"));
            System.out.println(" || craft: " + resultSet.getString("craft"));
        }
    }

    void speedISS(String url, String username, String password) throws SQLException {
        Connection connection = DriverManager.getConnection(url, username, password);
        Statement statement = connection.createStatement();
        String sqlQuery = "SELECT longitude, latitude, time FROM ISS_data ORDER BY time DESC LIMIT 1";
        ResultSet resultSet = statement.executeQuery(sqlQuery);
        while (resultSet.next()) {
            actLong = resultSet.getDouble("longitude");
            actLat = resultSet.getDouble("latitude");
            actTime = resultSet.getString("time");
        }

        sqlQuery = "SELECT longitude, latitude, time FROM ISS_data ORDER BY time LIMIT 1";
        resultSet = statement.executeQuery(sqlQuery);
        while (resultSet.next()) {
            oldLong = resultSet.getDouble("longitude");
            oldLat = resultSet.getDouble("latitude");
            oldTime = resultSet.getString("time");

        }
//        a = sin²(Δlat/2) + cos(lat1) * cos(lat2) * sin²(Δlon/2)
//        c = 2 * atan2(√a, √(1-a))
//        d = R * c

        double r = 6371; // Earth's radius in kilometers
        double radLatitude = Math.toRadians(actLat - oldLat);
        double radLongitude = Math.toRadians(actLong - oldLong);

        double a = Math.sin(radLatitude / 2) * Math.sin(radLatitude / 2)
                + Math.cos(Math.toRadians(oldLat)) * Math.cos(Math.toRadians(actLat))
                * Math.sin(radLongitude / 2) * Math.sin(radLongitude / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double result = r * c;

    }

    void printLocationTable(String url, String username, String password) throws SQLException {
        Connection connection = DriverManager.getConnection(url, username, password);
        Statement statement = connection.createStatement();
        String sqlQuery = "SELECT * FROM ISS_data";
        ResultSet resultSet = statement.executeQuery(sqlQuery);
        while (resultSet.next()) {
            System.out.print("longitude: " + resultSet.getString("longitude"));
            System.out.print("|| latitude: " + resultSet.getString("latitude"));
            System.out.print("|| message: " + resultSet.getString("message"));
            System.out.println("|| time: " + resultSet.getString("time"));
            System.out.println("----------------");
        }
    }

    void insertHumansTable(String url, String username, String password, TranslateIssRequestFromJson translateIssRequestFromJson) throws SQLException {
        Connection connection = DriverManager.getConnection(url, username, password);
        Statement statement = connection.createStatement();
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS Humans_data (" +
                "name VARCHAR(255)," +
                "craft VARCHAR(255)," +
                "PRIMARY KEY (name, craft)" +
                ")");
        statement.executeUpdate("DELETE FROM Humans_data");

        for (int i = 0; i < translateIssRequestFromJson.nameHumans.size(); i++) {
            String name = translateIssRequestFromJson.nameHumans.get(i);
            String craft = translateIssRequestFromJson.craftHumans.get(i);

            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Humans_data (name, craft) VALUES (?, ?)");
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, craft);
            preparedStatement.executeUpdate();
        }
        connection.close();
    }

    void insertLocationTable(String url, String username, String password, String longitude, String latitude, String message, String time) throws SQLException {
        Connection connection = DriverManager.getConnection(url, username, password);
        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO ISS_data (longitude, latitude, message, time) VALUES (?, ?, ?, ?)");
        preparedStatement.setString(1, longitude);
        preparedStatement.setString(2, latitude);
        preparedStatement.setString(3, message);
        preparedStatement.setString(4, time);

        int rowsAffected = preparedStatement.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("data loaded successfully");
        } else {
            System.err.println("data not loaded");
        }
    }
    void deleteRecordsFromHumansTable(String url, String username, String password, String name) throws SQLException {
        Connection connection = DriverManager.getConnection(url, username, password);
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM Humans_data WHERE name=?");
        preparedStatement.setString(1, name);

        int rowsAffected = preparedStatement.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("Data for " + name + " deleted successfully");
        } else {
            System.err.println("Data for " + name + " not found or not deleted");
        }

        connection.close();
    }
    //
}