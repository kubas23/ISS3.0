import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class Main {
    public static void main(String[] args) throws IOException, SQLException {


        // loading configuration information from a file "application.properties"
        Properties properties = new Properties();
        FileInputStream input = new FileInputStream("src/main/resources/application.properties");
        properties.load(input); // method for loading information
        String url = properties.getProperty("db.url");
        String username = properties.getProperty("db.username");
        String password = properties.getProperty("db.password");

        //create instances of classes
        IssSqlTable instanceOfIssSqlTableClass = new IssSqlTable();
        TranslateIssRequestFromJson instanceOfTranslateIssRequestFromJsonClass = new TranslateIssRequestFromJson();

        instanceOfIssSqlTableClass.createIfNotExistsLocationTable(url, username, password); //create table if not exists
        instanceOfTranslateIssRequestFromJsonClass.translateLocation(); //download and translate from json location data

        HibernateRunner hibernateRunner = new HibernateRunner();
        hibernateRunner.insertLocationTable(
                instanceOfTranslateIssRequestFromJsonClass.longitudeLocation,
                instanceOfTranslateIssRequestFromJsonClass.latitudeLocation,
                instanceOfTranslateIssRequestFromJsonClass.messageLocation,
                instanceOfTranslateIssRequestFromJsonClass.timeLocation);

//        instanceOfIssSqlTableClass.insertLocationTable( //insert location data to sql location table
//                url,
//                username,
//                password,
//                instanceOfTranslateIssRequestFromJsonClass.longitudeLocation,
//                instanceOfTranslateIssRequestFromJsonClass.latitudeLocation,
//                instanceOfTranslateIssRequestFromJsonClass.messageLocation,
//                instanceOfTranslateIssRequestFromJsonClass.timeLocation);
        instanceOfIssSqlTableClass.printLocationTable(url, username, password); //print sql location table
        System.out.println(instanceOfIssSqlTableClass.countOfRecordsLocationTable(url, username, password, "ISS_data")+" records in Location Table");
        System.out.println();
        instanceOfIssSqlTableClass.speedISS(url, username, password);

//        instanceOfTranslateIssRequestFromJsonClass.translateHumans(); //download and translate from json location data
//        //create (or clean old humans table), insert data\/
//        instanceOfIssSqlTableClass.insertHumansTable(url, username, password, instanceOfTranslateIssRequestFromJsonClass);
//        instanceOfIssSqlTableClass.printHumansTable(url, username, password); //print sql location table
//        System.out.println(instanceOfIssSqlTableClass.countOfRecordsLocationTable(url, username, password, "Humans_data")+" records in Humans Table");


//        IssSqlTable.deleteAllTables(url, username, password); // delete all tables
    }
}