package database.dao;

import database.dao.abstractDao.AbstractDao;
import database.entity.Actor;
import exceptions.DbException;
import utils.DatabaseUtils.DbWrapper;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class ActorDao extends AbstractDao<Actor> {

    private final String TABLE_NAME = "public.actors";

    private final String COL_ID = "actor_id";

    private final String COL_NAME = "name";

    private final String COL_SURNAME = "surname";

    private final String COL_BIRTHDAY = "birthday";

    private final String COL_MOTHERLAND = "motherland";

    private final String COL_PHOTO_ADDRESS = "photo";

    private final String COL_MARK = "mark";

    private final String COL_SUBSCRIPTION = "subscription";

    private final String MARK_BIND_TABLE = "actors_users_votes_bind";
    private final String COL_USER_ID = "user_id";

    public ActorDao(Connection connection) {
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

    private String getSelectMostPopular(Integer numPage) {

        return getSelectQuery() + " ORDER BY " + COL_MARK + " DESC," + COL_ID + " DESC"
                + " LIMIT " + 5 + " OFFSET " + numPage*5 + " ;";
    }

    private String getSelectActorsByName(String queryActor,Integer numPage){
        queryActor = queryActor.toLowerCase();
        return getSelectQuery() + " WHERE LOWER(" + COL_NAME + ") SIMILAR TO " + "'" +  queryActor + "%'"
        + " ORDER BY " + COL_NAME + " DESC," + COL_ID + " DESC"
                + " LIMIT " + 5 + " OFFSET " + numPage*5 + " ;";

    }

    private String getSelectActorsByMovie(Integer movieId){
        return "SELECT * FROM getmovieactors(" + movieId + ")";
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

        return "SELECT * FROM getcommonactormark(" + movieId + ") AS " + COL_MARK + ";";
    }

    private String getSelectUserMark(Integer movieId,Integer userId){

        return "SELECT * FROM getuseractormark(" + movieId + "," + userId + ") AS " + COL_MARK + ";";
    }


    @Override
    protected List<Actor> parseResultSet(ResultSet rs) throws DbException {
        LinkedList<Actor> result = new LinkedList<>();
        try {
            while (rs.next()) {
                Actor actor = new Actor();
                actor.setId(rs.getInt(COL_ID));
                actor.setName(rs.getString(COL_NAME));
                actor.setSurname(rs.getString(COL_SURNAME));
                actor.setBirthday(rs.getDate(COL_BIRTHDAY));
                actor.setMotherland(rs.getInt(COL_MOTHERLAND));
                actor.setPhoto(rs.getString(COL_PHOTO_ADDRESS));
                actor.setMark(rs.getInt(COL_MARK));
                actor.setSubscription(rs.getString(COL_SUBSCRIPTION));
                result.add(actor);
            }
        } catch (Exception e) {
            throw new DbException(e);
        }
        return result;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Actor actor) throws DbException {
        try {
            int i = 1;
            statement.setString(i++, actor.getName());
            statement.setString(i++, actor.getSurname());
            statement.setDate(i++, actor.getBirthday());
            statement.setInt(i++, actor.getMotherland().getId());
            statement.setString(i++, actor.getPhoto());
            statement.setString(i, actor.getSubscription());
        } catch (Exception e) {
            throw new DbException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Actor actor) throws DbException {
        try {
            int i = 1;
            statement.setString(i++, actor.getName());
            statement.setString(i++, actor.getSurname());
            statement.setDate(i++, (Date) actor.getBirthday());
            statement.setInt(i++, actor.getMotherland().getId());
            statement.setString(i++, actor.getPhoto());
            statement.setInt(i++, actor.getMark());
            statement.setString(i++, actor.getSubscription());
            statement.setInt(i, actor.getId());
        } catch (Exception e) {
            throw new DbException(e);
        }
    }

    public List<Actor> findMostPopular(Integer numPage) throws DbException {
        List<Actor> list;
        String sql = getSelectMostPopular(numPage);
        try (PreparedStatement statement = DbWrapper.getConnection().prepareStatement(sql)) {
            ResultSet rs = statement.executeQuery();
            list = parseResultSet(rs);
        } catch (Exception e) {
            throw new DbException(e);
        }
        return list;
    }

    public List<Actor> findActorsByName(String queryActor,Integer numPage) throws DbException {
        List<Actor> list;
        String sql = getSelectActorsByName(queryActor,numPage);
        try (PreparedStatement statement = DbWrapper.getConnection().prepareStatement(sql)) {
            ResultSet rs = statement.executeQuery();
            list = parseResultSet(rs);
        } catch (Exception e) {
            throw new DbException(e);
        }
        return list;
    }

    public List<Actor> findAllActorsByMovie(Integer movieId) throws DbException {
        List<Actor> list;
        String sql = getSelectActorsByMovie(movieId);
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
