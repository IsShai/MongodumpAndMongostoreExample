import com.github.javafaker.Faker;
import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

public class main {

    public static void main(String[] args) {
        System.out.println("inside main");

        //ADD DATA TO DATABASE - ONLY FOR FIRST START
       // addDataToMongo();

        // STORE AND DUMP
//        mongostoreData(); // RESTORE THE DATA
        mongodumpData(); // BACK UP THE DATA
    }

    private static void mongostoreData() {
        System.out.println("inside mongostoreData");

        String pathToCommand = "C:\\Program Files\\MongoDB\\Server\\4.2\\bin\\mongorestore.exe";
        String host = "Cluster0-shard-0/cluster0-shard-00-00-c8nol.gcp.mongodb.net:27017,cluster0-shard-00-01-c8nol.gcp.mongodb.net:27017,cluster0-shard-00-02-c8nol.gcp.mongodb.net:27017";
        String username = "admin";
        String password = "password";
        String pathTotheExportFile = "C:\\Users\\Shai\\Desktop\\dump";


        try {
            String commandImportWith = pathToCommand + " --gzip --host " + host + " --ssl --username " + username + " --password " + password + " --authenticationDatabase admin " + pathTotheExportFile;
            Runtime.getRuntime().exec(commandImportWith);


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void mongodumpData() {
        System.out.println("inside mongodumpData");

        String pathToCommand = "C:\\Program Files\\MongoDB\\Server\\4.2\\bin\\mongodump.exe";
        String host = "Cluster0-shard-0/cluster0-shard-00-00-c8nol.gcp.mongodb.net:27017,cluster0-shard-00-01-c8nol.gcp.mongodb.net:27017,cluster0-shard-00-02-c8nol.gcp.mongodb.net:27017";
        String username = "admin";
        String password = "password";
        String database = "first";
        String collection = "users";
        String outputPath = "C:\\Users\\Shai\\Desktop\\dump";
        String queryKey = "\\\"firstname\\\"";
        String queryVal = "\\\"John\\\"";
        String query = "\"{" + queryKey + ":" + queryVal + "}\"";

        try {
            String commandExport = pathToCommand + " --gzip --host " + host + " --ssl --username " + username + " --password " + password + " --authenticationDatabase admin --db " + database + " --collection " + collection + " -q " + query + " --out " + outputPath;
            Runtime.getRuntime().exec(commandExport);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void addDataToMongo() {
        String uri = "mongodb+srv://admin:admin@cluster0-c8nol.gcp.mongodb.net/test?retryWrites=true&w=majority";

        MongoClientURI clientURI = new MongoClientURI(uri);
        MongoClient mongoClient = new MongoClient(clientURI);

        MongoDatabase mongoDatabase = mongoClient.getDatabase("first");
        MongoCollection collection = mongoDatabase.getCollection("users");

        System.out.println("Database Connected");

        for (int i = 0; i < 1000; i++) {
            String name = getName();
            String lastname = getLastname();
            System.out.println("I IS : ---------------------------------------------------------" + i + "name is : -- " + name + "  " + lastname);
            Document doc = new Document("firstname", getName())
                    .append("lastname", getLastname())
                    .append("id", UUID.randomUUID().toString())
                    .append("versions", Arrays.asList("v3.2", "v3.0", "v2.6"))
                    .append("info", new Document("x", 203).append("y", 102));

            collection.insertOne(doc);
        }
    }

    private static String getName() {
        Faker faker = new Faker();
        String firstName = faker.name().firstName();
        return firstName;
    }

    private static String getLastname() {
        Faker faker = new Faker();
        String lastName = faker.name().lastName();
        return lastName;

    }

}
