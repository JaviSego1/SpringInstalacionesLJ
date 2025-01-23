package com.iesvdc.acceso.pistasdeportivas.servicios;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class ServicioDetalleUsuario implements UserDetailsService {

    @Autowired
    private DataSource dataSource;
    

    @Override
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT username, password, enabled FROM usuario WHERE username = ?")) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return User.builder()
                        .username(rs.getString("username"))
                        .password(rs.getString("password"))
                        .roles(getUserRole(username))
                        .build();
            } else {
                throw new UsernameNotFoundException("User not found");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String getUserRole(String username) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT tipo as 'ROLE' FROM usuario WHERE username = ?")) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("tipo");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return "USER";
    }
}
