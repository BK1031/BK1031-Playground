import controller.EmployeeController;
import org.apache.ibatis.jdbc.ScriptRunner;
import spark.Request;
import spark.Response;
import spark.Route;
import sun.font.ScriptRun;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import static spark.Spark.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;

public class Application {

    private final String url = "jdbc:postgresql://localhost:5432/BK1031-Playground";
    private final String user = "bharat";
    private final String password = "";
    private final static String initPath = "/src/main/java/resources/init.sql";

    public static void main(String[] args) throws SQLException {
        // Start Spark Webserver
        init();
        // Connect to Postgres DB
        Application app = new Application();
        Connection db = app.connect();
        // Initialize DB
        String basePath = new File("").getAbsolutePath();
        try {
            // Initialize object for ScripRunner
            ScriptRunner sr = new ScriptRunner(db);
            // Give the input file to Reader
            Reader reader = new BufferedReader(new FileReader(basePath + initPath));
            // Exctute script
            sr.runScript(reader);

        } catch (Exception e) {
            System.err.println("Failed to Execute " + initPath
                    + "\nERROR: " + e.getMessage());
        }
        // Initialize Object Controllers
        EmployeeController employeeController = new EmployeeController(db);
        get("/api/test", (req, res) -> "Hello World");
    }

    public Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the PostgreSQL server successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

}
