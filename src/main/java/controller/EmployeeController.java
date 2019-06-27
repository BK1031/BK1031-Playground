package controller;

import model.Employee;

import static spark.Spark.*;
import java.sql.Connection;
import java.sql.ResultSet;

public class EmployeeController {

    private Connection db;

    public EmployeeController(Connection db) {
        this.db = db;
        getAllEmployees();
    }

    private void getAllEmployees() {
        get("/api/employees", (req, res) -> {
            res.type("application/json");
            String returnString = "[";
            String sql = "SELECT * FROM EMPLOYEE";
            ResultSet rs = db.createStatement().executeQuery(sql);
            while(rs.next()) {
                int id  = rs.getInt("id");
                String first = rs.getString("FIRST_NAME");
                String last = rs.getString("LAST_NAME");
                String email = rs.getString("EMAIL");

                Employee employee = new Employee(id, first, last, email);
                System.out.println(employee);
                returnString += employee.toString();
                if (!rs.isLast()) {
                    returnString += ",";
                }
            }
            rs.close();
            returnString += "]";
            return returnString;
        });
    }

    private void addEmployee() {

    }

}
