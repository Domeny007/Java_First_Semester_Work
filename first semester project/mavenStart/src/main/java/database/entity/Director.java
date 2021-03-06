package database.entity;

import database.dao.CountryDao;
import database.dao.DirectorDao;
import database.dao.ReviewDao;
import exceptions.DbException;
import utils.DatabaseUtils.DbWrapper;
import utils.DateUtil;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Director implements Identified,Serializable{

    private Integer id;

    private String name;

    private String surname;

    transient private java.sql.Date birthday;

    transient private Country motherland;

    private String photo;

    private Integer mark;

    private String subscription;

    private List<Review> reviews;

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Director){
            if(this.getId().equals(((Director) obj).getId())){
                return true;
            }
        }
        return false;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(java.sql.Date birthday) {
        this.birthday = birthday;
    }

    public Country getMotherland() {
        return motherland;
    }

    public void setMotherland(Integer motherlandId) {

        try {
            this.motherland = new CountryDao(DbWrapper.getConnection()).find(motherlandId);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public String getMotherlandName() {
        return motherland.getName();
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Integer getMark() {
        return mark;
    }

    public void setMark(Integer mark) {
        this.mark = mark;
    }

    public String getSubscription() {
        return subscription;
    }

    public void setSubscription(String subscription) {
        this.subscription = subscription;
    }

    public List<Review> getReviews() {
        List<Review> reviews = new ArrayList<>();
        try {
            reviews =  new ReviewDao(DbWrapper.getConnection()).findAllReviewsByDirector(this.id);
        } catch (DbException e) {
            e.printStackTrace();
        }
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public String getBirthdayStr(){
        return DateUtil.parseYearDateToStr(this.birthday);
    }

    public void addUserMark(Integer mark,Integer movieId, Integer userId) throws DbException {
        DirectorDao directorDao = new DirectorDao(DbWrapper.getConnection());

        setMark(directorDao.addUserMark(mark,movieId,userId));
    }

    public Integer getUserMark(Integer userId) {
        Integer userMark = null;
        try {
            DirectorDao directorDao = new DirectorDao(DbWrapper.getConnection());
            userMark = directorDao.getUserMark(this.id, userId);
            if(userMark == 0){
                userMark = null;
            }

        } catch (DbException e) {
            e.printStackTrace();
        }

        return userMark;

    }

}
