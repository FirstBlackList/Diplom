package ru.netology.data;

import lombok.*;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class DbHelper {

    private static QueryRunner runner;
    private static Connection conn;

    private static ResultSetHandler<List<TableFields>> resultHandler = new BeanListHandler<>(TableFields.class);

    private static String url = System.getProperty("db.url");
    private static String user = System.getProperty("db.user");
    private static String password = System.getProperty("db.password");

    @SneakyThrows
    public static void setup() {
        runner = new QueryRunner();
        conn = DriverManager.getConnection((url), user, password);
    }

    @SneakyThrows
    public static void cleanDb() {
        setup();
        runner.update(conn, "DELETE FROM credit_request_entity;");
        runner.update(conn, "DELETE FROM payment_entity;");
        runner.update(conn, "DELETE FROM order_entity;");
    }

    @SneakyThrows
    public static List<TableFields> getCreditsRequest() {
        setup();
        var query = "SELECT * FROM credit_request_entity ORDER BY created DESC;";
        return runner.query(conn, query, resultHandler);
    }

    @SneakyThrows
    public static List<TableFields> getOrders() {
        setup();
        var query = "SELECT * FROM order_entity ORDER BY created DESC;";
        return runner.query(conn, query, resultHandler);
    }

    @SneakyThrows
    public static List<TableFields> getPayments() {
        setup();
        var query = "SELECT * FROM payment_entity ORDER BY created DESC;";
        return runner.query(conn, query, resultHandler);
    }

    public static String getPaymentStatus() {
        String statusSQL = "SELECT status FROM payment_entity";
        return getStatus(statusSQL);
    }

    public static String getCreditStatus() {
        String statusSQL = "SELECT status FROM credit_request_entity";
        return getStatus(statusSQL);
    }

    @SneakyThrows
    private static String getStatus(String query) {
        setup();
        return runner.query(conn, query, new ScalarHandler<>());
    }

}
