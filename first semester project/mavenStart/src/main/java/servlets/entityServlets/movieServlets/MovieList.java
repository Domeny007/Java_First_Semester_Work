package servlets.entityServlets.movieServlets;

import database.dao.MovieDao;
import database.entity.Movie;
import exceptions.DbException;
import utils.Const;
import utils.DatabaseUtils.DbWrapper;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "movieList", value = "/movieList")
public class MovieList extends HttpServlet {

    private List<Movie> movieList;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //Установка кодировки
        Const.setRightEncoding(req,resp);

        gettingListFromDB(Const.START_PAGE);

        req.setAttribute("list",movieList);
        req.setAttribute("size",movieList.size());
        req.setAttribute(Const.KEY_NUM_PAGE,Const.START_PAGE);

        RequestDispatcher requestDispatcher = req.getRequestDispatcher(getServletContext().getContextPath() + "/WEB-INF/views/movies/movieList.jsp");
        requestDispatcher.forward(req,resp);

    }

    private void gettingListFromDB(Integer numPage) {

        try {

            MovieDao movieDao = new MovieDao(DbWrapper.getConnection());

            movieList =  movieDao.findMostPopular(numPage);

        } catch (DbException e) {
            e.printStackTrace();
        }

    }
}
