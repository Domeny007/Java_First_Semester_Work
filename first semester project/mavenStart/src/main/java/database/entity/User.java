package database.entity;


import database.dao.CountryDao;
import database.dao.ReviewDao;
import database.dao.RightDao;
import utils.DatabaseUtils.DbWrapper;
import exceptions.DbException;

import java.io.Serializable;
import java.util.List;

public class User implements Identified,Serializable {

    private Integer id;

    private String email;

    private String password;

    private String username;

    private Country country;

    private String gender;

    private String subscribed;

    private Right rights;

    private Integer mark;

    private String cookieId;

    private List<Review> reviews;

    public User() {
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof User){
            if(this.getId().equals(((User) obj).getId())){
                return true;
            }
        }
        return false;
    }

    public User(String email, String password, String username, Integer countryId, String gender, String subscribed) {
        this.email = email;
        this.username = username;
        this.password = password;
        setCountry(countryId);
        this.gender = gender;
        this.subscribed = subscribed;
    }



    public String getEmail() {
        return email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Integer countryId) {

        try {
            this.country = new CountryDao(DbWrapper.getConnection()).find(countryId);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSubscribed() {
        return subscribed;
    }

    public void setSubscribed(String subscribed) {
        this.subscribed = subscribed;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Right getRights() {
        return rights;
    }

    public void setRights(Right rights){
        this.rights = rights;
    }

    public void setRights(Integer rightsId) {

        try {
            this.rights = new RightDao(DbWrapper.getConnection()).find(rightsId);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public Integer getMark() {
        return mark;
    }

    public void setMark(Integer mark) {
        this.mark = mark;
    }

    public String getFullDescription(final String SEPARATOR){
        return this.username + SEPARATOR + this.country + SEPARATOR +
                this.gender + SEPARATOR + this.subscribed;
    }

    public List<Review> getReviews() throws DbException {

        return new ReviewDao(DbWrapper.getConnection()).findAllReviewsByWriter(this.getId());
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public String getCookieId() {
        return cookieId;
    }

    public void setCookieId(String cookieId) {
        this.cookieId = cookieId;
    }
}

