package servlets.movieAdd;

import utils.Const;
import utils.DatabaseUtils.Loader;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(value = "/bind-directors")
public class BindDirector extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Установка кодировки
        Const.setRightEncoding(req, resp);

        Loader.addCountries(req,Const.COUNTRIES_DEFAULT_MODE);

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/views/adminSearch/search-directors.jsp");
        requestDispatcher.forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Const.setRightEncoding(req, resp);

        String directorsId = req.getParameter(Const.KEY_MOVIE_DIRECTORS);
        String directorsNames = req.getParameter(Const.KEY_MOVIE_DIRECTORS_NAMES);

        HttpSession session = req.getSession();
        session.setAttribute(Const.KEY_MOVIE_DIRECTORS, directorsId);
        session.setAttribute(Const.KEY_MOVIE_DIRECTORS_NAMES, directorsNames);

        resp.sendRedirect("/add-movie");

    }
}
