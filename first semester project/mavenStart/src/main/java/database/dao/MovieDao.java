package database.dao;

import database.dao.abstractDao.AbstractDao;
import database.entity.Movie;
import exceptions.DbException;
import utils.DatabaseUtils.DbWrapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class MovieDao extends AbstractDao<Movie> {

    private final String TABLE_NAME = "public.movies";

    private final String COL_ID = "movie_id";

    private final String COL_NAME = "name";

    private final String COL_SUBSCRIPTION = "subscription";

    private final String COL_YEAR = "year";

    private final String COL_BUDGET = "budget";

    private final String COL_MONEY = "money";

    private final String COL_MARK = "mark";

    private final String COL_PHOTO_ADDRESS = "photo";


    private final String COL_ACTOR_ID = "actor_id";

    private final String COL_DIRECTOR_ID = "director_id";

    private final String COL_GENRE_ID = "genre_id";

    private final String COL_COUNTRY_ID = "country_id";

    private final String ACTORS_BIND_TABLE = "movies_actors_bind";

    private final String DIRECTORS_BIND_TABLE = "movies_directors_bind";

    private final String COUNTRIES_BIND_TABLE = "movies_countries_bind";

    private final String GENRES_BIND_TABLE = "movies_genres_bind";

    private final List<String> BIND_TABLES = Arrays.asList(ACTORS_BIND_TABLE,DIRECTORS_BIND_TABLE,
                                                            COUNTRIES_BIND_TABLE,GENRES_BIND_TABLE);

    private final List<String> BIND_ID = Arrays.asList(COL_ACTOR_ID,COL_DIRECTOR_ID,
            COL_COUNTRY_ID,COL_GENRE_ID);

    private final String MARK_BIND_TABLE = "movies_users_votes_bind";
    private final String COL_USER_ID = "user_id";

    public MovieDao(Connection connection) {
        super(connection);
    }

    @Override
    public String getSelectQuery() {
        return "SELECT "
                + COL_ID + ","
                + COL_NAME + ","
                + COL_SUBSCRIPTION + ","
                + COL_YEAR + ","
                + COL_BUDGET + ","
                + COL_MONEY + ","
                + COL_MARK + ","
                + COL_PHOTO_ADDRESS
                + " FROM " + TABLE_NAME;
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO " + TABLE_NAME + " ("
                + COL_NAME + ","
                + COL_SUBSCRIPTION + ","
                + COL_YEAR + ","
                + COL_BUDGET + ","
                + COL_MONEY + ","
                + COL_PHOTO_ADDRESS
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
                + COL_SUBSCRIPTION + " = ?, "
                + COL_YEAR + " =  ?, "
                + COL_BUDGET + " = ?, "
                + COL_MONEY + " =  ?, "
                + COL_MARK + " =  ?, "
                + COL_PHOTO_ADDRESS + " =  ? "
                + " WHERE " + COL_ID + " = ?;";
    }

    @Override
    public String getDeleteQuery() {

        return "DELETE FROM " + TABLE_NAME + " WHERE " + COL_ID + " = ?;";
    }

    private String getCreateBindQuery(String bindTable,String columnOne,String columnTwo,
                                       Integer idOne,Integer idTwo){
        return "INSERT INTO "
                + bindTable + "("
                + columnOne + ","
                + columnTwo
                + ") VALUES (" + idOne + "," + idTwo + ");";
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

        return "SELECT * FROM getcommonmoviemark(" + movieId + ") AS " + COL_MARK + ";";
    }

    private String getSelectUserMark(Integer movieId,Integer userId){

        return "SELECT * FROM getusermoviemark(" + movieId + "," + userId + ") AS " + COL_MARK + ";";
    }

    private String getSelectMostPopular(Integer numPage) {
        return getSelectQuery() + " ORDER BY " + COL_MARK + " DESC," + COL_ID + " DESC"
                + " LIMIT " + 5 + " OFFSET " + numPage*5 + " ;";
    }

    private String getSelectMoviesByName(String queryMovie,Integer numPage){
        queryMovie = queryMovie.toLowerCase();
        return getSelectQuery() + " WHERE LOWER(" + COL_NAME + ") SIMILAR TO " + "'" +  queryMovie + "%'"
                + " ORDER BY " + COL_NAME+ " DESC," + COL_ID + " DESC"
                + " LIMIT " + 5 + " OFFSET " + numPage*5 + " ;";
    }

    @Override
     public void save(Movie movie) throws DbException {
        super.save(movie);
        for (int i = 0; i < BIND_TABLES.size(); i++){
            List<Integer> bindIdList = new ArrayList<>();
            String bindTable = BIND_TABLES.get(i);
            String bindColumn = BIND_ID.get(i);
            switch (BIND_TABLES.get(i)){
                case ACTORS_BIND_TABLE:
                    bindIdList = movie.getActorsId();
                    break;

                case DIRECTORS_BIND_TABLE:
                    bindIdList = movie.getDirectorsId();
                    break;

                case COUNTRIES_BIND_TABLE:
                    bindIdList = movie.getCountriesId();
                    break;

                case GENRES_BIND_TABLE:
                    bindIdList = movie.getGenresId();
                    break;
            }
            for(Integer bindIdInt : bindIdList) {
                try {
                    String statementStr = getCreateBindQuery(bindTable, bindColumn, COL_ID,bindIdInt , movie.getId());
                    PreparedStatement statement = DbWrapper.getConnection().prepareStatement(statementStr);
                    statement.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    @Override
    protected List<Movie> parseResultSet(ResultSet rs) throws DbException {
        LinkedList<Movie> result = new LinkedList<>();
        try {
            while (rs.next()) {
                Movie movie = new Movie();
                movie.setId(rs.getInt(COL_ID));
                movie.setName(rs.getString(COL_NAME));
                movie.setSubscription(rs.getString(COL_SUBSCRIPTION));
                movie.setYear(rs.getDate(COL_YEAR));
                movie.setBudget(rs.getInt(COL_BUDGET));
                movie.setMoney(rs.getInt(COL_MONEY));
                movie.setMark(rs.getInt(COL_MARK));
                movie.setPhoto(rs.getString(COL_PHOTO_ADDRESS));
                result.add(movie);
            }
        } catch (Exception e) {
            throw new DbException(e);
        }
        return result;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Movie movie) throws DbException {
        try {
            int i = 1;
            statement.setString(i++, movie.getName());
            statement.setString(i++, movie.getSubscription());
            statement.setDate(i++, movie.getYear());
            statement.setInt(i++, movie.getBudget());
            statement.setInt(i++, movie.getMoney());
            statement.setString(i, movie.getPhoto());
        } catch (Exception e) {
            throw new DbException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Movie movie) throws DbException {
        try {
            int i = 1;
            statement.setString(i++, movie.getName());
            statement.setString(i++, movie.getSubscription());
            statement.setDate(i++, movie.getYear());
            statement.setInt(i++, movie.getBudget());
            statement.setInt(i++, movie.getMoney());
            statement.setInt(i++, movie.getMark());
            statement.setString(i++, movie.getPhoto());
            statement.setInt(i, movie.getId());
        } catch (Exception e) {
            throw new DbException(e);
        }
    }

    public List<Movie> findMostPopular(Integer numPage) throws DbException {
        List<Movie> list;
        String sql = getSelectMostPopular(numPage);
        try (PreparedStatement statement = DbWrapper.getConnection().prepareStatement(sql)) {
            ResultSet rs = statement.executeQuery();
            list = parseResultSet(rs);
        } catch (Exception e) {
            throw new DbException(e);
        }
        return list;
    }

    public List<Movie> findMovieByName(String queryMovie,Integer numPage) throws DbException {
        List<Movie> list;
        String sql = getSelectMoviesByName(queryMovie,numPage);
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
