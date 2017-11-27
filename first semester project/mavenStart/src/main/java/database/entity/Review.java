package database.entity;


import database.dao.ReviewDao;
import database.dao.UserDao;
import exceptions.DbException;
import utils.DatabaseUtils.DbWrapper;

import java.io.Serializable;

public class Review implements Identified,Serializable {

    private Integer id;

    private String title;

    private String content;

    private Integer mark;

    private User writer;

    private Movie movie;

    private Actor actor;

    private Director director;

    public Review() {}

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Review){
            if(this.getId().equals(((Review) obj).getId())){
                return true;
            }
        }
        return false;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getMark() {
        return mark;
    }

    public void setMark(Integer mark) {
        this.mark = mark;
    }

    public User getWriter() {
        return writer;
    }

    public void setWriter(User writer) {
        this.writer = writer;
    }

    public void setWriter(Integer writerId) throws DbException {
        UserDao userDao = new UserDao(DbWrapper.getConnection());
        this.writer = userDao.find(writerId);
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Actor getActor() {
        return actor;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
    }

    public Director getDirector() {
        return director;
    }

    public void setDirector(Director director) {
        this.director = director;
    }

    public void addUserMark(Integer mark,Integer reviewId, Integer userId) throws DbException {
        ReviewDao reviewDao = new ReviewDao(DbWrapper.getConnection());

        setMark(reviewDao.addUserMark(mark,reviewId,userId));
    }

}
