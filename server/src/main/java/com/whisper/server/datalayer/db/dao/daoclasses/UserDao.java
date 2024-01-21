package com.whisper.server.datalayer.db.dao.daoclasses;

import com.whisper.server.datalayer.db.MyDatabase;
import com.whisper.server.datalayer.db.dao.Dao;
import com.whisper.server.datalayer.db.dao.DaoInterface;
import com.whisper.server.datalayer.db.dao.daointerfaces.UserDaoInterface;
import com.whisper.server.model.User;
import com.whisper.server.model.enums.Gender;
import com.whisper.server.model.enums.Mode;
import com.whisper.server.model.enums.Status;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserDao implements UserDaoInterface {
    private MyDatabase myDatabase = null;
    private static UserDaoInterface instance = null;

    private UserDao(MyDatabase myDatabase) {
        this.myDatabase = myDatabase;
    }
    public synchronized static UserDaoInterface getInstance(MyDatabase myDatabase) {
        if (instance == null) {
            instance = new UserDao(myDatabase);
        }
        return instance;
    }

    // Creating a user
    public int createUser(User object) throws SQLException {
        String query = "INSERT INTO user (phone_number, password, email, user_name" +
                ", gender, date_of_birth, country, bio, mode, status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = myDatabase.getConnection().prepareStatement(query)) {
            ps.setString(1, object.getPhoneNumber());
            ps.setString(2, object.getPassword());
            ps.setString(3, object.getEmail());
            ps.setString(4, object.getUserName());
            ps.setString(5, object.getGender().toString());
            ps.setDate(6, object.getDateOfBirth());
            ps.setString(7, object.getCountry());
            ps.setString(8, object.getBio());
            ps.setString(9, object.getMode().toString());
            ps.setString(10, object.getStatus().toString());

            return ps.executeUpdate();
        }
    }

    // Getting a user by id
    public User getUserById(int id) throws SQLException {
        String query = "SELECT * FROM user WHERE id = ?";
        User user = null;

        try (PreparedStatement ps = myDatabase.getConnection().prepareStatement(query)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                user = new User();
                user.setPhoneNumber(rs.getString("phone_number"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setUserName(rs.getString("user_name"));
                user.setGender(Gender.valueOf(rs.getString("gender")));
                user.setDateOfBirth(rs.getDate("date_of_birth"));
                user.setCountry(rs.getString("country"));
                user.setBio(rs.getString("bio"));
                user.setMode(Mode.valueOf(rs.getString("mode")));
                user.setStatus(Status.valueOf(rs.getString("status")));
            }
        }
        return user;
    }


    // Updating a user using the user object
    public int updateUser(User user) throws SQLException {
        String query = "UPDATE user SET phone_number = ?, password = ?, email = ?," +
                " user_name = ?, gender = ?, date_of_birth = ?, country = ?, bio = ?," +
                " mode = ?, status = ? WHERE id = ?";
        try (PreparedStatement ps = myDatabase.getConnection().prepareStatement(query)) {
            ps.setString(1, user.getPhoneNumber());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getUserName());
            ps.setString(5, user.getGender().toString());
            ps.setDate(6, user.getDateOfBirth());
            ps.setString(7, user.getCountry());
            ps.setString(8, user.getBio());
            ps.setString(9, user.getMode().toString());
            ps.setString(10, user.getStatus().toString());
            ps.setInt(11, user.getUserId());

            return ps.executeUpdate();
        }
    }

    // Deleting a user by id
    public int deleteById(int id) throws SQLException {
        String query = "DELETE FROM user WHERE id = ?";

        try (PreparedStatement ps = myDatabase.getConnection().prepareStatement(query)) {
            ps.setInt(1, id);
            return ps.executeUpdate();
        }
    }

    // Getting all users
    public List<User> getAll() throws SQLException {
        String query = "SELECT * FROM user";
        List<User> users = new ArrayList<>();

        try (PreparedStatement ps = myDatabase.getConnection().prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                User user = new User();
                user.setPhoneNumber(rs.getString("phone_number"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setUserName(rs.getString("user_name"));
                user.setGender(Gender.valueOf(rs.getString("gender")));
                user.setDateOfBirth(rs.getDate("date_of_birth"));
                user.setCountry(rs.getString("country"));
                user.setBio(rs.getString("bio"));
                user.setMode(Mode.valueOf(rs.getString("mode")));
                user.setStatus(Status.valueOf(rs.getString("status")));
                users.add(user);
            }
        }
        return users;
    }
}
