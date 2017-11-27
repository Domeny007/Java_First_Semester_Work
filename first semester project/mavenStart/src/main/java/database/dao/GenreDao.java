package database.dao;

import database.dao.abstractDao.AbstractDao;
import database.entity.Genre;
import exceptions.DbException;
import utils.DatabaseUtils.DbWrapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

public class GenreDao extends AbstractDao<Genre>{

    private final String TABLE_NAME = "public.genres";

    private final String COL_ID = "genre_id";

    private final String COL_NAME = "name";

    public GenreDao(Connection connection) {
        super(connection);
    }

    @Override
    public String getSelectQuery() {
        return "SELECT "
                + COL_ID + ","
                + COL_NAME
                + " FROM " + TABLE_NAME;
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO " + TABLE_NAME + " ("
                + COL_NAME
                + ") VALUES (?);";
    }

    @Override
    public String getReadQuery(String id) {

        return getSelectQuery() + " WHERE " + COL_ID + " = " + id + ";";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE " + TABLE_NAME + " SET "
                + COL_NAME + " = ? "
                + " WHERE " + COL_ID + " = ?;";
    }

    @Override
    public String getDeleteQuery() {

        return "DELETE FROM " + TABLE_NAME + " WHERE " + COL_ID + " = ?;";
    }

    private String getSelectGenresByName(String queryGenre){
        queryGenre = queryGenre.toLowerCase();
        return getSelectQuery() + " WHERE LOWER(" + COL_NAME + ") SIMILAR TO " + "'" +  queryGenre + "%'"
                + " ORDER BY " + COL_NAME + " DESC LIMIT 10;";
    }

    private String getSelectGenresByMovie(Integer movieId){
        return "SELECT * FROM getmoviegenres(" + movieId + ")";
    }

    @Override
    protected List<Genre> parseResultSet(ResultSet rs) throws DbException {
        LinkedList<Genre> result = new LinkedList<>();
        try {
            while (rs.next()) {
                Genre genre = new Genre();
                genre.setId(rs.getInt(COL_ID));
                genre.setName(rs.getString(COL_NAME));
                result.add(genre);
            }
        } catch (Exception e) {
            throw new DbException(e);
        }
        return result;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Genre genre) throws DbException {
        try {
            int i = 1;
            statement.setString(i, genre.getName());
        } catch (Exception e) {
            throw new DbException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Genre genre) throws DbException {
        try {
            int i = 1;
            statement.setString(i++, genre.getName());
            statement.setInt(i, genre.getId());
        } catch (Exception e) {
            throw new DbException(e);
        }
    }

    public List<Genre> findGenresByName(String queryGenre) throws DbException {
        List<Genre> list;
        String sql = getSelectGenresByName(queryGenre);
        try (PreparedStatement statement = DbWrapper.getConnection().prepareStatement(sql)) {
            ResultSet rs = statement.executeQuery();
            list = parseResultSet(rs);
        } catch (Exception e) {
            throw new DbException(e);
        }
        return list;
    }

    public List<Genre> findAllGenresByMovie(Integer movieId) throws DbException {
        List<Genre> list;
        String sql = getSelectGenresByMovie(movieId);
        try (PreparedStatement statement = DbWrapper.getConnection().prepareStatement(sql)) {
            ResultSet rs = statement.executeQuery();
            list = parseResultSet(rs);
        } catch (Exception e) {
            throw new DbException(e);
        }
        return list;
    }
}
