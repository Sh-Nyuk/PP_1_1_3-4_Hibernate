package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
//    Connection conn;

    public UserDaoJDBCImpl() {
//        conn = Util.getConn();
    }

    public void createUsersTable() {
        try(Connection connection = Util.getConn()) {
            String command = "CREATE TABLE IF NOT EXISTS users (id BIGINT AUTO_INCREMENT PRIMARY KEY," +
                    " name VARCHAR(255), lastname VARCHAR(255), age TINYINT)";
            Statement statement = connection.createStatement();
            statement.execute(command);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        try(Connection connection = Util.getConn()) {
            String command = "DROP TABLE IF EXISTS users";
            Statement statement = connection.createStatement();
            statement.execute(command);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try(Connection connection = Util.getConn()) {
            String command = "INSERT INTO users (name, lastname, age) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(command);
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        try(Connection connection = Util.getConn()) {
            String command = "DELETE FROM users WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(command);
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try(Connection connection = Util.getConn()) {
            String command = "SELECT * FROM users";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(command);

            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String lastName = resultSet.getString("lastname");
                byte age = resultSet.getByte("age");
                User tempUser = new User(name, lastName, age);
                tempUser.setId(id);
                users.add(tempUser);
            }
            return users;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void cleanUsersTable() {
        try(Connection connection = Util.getConn()) {
            String command = "DELETE FROM users";
            Statement statement = connection.createStatement();
            statement.execute(command);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
