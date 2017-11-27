package database.dao;

import database.dao.abstractDao.AbstractDao;
import utils.DatabaseUtils.DbWrapper;
import exceptions.DbException;
import database.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class UserDao extends AbstractDao<User> {

    private final String TABLE_NAME = "public.users";

    private final String COL_ID = "user_id";

    private final String COL_EMAIL = "email";

    private final String COL_PASSWORD = "password";

    private final String COL_USERNAME = "username";

    private final String COL_COUNTRY_ID = "country_id";

    private final String COL_GENDER = "gender";

    private final String COL_MARK = "mark";

    private final String COL_RIGHTS = "user_right";

    private final String COL_COOKIE_ID = "cookie_id";

    public UserDao(Connection connection) {
        super(connection);
    }

    @Override
    public String getSelectQuery() {
        return "SELECT "
                + COL_ID + ","
                + COL_EMAIL + ","
                + COL_USERNAME + ","
                + COL_COUNTRY_ID + ","
                + COL_GENDER + ","
                + COL_MARK + ","
                + COL_RIGHTS + ","
                + COL_COOKIE_ID
                + " FROM " + TABLE_NAME;
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO " + TABLE_NAME + " ("
                + COL_EMAIL + ","
                + COL_PASSWORD + ","
                + COL_USERNAME + ","
                + COL_COUNTRY_ID + ","
                + COL_GENDER + ","
                + COL_COOKIE_ID
                + ") VALUES (?, ?, ?, ?, ?, ?);";
    }

    @Override
    public String getReadQuery(String id) {
        return getSelectQuery() + " WHERE " + COL_ID + " = " + id + ";";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE " + TABLE_NAME + " SET "
                + COL_USERNAME + " = ?, "
                + COL_COUNTRY_ID + " = ?, "
                + COL_GENDER + " =  ?, "
                + COL_MARK + " =  ?, "
                + COL_RIGHTS + " = ? "
                + " WHERE " + COL_ID + " = ?;";
    }

    @Override
    public String getDeleteQuery() {

        return "DELETE FROM " + TABLE_NAME + " WHERE " + COL_ID + " = ?;";
    }

    public String getSelectByCookieId(){
        return getSelectQuery() +  " WHERE " + COL_COOKIE_ID + " = ?;" ;
    }

    public String getSelectByEmail() {
        return getSelectQuery() + " WHERE " + COL_EMAIL + " = ?;";
    }

    public String getSelectCookieIds(){
        return "SELECT " + COL_COOKIE_ID + " FROM " + TABLE_NAME +";" ;
    }

    public String getSelectPassword(String email){
        return "SELECT " + COL_PASSWORD + " FROM " + TABLE_NAME
                + " WHERE " + COL_EMAIL + " = ?;";
    }


    public String getSelectEmails(){
        return "SELECT " + COL_EMAIL + " FROM " + TABLE_NAME +";" ;
    }

    @Override
    protected List<User> parseResultSet(ResultSet rs) throws DbException {
        LinkedList<User> result = new LinkedList<>();
        try {
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt(COL_ID));
                user.setEmail(rs.getString(COL_EMAIL));
                user.setUsername(rs.getString(COL_USERNAME));
                user.setCountry(rs.getInt(COL_COUNTRY_ID));
                user.setGender(rs.getString(COL_GENDER));
                user.setMark(rs.getInt(COL_MARK));
                user.setRights(rs.getInt(COL_RIGHTS));
                user.setCookieId(rs.getString(COL_COOKIE_ID));
                result.add(user);
            }
        } catch (Exception e) {
            throw new DbException(e);
        }
        return result;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, User user) throws DbException {
        try {
            int i = 1;
            statement.setString(i++, user.getEmail());
            statement.setString(i++, user.getPassword());
            statement.setString(i++, user.getUsername());
            statement.setInt(i++, user.getCountry().getId());
            statement.setString(i++, user.getGender());
            statement.setString(i, user.getCookieId());
        } catch (Exception e) {
            throw new DbException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, User user) throws DbException {
        try {
            int i = 1;
            statement.setString(i++, user.getUsername());
            statement.setInt(i++, user.getCountry().getId());
            statement.setString(i++, user.getGender());
            statement.setInt(i++, user.getMark());
            statement.setInt(i++, user.getRights().getId());
            statement.setInt(i, user.getId());
        } catch (Exception e) {
            throw new DbException(e);
        }
    }


    public List<String> findAllCookiesId() throws DbException {
        List<String> list = new ArrayList<>();
        String sql = getSelectCookieIds();
        try (PreparedStatement statement = DbWrapper.getConnection().prepareStatement(sql)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                list.add(rs.getString(COL_COOKIE_ID));
            }
        } catch (Exception e) {
            throw new DbException(e);
        }
        return list;
    }

    public List<String> findAllEmails() throws DbException {
        List<String> list = new ArrayList<>();
        String sql = getSelectEmails();
        try (PreparedStatement statement = DbWrapper.getConnection().prepareStatement(sql)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                list.add(rs.getString(COL_EMAIL));
            }
        } catch (Exception e) {
            throw new DbException(e);
        }
        return list;
    }



    public User findByCookieId(String cookieId) throws DbException {
        String sql = getSelectByCookieId();
        User user;
        try (PreparedStatement statement = DbWrapper.getConnection().prepareStatement(sql)) {
            statement.setString(1,cookieId);
            ResultSet rs = statement.executeQuery();
            List<User> list = parseResultSet(rs);
            if ((list == null) || (list.size() != 1)) {
                throw new DbException("Exception on findByCookieId.");
            }
            user = list.iterator().next();
        } catch (Exception e) {
            throw new DbException(e);
        }
        return user;
    }

    public User findByEmail(String email) throws DbException {
        String sql = getSelectByEmail();
        User user;
        try (PreparedStatement statement = DbWrapper.getConnection().prepareStatement(sql)) {
            statement.setString(1,email);
            ResultSet rs = statement.executeQuery();
            List<User> list = parseResultSet(rs);
            if ((list == null) || (list.size() != 1)) {
                throw new DbException("Exception on findByCookieId.");
            }
            user = list.iterator().next();
        } catch (Exception e) {
            throw new DbException(e);
        }
        return user;
    }

    public String findPassword(String email) throws DbException {
        String password = null;
        String sql = getSelectPassword(email);
        try (PreparedStatement statement = DbWrapper.getConnection().prepareStatement(sql)) {
            statement.setString(1,email);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                password = rs.getString(COL_PASSWORD);
            }
        } catch (Exception e) {
            throw new DbException(e);
        }
        return password;
    }
}
