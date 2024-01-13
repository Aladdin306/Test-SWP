/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import entity.Account;
import dal.DBContext;
import entity.BaseEntity;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author leduy
 */
public class AccountDBContext extends DBContext {

    public Account ValidateAccount(String usename, String password) {
        String sql = "Select * from Account where username = ? and password = ?";
        Account acc = null;
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, usename);
            stm.setString(2, password);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                acc = new Account(
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getInt("role_id")
                );
                return acc;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return acc;
    }

    public ArrayList<Account> getAccountIdByUsername(String username) {
        ArrayList<Account> getId = new ArrayList<>();
        String sql = "SELECT s.student_id, i.instructor_id FROM Account acc "
                + "LEFT JOIN Student s ON s.username = acc.username "
                + "LEFT JOIN Instructor i ON i.username = acc.username "
                + "WHERE acc.username=?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, username);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                Account acc = new Account(
                        rs.getString("student_id"),
                        rs.getString("instructor_id")
                );
                getId.add(acc);
            }
        } catch (SQLException e) {
            System.out.println(e); // Handle exceptions appropriately in a real application
        }
        return getId;
    }

    public static void main(String[] args) {
        AccountDBContext da = new AccountDBContext();
        System.out.println(da.ValidateAccount("a1", "123"));
    }

    @Override
    public ArrayList list() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void insert(BaseEntity entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update(BaseEntity entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(BaseEntity entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public BaseEntity get(BaseEntity entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
