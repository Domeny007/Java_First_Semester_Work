package database.entity;

import database.dao.*;
import exceptions.DbException;
import utils.DatabaseUtils.DbWrapper;
import utils.DateUtil;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Movie implements Identified,Serializable {

    private Integer id;

    private String name;

    private String subscription;

    private transient Date year;

    private Integer budget;

    private Integer money;

    private Integer mark;

    private Integer votes;

    private String photo;

    private List<Actor> actors;

    private List<Director> directors;

    private List<Country> countries;

    private List<Genre> genres;

    private List<Review> reviews;

    private List<Integer> actorsId;

    private List<Integer> directorsId;

    private List<Integer> countriesId;

    private List<Integer> genresId;

    private List<Integer> reviewsId;

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Movie){
            if(this.getId().equals(((Movie) obj).getId())){
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

    public String getSubscription() {
        return subscription;
    }

    public void setSubscription(String subscription) {
        this.subscription = subscription;
    }

    public Date getYear() {
        return year;
    }

    public void setYear(Date year) {
        this.year = year;
    }

    public Integer getBudget() {
        return budget;
    }

    public void setBudget(Integer budget) {
        this.budget = budget;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public Integer getMark() {
        return mark;
    }

    public void setMark(Integer mark) {

        this.mark = mark;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getYearStr(){
        return DateUtil.parseYearDateToStr(this.year);
    }

    /*public List<Actor> getRealActors() {
        List<Actor> actors = new ArrayList<>();
        try {
            ActorDao genreDao = new ActorDao(DbWrapper.getConnection());

            for(Integer actor : this.actors){
                actors.add(genreDao.find(actor));
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
        return actors;
    }

    public void setActors(List<Integer> actors) {
        this.actors = actors;
    }

    public List<Director> getRealDirectors() {
        List<Director> directors = new ArrayList<>();
        try {
            DirectorDao directorDao = new DirectorDao(DbWrapper.getConnection());

            for(Integer genre : this.genres){
                directors.add(directorDao.find(genre));
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
        return directors;
    }

    public void setDirectors(List<Integer> directors) {
        this.directors = directors;
    }

    public List<Country> getRealCountries() {
        List<Country> countries = new ArrayList<>();
        try {
            CountryDao countryDao = new CountryDao(DbWrapper.getConnection());

            for(Integer country : this.countries){
                countries.add(countryDao.find(country));
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
        return countries;
    }

    public void setCountries(List<Integer> countries) {
        this.countries = countries;
    }

    public List<Genre> getRealGenres() {
        List<Genre> genres = new ArrayList<>();
        try {
            GenreDao genreDao = new GenreDao(DbWrapper.getConnection());

        for(Integer genre : this.genres){
            genres.add(genreDao.find(genre));
        }
        } catch (DbException e) {
        e.printStackTrace();
    }
       return genres;
    }

    public void setGenres(List<Integer> genres) {
        this.genres = genres;
    }

    public List<Integer> getActors() {
        return actors;
    }

    public List<Integer> getDirectors() {
        return directors;
    }

    public List<Integer> getCountries() {
        return countries;
    }

    public List<Integer> getGenres() {
        return genres;
    }

    public List<Integer> getReviews() {
        return reviews;
    }*/

    public List<Actor> getActors() {
        List<Actor> actors = new ArrayList<>();
        try {
            actors =  new ActorDao(DbWrapper.getConnection()).findAllActorsByMovie(this.id);
        } catch (DbException e) {
            e.printStackTrace();
        }
        return actors;
    }

    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }

    public List<Director> getDirectors() {
        List<Director> directors = new ArrayList<>();
        try {
            directors =  new DirectorDao(DbWrapper.getConnection()).findAllDirectorsByMovie(this.id);
        } catch (DbException e) {
            e.printStackTrace();
        }
        return directors;
    }

    public void setDirectors(List<Director> directors) {
        this.directors = directors;
    }

    public List<Country> getCountries() {
        List<Country> countries = new ArrayList<>();
        try {
            countries =  new CountryDao(DbWrapper.getConnection()).findAllCountriesByMovie(this.id);
        } catch (DbException e) {
            e.printStackTrace();
        }
        return countries;
    }

    public void setCountries(List<Country> countries) {
        this.countries = countries;
    }

    public List<Genre> getGenres() {
        List<Genre> genres = new ArrayList<>();
        try {
            genres =  new GenreDao(DbWrapper.getConnection()).findAllGenresByMovie(this.id);
        } catch (DbException e) {
            e.printStackTrace();
        }
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public List<Review> getReviews() {
        List<Review> reviews = new ArrayList<>();
        try {
            reviews =  new ReviewDao(DbWrapper.getConnection()).findAllReviewsByMovie(this.id);
        } catch (DbException e) {
            e.printStackTrace();
        }
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public List<Integer> getActorsId() {
        return actorsId;
    }

    public void setActorsId(List<Integer> actorsId) {
        this.actorsId = actorsId;
    }

    public List<Integer> getDirectorsId() {
        return directorsId;
    }

    public void setDirectorsId(List<Integer> directorsId) {
        this.directorsId = directorsId;
    }

    public List<Integer> getCountriesId() {
        return countriesId;
    }

    public void setCountriesId(List<Integer> countriesId) {
        this.countriesId = countriesId;
    }

    public List<Integer> getGenresId() {
        return genresId;
    }

    public void setGenresId(List<Integer> genresId) {
        this.genresId = genresId;
    }

    public List<Integer> getReviewsId() {
        return reviewsId;
    }

    public void setReviewsId(List<Integer> reviewsId) {
        this.reviewsId = reviewsId;
    }

    public void addUserMark(Integer mark,Integer movieId, Integer userId) throws DbException {
        MovieDao movieDao = new MovieDao(DbWrapper.getConnection());

        setMark(movieDao.addUserMark(mark,movieId,userId));
    }

    public Integer getUserMark(Integer userId) {
        Integer userMark = null;
        try {
            MovieDao movieDao = new MovieDao(DbWrapper.getConnection());
            userMark = movieDao.getUserMark(this.id, userId);
            if(userMark == 0){
                userMark = null;
            }

        } catch (DbException e) {
            e.printStackTrace();
        }

        return userMark;

    }

    /*public List<Review> getRealReviews() {
        *//*List<Review> reviews = new ArrayList<>();
        try {
            ReviewDao reviewDao = new ReviewDao(DbWrapper.getConnection());

            for(Integer review : this.reviews){
                reviews.add(reviewDao.find(review));
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
        return reviews;*//*
        List<Review> reviews = new ArrayList<>();
        try {
            reviews =  new ReviewDao(DbWrapper.getConnection()).findAllReviewsByMovie(this.id);
        } catch (DbException e) {
            e.printStackTrace();
        }
        return reviews;

    }*/

}
