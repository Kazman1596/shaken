package org.example.dao;
import org.example.model.Account;

import org.example.exception.DaoException;
import org.example.model.RegisterUserDto;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcAccountDao implements AccountDao {
    //TODO: Ignoring all dates for now
    private final String ACCOUNT_SELECT_STRING = "SELECT account_id, first_name, last_name, email, profile_picture, bio, username, password FROM account ";
    private final JdbcTemplate jdbcTemplate;

    public JdbcAccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Account getAccountById(int userId) {
        Account account = null;
        String sql = ACCOUNT_SELECT_STRING + "WHERE account_id = ?";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
            if (results.next()) {
                account = mapRowToUser(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return account;
    }

    @Override
    public List<Account> getAccounts() {
        List<Account> accounts = new ArrayList<>();
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(ACCOUNT_SELECT_STRING);
            while (results.next()) {
                Account account = mapRowToUser(results);
                accounts.add(account);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return accounts;
    }

    public List<Account> searchAccounts(String input) {
        List<Account> accounts = new ArrayList<>();
        String wild = "%" + input + "%";
        String sql = ACCOUNT_SELECT_STRING
                + "WHERE first_name ILIKE ?\n" +
                "OR last_name ILIKE ?\n" +
                "OR username ILIKE ?;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, wild, wild, wild);
            while (results.next()) {
                Account account = mapRowToUser(results);
                accounts.add(account);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }

        return accounts;
    }

    @Override
    public Account getAccountByUsername(String username) {
        if (username == null) throw new IllegalArgumentException("Username cannot be null");
        Account account = null;
        String sql = ACCOUNT_SELECT_STRING + "WHERE username = ?;";
        try {
            SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, username);
            if (rowSet.next()) {
                account = mapRowToUser(rowSet);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return account;
    }

    @Override
    public Account createAccount(RegisterUserDto user) {
        Account newAccount = null;
        // create user
        String sql = "INSERT INTO account (first_name, last_name, email, username, password, date_added) VALUES (?, ?, ?, ?, ?, ?) RETURNING account_id";
        String password_hash = new BCryptPasswordEncoder().encode(user.getPassword());
        try {
            int newAccountId = jdbcTemplate.queryForObject(sql, int.class, user.getFirstName(), user.getLastName(), user.getEmail(), user.getUsername(), password_hash, user.getDateAdded());
            newAccount = getAccountById(newAccountId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return newAccount;
    }

    //TODO: Not returning date as JSON, but works
    private Account mapRowToUser(SqlRowSet rs) {
        Account account = new Account();
        account.setId(rs.getInt("account_id"));
        account.setFirstName(rs.getString("first_name"));
        account.setLastName(rs.getString("last_name"));
        account.setEmail(rs.getString("email"));
        account.setUsername(rs.getString("username"));
        account.setPassword(rs.getString("password"));
        account.setActivated(true);
        account.setAuthorities("USER");
        return account;
    }
}
