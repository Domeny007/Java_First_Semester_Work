package database.dao;

import database.dao.abstractDao.AbstractDao;
import utils.DatabaseUtils.DbWrapper;
import exceptions.DbException;
import database.entity.Review;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class ReviewDao extends AbstractDao<Review> {

    private final String TABLE_NAME = "public.reviews";

    private final String COL_ID = "review_id";

    private final String COL_TITLE = "title";

    private final String COL_CONTENT = "content";

    private final String COL_MARK = "mark";

    private final String COL_WRITER_ID = "writer_id";

    private final String COL_MOVIE_ID = "movie_id";

    private final String COL_ACTOR_ID = "actor_id";

    private final String COL_DIRECTOR_ID = "director_id";

    private final String MARK_BIND_TABLE = "reviews_users_votes_bind";
    private final String COL_USER_ID = "user_id";

    private String type;

    public ReviewDao(Connection connection) {
        super(connection);
    }

    @Override
    public String  getSelectQuery() {
        return "SELECT "
                + COL_ID + ","
                + COL_TITLE + ","
                + COL_CONTENT + ","
                + COL_MARK + ","
                + COL_WRITER_ID
                + " FROM " + TABLE_NAME;
    }

    @Override
    public String getCreateQuery() {
        String LAST_COLUMN = null;
        switch (type){
            case "movie":
                LAST_COLUMN = COL_MOVIE_ID;
                break;

            case "actor":
                LAST_COLUMN = COL_ACTOR_ID;
                break;

            case "director":
                LAST_COLUMN = COL_DIRECTOR_ID;
                break;
        }

        return "INSERT INTO "
                + TABLE_NAME + "("
                + COL_TITLE + ","
                + COL_CONTENT + ","
                + COL_WRITER_ID + ","
                + LAST_COLUMN
                + ") VALUES (?, ?, ?, ?);";
    }

    @Override
    public String getReadQuery(String id) {
        return getSelectQuery() + " WHERE " + COL_ID + " = " + id;
    }

    @Override
    public String getUpdateQuery() {

        return "UPDATE "
                + TABLE_NAME + " SET "
                + COL_TITLE + " = ?, "
                + COL_CONTENT + " = ?, "
                + COL_MARK + " = ? "
                + " WHERE " + COL_ID + " = ?;";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM " + TABLE_NAME + " WHERE " + COL_ID + " = ?;";
    }

    private String getSelectReviewsByWriter(Integer writerId){
        return getSelectQuery() + " WHERE " + COL_WRITER_ID + " = " + writerId ; }

    private String getSelectReviewsByMovie(Integer movieId){
        return getSelectQuery() + " WHERE " + COL_MOVIE_ID + " = " + movieId ; }

    private String getSelectReviewsByActor(Integer actorId){
        return getSelectQuery() + " WHERE " + COL_ACTOR_ID + " = " + actorId ; }

    private String getSelectReviewsByDirector(Integer directorId){
        return getSelectQuery() + " WHERE " + COL_DIRECTOR_ID + " = " + directorId ; }

    private String getCreateMarkQuery(Integer movieId,Integer userId,Integer mark){

        return "INSERT INTO "
                + MARK_BIND_TABLE + "("
                + COL_ID + ","
                + COL_USER_ID + ","
                + COL_MARK
                + ") VALUES (" + movieId + "," + userId + "," + mark + ");";
    }

    private String getSelectCurrentMark(Integer reviewId){

        return "SELECT * FROM getcommonreviewmark(" + reviewId + ") AS " + COL_MARK + ";";
    }


    public void save(Review review, String type) throws DbException {
        this.type = type;
        super.save(review);
    }

    @Override
    protected List<Review> parseResultSet(ResultSet rs) throws DbException {
        LinkedList<Review> result = new LinkedList<>();
        try {
            while (rs.next()) {
                Review review = new Review();
                review.setId(rs.getInt(COL_ID));
                review.setTitle(rs.getString(COL_TITLE));
                review.setContent(rs.getString(COL_CONTENT));
                review.setMark(rs.getInt(COL_MARK));
                review.setWriter(rs.getInt(COL_WRITER_ID));
                result.add(review);
            }
        } catch (Exception e) {
            throw new DbException(e);
        }
        return result;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Review review) throws DbException {
        try {
            int i = 1;
            statement.setString(i++, review.getTitle());
            statement.setString(i++, review.getContent());
            statement.setInt(i++, review.getWriter().getId());
            switch (type){
                case "movie":
                    statement.setInt(i, review.getMovie().getId());
                    break;

                case "actor":
                    statement.setInt(i, review.getActor().getId());
                    break;

                case "director":
                    statement.setInt(i, review.getDirector().getId());
                    break;
            }

        } catch (Exception e) {
            throw new DbException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Review review) throws DbException {
        try {
            int i = 1;
            statement.setString(i++, review.getTitle());
            statement.setString(i++, review.getContent());
            statement.setInt(i++, review.getMark());
            statement.setInt(i, review.getId());
        } catch (Exception e) {
            throw new DbException(e);
        }
    }

    public List<Review> findAllReviewsByWriter(Integer writerId) throws DbException {
        List<Review> list;
        String sql = getSelectReviewsByWriter(writerId);
        try (PreparedStatement statement = DbWrapper.getConnection().prepareStatement(sql)) {
            ResultSet rs = statement.executeQuery();
            list = parseResultSet(rs);
        } catch (Exception e) {
            throw new DbException(e);
        }
        return list;
    }

    public List<Review> findAllReviewsByMovie(Integer movieId) throws DbException {
        List<Review> list;
        String sql = getSelectReviewsByMovie(movieId);
        try (PreparedStatement statement = DbWrapper.getConnection().prepareStatement(sql)) {
            ResultSet rs = statement.executeQuery();
            list = parseResultSet(rs);
        } catch (Exception e) {
            throw new DbException(e);
        }
        return list;
    }

    public List<Review> findAllReviewsByActor(Integer actorId) throws DbException {
        List<Review> list;
        String sql = getSelectReviewsByActor(actorId);
        try (PreparedStatement statement = DbWrapper.getConnection().prepareStatement(sql)) {
            ResultSet rs = statement.executeQuery();
            list = parseResultSet(rs);
        } catch (Exception e) {
            throw new DbException(e);
        }
        return list;
    }


    public List<Review> findAllReviewsByDirector(Integer directorId) throws DbException {
        List<Review> list;
        String sql = getSelectReviewsByDirector(directorId);
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

}
