package servlets.ajaxSearch;

import database.dao.MovieDao;
import database.entity.Movie;
import exceptions.DbException;
import utils.DatabaseUtils.DbWrapper;
import utils.Const;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "searchMovies" , value = "/searchMovies")
public class SearchMovies extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String queryMovie = req.getParameter("movie");

        String searchType = req.getParameter("searchType");

        Integer numPage = Integer.valueOf(req.getParameter(Const.KEY_NUM_PAGE));

        List<Movie> movies = new ArrayList<>();
        try {
            if (queryMovie != null && !queryMovie.trim().equals("")) {
                movies = new MovieDao(DbWrapper.getConnection()).findMovieByName(queryMovie, numPage);
                req.setAttribute("title","Найденные по данному имени");
            } else {
                movies = new MovieDao(DbWrapper.getConnection()).findMostPopular(numPage);
                req.setAttribute("title","Самые популярные");
            }
        } catch (DbException exception) {
            exception.getMessage();
        }

        req.setAttribute("movies", movies);
        req.setAttribute("size",movies.size());


        switch (searchType) {
            case "user":
                req.getRequestDispatcher("/WEB-INF/xml/user/user_movies.jsp").forward(req, resp);
                break;

            case "group":
                req.getRequestDispatcher("/WEB-INF/xml/movies.jsp").forward(req, resp);
                break;

            case "menu":
                req.getRequestDispatcher("/WEB-INF/xml/menu_search.jsp").forward(req, resp);
                break;
        }

    }
}
