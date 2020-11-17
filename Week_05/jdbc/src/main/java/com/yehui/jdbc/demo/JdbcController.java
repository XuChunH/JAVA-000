package com.yehui.jdbc.demo;

import org.h2.Driver;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

/**
 * @author yehui
 * @date 2020/11/17
 */
@RestController
@RequestMapping("/jdbc")
public class JdbcController {

    //language=SQL
    private static final String CREATE_TABLE_SQL =
        "create table user\n" + "(\n" + "    id int not null IDENTITY PRIMARY KEY ,\n" + "    " + "name varchar(255) not null,\n"
            + "    age int not null \n" + ")";

    //language=SQL

    public static final String INSERT_SQL = "insert into user (name, age) values (? , ?)";
    //language=SQL

    public static final String UPDATE_SQL = "update user set age = ? where id < 10";

    //language=SQL

    public static final String READ_SQL = "select * from user where id < ?";

    //language=SQL

    public static final String DELETE_SQL = "delete from user where age > ?";

    private Connection connection;

    static {
        try {
            Class.forName(Driver.class.getName());

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Resource
    private DataSource dataSource;

    @PostConstruct
    public void postConstruct() throws SQLException {

        this.connection = DriverManager.getConnection("jdbc:h2:mem:test_mem", "sa", "sa");
        try (final PreparedStatement createTableStatement = connection.prepareStatement(CREATE_TABLE_SQL)) {
            createTableStatement.executeUpdate();
        }
    }

    @PreDestroy
    public void preDestroy() throws SQLException {

        if (Objects.nonNull(connection) && !connection.isClosed()) {
            connection.close();
        }
    }

    @RequestMapping("/curd")
    public String curd() throws SQLException {

        try (final PreparedStatement insertStatement = connection.prepareStatement(INSERT_SQL)) {
            insertStatement.setString(1, "name");
            insertStatement.setInt(2, 20);
            System.out.println("插入行数: " + insertStatement.executeUpdate());
        }

        try (final PreparedStatement updateStatement = connection.prepareStatement(UPDATE_SQL);) {
            updateStatement.setInt(1, 200);
            System.out.println("更新行数: " + updateStatement.executeUpdate());
        }

        try (final PreparedStatement readStatement = connection.prepareStatement(READ_SQL)) {
            readStatement.setInt(1, 20);
            try (final ResultSet resultSet = readStatement.executeQuery()) {
                while (resultSet.next()) {
                    final int id = resultSet.getInt(1);
                    final String name = resultSet.getString(2);
                    final int age = resultSet.getInt(3);
                    System.out.printf("id: %s, name: %s, age: %s%n", id, name, age);
                }
            }
        }

        try (final PreparedStatement deleteStatement = connection.prepareStatement(DELETE_SQL)) {
            deleteStatement.setInt(1, 20);
            System.out.println("删除行数: " + deleteStatement.executeUpdate());
        }
        return "ok";
    }

    @RequestMapping("/transaction")
    public String transaction() throws SQLException {

        try {
            connection.setAutoCommit(false);
            try (final PreparedStatement insertStatement = connection.prepareStatement(INSERT_SQL)) {
                insertStatement.setString(1, "name");
                insertStatement.setInt(2, 20);
                System.out.println("插入行数: " + insertStatement.executeUpdate());
            }

            try (final PreparedStatement updateStatement = connection.prepareStatement(UPDATE_SQL);) {
                updateStatement.setInt(1, 200);
                System.out.println("更新行数: " + updateStatement.executeUpdate());
            }

            try (final PreparedStatement readStatement = connection.prepareStatement(READ_SQL)) {
                readStatement.setInt(1, 20);
                try (final ResultSet resultSet = readStatement.executeQuery()) {
                    while (resultSet.next()) {
                        final int id = resultSet.getInt(1);
                        final String name = resultSet.getString(2);
                        final int age = resultSet.getInt(3);
                        System.out.printf("id: %s, name: %s, age: %s%n", id, name, age);
                    }
                }
            }

            try (final PreparedStatement deleteStatement = connection.prepareStatement(DELETE_SQL)) {
                deleteStatement.setInt(1, 20);
                System.out.println("删除行数: " + deleteStatement.executeUpdate());
            }
            connection.commit();
            return "ok";
        } finally {
            connection.setAutoCommit(true);
        }
    }

    @RequestMapping("/hikari")
    public String hikari() throws SQLException {

        final Connection connection = dataSource.getConnection();
        try {
            connection.setAutoCommit(false);
            try (final PreparedStatement insertStatement = connection.prepareStatement(INSERT_SQL)) {
                insertStatement.setString(1, "name");
                insertStatement.setInt(2, 20);
                System.out.println("插入行数: " + insertStatement.executeUpdate());
            }

            try (final PreparedStatement updateStatement = connection.prepareStatement(UPDATE_SQL);) {
                updateStatement.setInt(1, 200);
                System.out.println("更新行数: " + updateStatement.executeUpdate());
            }

            try (final PreparedStatement readStatement = connection.prepareStatement(READ_SQL)) {
                readStatement.setInt(1, 20);
                try (final ResultSet resultSet = readStatement.executeQuery()) {
                    while (resultSet.next()) {
                        final int id = resultSet.getInt(1);
                        final String name = resultSet.getString(2);
                        final int age = resultSet.getInt(3);
                        System.out.printf("id: %s, name: %s, age: %s%n", id, name, age);
                    }
                }
            }

            try (final PreparedStatement deleteStatement = connection.prepareStatement(DELETE_SQL)) {
                deleteStatement.setInt(1, 20);
                System.out.println("删除行数: " + deleteStatement.executeUpdate());
            }
            connection.commit();
            return "ok";
        } finally {
            connection.setAutoCommit(true);
        }
    }

}
