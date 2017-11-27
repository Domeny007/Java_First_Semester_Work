package database.dao;

import database.dao.abstractDao.AbstractDao;
import database.entity.Director;
import exceptions.DbException;
import utils.DatabaseUtils.DbWrapper;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class DirectorDao extends AbstractDao<Director> {

    private final String TABLE_NAME = "public.directors";

    private final String COL_ID = "director_id";

    private final String COL_NAME = "name";

    private final String COL_SURNAME = "surname";

    private final String COL_BIRTHDAY = "birthday";

    private final String COL_MOTHERLAND = "motherland";

    private final String COL_PHOTO_ADDRESS = "photo";

    private final String COL_MARK = "mark";

    private final String COL_SUBSCRIPTION = "subscription";

    private final String MARK_BIND_TABLE = "directors_users_votes_bind";
    private final String COL_USER_ID = "user_id";

    public DirectorDao(Connection connection) {
        super(connection);
    }

    @Override
    public String getSelectQuery() {
        return "SELECT "
                + COL_ID + ","
                + COL_NAME + ","
                + COL_SURNAME + ","
                + COL_BIRTHDAY + ","
                + COL_MOTHERLAND + ","
                + COL_PHOTO_ADDRESS + ","
                + COL_MARK + ","
                + COL_SUBSCRIPTION
                + " FROM " + TABLE_NAME;
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO " + TABLE_NAME + " ("
                + COL_NAME + ","
                + COL_SURNAME + ","
                + COL_BIRTHDAY + ","
                + COL_MOTHERLAND + ","
                + COL_PHOTO_ADDRESS + ","
                + COL_SUBSCRIPTION
                + ") VALUES (?, ?, ?, ?, ?, ?);";
    }

    @Override
    public String getReadQuery(String id) {

        return getSelectQuery() + " WHERE " + COL_ID + " = " + id + ";";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE " + TABLE_NAME + " SET "
                + COL_NAME + " = ?, "
                + COL_SURNAME + " = ?, "
                + COL_BIRTHDAY + " =  ?, "
                + COL_MOTHERLAND + " = ?, "
                + COL_PHOTO_ADDRESS + " =  ?, "
                + COL_MARK + " =  ?, "
                + COL_SUBSCRIPTION + " =  ? "
                + " WHERE " + COL_ID + " = ?;";
    }

    @Override
    public String getDeleteQuery() {

        return "DELETE FROM " + TABLE_NAME + " WHERE " + COL_ID + " = ?;";
    }

    private String getSelectDirectorsByName(String queryDirector,Integer numPage){
        queryDirector = queryDirector.toLowerCase();
        return getSelectQuery() + " WHERE LOWER(" + COL_NAME + ") SIMILAR TO " + "'" +  queryDirector + "%'"
                + " ORDER BY " + COL_NAME + " DESC," + COL_ID + " DESC"
                + " LIMIT " + 5 + " OFFSET " + numPage*5 + " ;";
    }

    public String getSelectMostPopular(Integer numPage) {
        return getSelectQuery() + " ORDER BY " + COL_MARK + " DESC," + COL_ID + " DESC"
                + " LIMIT " + 5 + " OFFSET " + numPage*5 + " ;";
    }

    private String getSelectDirectorsByMovie(Integer movieId){
        return "SELECT * FROM getmoviedirectors(" + movieId + ")";
    }

    private String getCreateMarkQuery(Integer movieId,Integer userId,Integer mark){

        return "INSERT INTO "
                + MARK_BIND_TABLE + "("
                + COL_ID + ","
                + COL_USER_ID + ","
                + COL_MARK
                + ") VALUES (" + movieId + "," + userId + "," + mark + ");";
    }

    private String getSelectCurrentMark(Integer movieId){

        return "SELECT * FROM getcommondirectormark(" + movieId + ") AS " + COL_MARK + ";";
    }

    private String getSelectUserMark(Integer movieId,Integer userId){

        return "SELECT * FROM getuserdirectormark(" + movieId + "," + userId + ") AS " + COL_MARK + ";";
    }

    @Override
    protected List<Director> parseResultSet(ResultSet rs) throws DbException {
        LinkedList<Director> result = new LinkedList<>();
        try {
            while (rs.next()) {
                Director director = new Director();
                director.setId(rs.getInt(COL_ID));
                director.setName(rs.getString(COL_NAME));
                director.setSurname(rs.getString(COL_SURNAME));
                director.setBirthday(rs.getDate(COL_BIRTHDAY));
                director.setMotherland(rs.getInt(COL_MOTHERLAND));
                director.setPhoto(rs.getString(COL_PHOTO_ADDRESS));
                director.setMark(rs.getInt(COL_MARK));
                director.setSubscription(rs.getString(COL_SUBSCRIPTION));
                result.add(director);
            }
        } catch (Exception e) {
            throw new DbException(e);
        }
        return result;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Director director) throws DbException {
        try {
            int i = 1;
            statement.setString(i++, director.getName());
            statement.setString(i++, director.getSurname());
            statement.setDate(i++, (Date) director.getBirthday());
            statement.setInt(i++, director.getMotherland().getId());
            statement.setString(i++, director.getPhoto());
            statement.setString(i, director.getSubscription());

        } catch (Exception e) {
            throw new DbException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Director director) throws DbException {
        try {
            int i = 1;
            statement.setString(i++, director.getName());
            statement.setString(i++, director.getSurname());
            statement.setDate(i++, (Date) director.getBirthday());
            statement.setInt(i++, director.getMotherland().getId());
            statement.setString(i++, director.getPhoto());
            statement.setInt(i++, director.getMark());
            statement.setString(i++, director.getSubscription());
            statement.setInt(i, director.getId());
        } catch (Exception e) {
            throw new DbException(e);
        }
    }

    public List<Director> findMostPopular(Integer numPage) throws DbException {
        List<Director> list;
        String sql = getSelectMostPopular(numPage);
        try (PreparedStatement statement = DbWrapper.getConnection().prepareStatement(sql)) {
            ResultSet rs = statement.executeQuery();
            list = parseResultSet(rs);
        } catch (Exception e) {
            throw new DbException(e);
        }
        return list;
    }

    public List<Director> findDirectorsByName(String queryDirector,Integer numPage) throws DbException {
        List<Director> list;
        String sql = getSelectDirectorsByName(queryDirector,numPage);
        try (PreparedStatement statement = DbWrapper.getConnection().prepareStatement(sql)) {
            ResultSet rs = statement.executeQuery();
            list = parseResultSet(rs);
        } catch (Exception e) {
            throw new DbException(e);
        }
        return list;
    }

    public List<Director> findAllDirectorsByMovie(Integer movieId) throws DbException {
        List<Director> list;
        String sql = getSelectDirectorsByMovie(movieId);
        try (PreparedStatement statement = DbWrapper.getConnection().prepareStatement(sql)) {
            ResultSet rs = statement.executeQuery();
            list = parseResultSet(rs);
        } catch (Exception e) {
            throw new DbException(e);
        }
        return list;
    }

    public Integer addUserMark(Integer mark,Integer movieId,Integer userId) throws DbException {
        Integer newMark = null;
        try {
            String sql = getCreateMarkQuery(movieId,userId,mark);
            PreparedStatement statement = DbWrapper.getConnection().prepareStatement(sql);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String sqlMark = getSelectCurrentMark(movieId);
        try (PreparedStatement statement = DbWrapper.getConnection().prepareStatement(sqlMark)) {
            ResultSet rs = statement.executeQuery();
            if(rs.next()){
                newMark = rs.getInt(COL_MARK);
            }
        } catch (Exception e) {
            throw new DbException(e);
        }
        return newMark;
    }

    public Integer getUserMark(Integer movieId,Integer userId) throws DbException {
        Integer userMark = null;
        String sqlMark = getSelectUserMark(movieId,userId);
        try (PreparedStatement statement = DbWrapper.getConnection().prepareStatement(sqlMark)) {
            ResultSet rs = statement.executeQuery();
            if(rs.next()){
                userMark = rs.getInt(COL_MARK);
            }
        } catch (Exception e) {
            throw new DbException(e);
        }
        return userMark;
    }
}
