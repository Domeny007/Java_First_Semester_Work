package servlets.entityServlets.movieServlets;

import database.dao.MovieDao;
import database.entity.*;
import exceptions.DbException;
import utils.Const;
import utils.DatabaseUtils.DbWrapper;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "movieContent", value = "/movie_content")
public class MovieContent extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //Установка кодировки
        Const.setRightEncoding(req,resp);

        Object movieObjId = req.getSession().getAttribute(Const.KEY_MOVIE_ID);

        Integer movieId;

        if(movieObjId == null){
            movieId = Integer.valueOf(req.getParameter(Const.KEY_MOVIE_ID));
        } else {
            movieId = (Integer) movieObjId;
        }

        Movie movie = getMovie(movieId);

        setGeneralMovieInf(movie,req);

        HttpSession session = req.getSession();

        session.setAttribute(Const.KEY_CURRENT_MOVIE, movie);

        List<Review> reviews = movie.getReviews();

        session.setAttribute(Const.KEY_MOVIE_REVIEWS,reviews);

        List<Actor> actors = movie.getActors();

        session.setAttribute(Const.KEY_MOVIE_ACTORS,actors);

        List<Director> directors = movie.getDirectors();

        session.setAttribute(Const.KEY_MOVIE_DIRECTORS,directors);

        List<Country> countries = movie.getCountries();

        session.setAttribute(Const.KEY_MOVIE_COUNTRIES,countries);

        List<Genre> genres = movie.getGenres();

        session.setAttribute(Const.KEY_MOVIE_GENRES,genres);

        User user = (User) session.getAttribute(Const.KEY_CURRENT_USER);

        Integer userMark = movie.getUserMark(user.getId());

        session.setAttribute(Const.KEY_USER_MOVIE_MARK,userMark);

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/views/movies/movieContent.jsp");
        requestDispatcher.forward(req,resp);

    }

    private Movie getMovie(Integer movieId) {

        Movie movie = null;

        try {

            MovieDao movieDao = new MovieDao(DbWrapper.getConnection());

            movie = movieDao.find(movieId);

        } catch (DbException e) {
            e.printStackTrace();
        }

        return movie;
    }

    private void setGeneralMovieInf(Movie movie, HttpServletRequest request){

        String year = movie.getYearStr();

        String budget = String.valueOf(movie.getBudget());

        String money = String.valueOf(movie.getMoney());

        List<Integer> numList = new ArrayList<>();

        for(int i = 1; i <= 10; i++){
            numList.add(i);
        }

        HttpSession session = request.getSession();

        session.setAttribute(Const.KEY_MOVIE_YEAR,year);
        session.setAttribute(Const.KEY_MOVIE_BUDGET,budget);
        session.setAttribute(Const.KEY_MOVIE_MONEY,money);
        session.setAttribute(Const.KEY_NUMLIST,numList);

    }

}
