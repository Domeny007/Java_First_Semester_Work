package servlets.movieAdd;

import utils.Const;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(value = "/bind-countries")
public class BindCountry extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Установка кодировки
        Const.setRightEncoding(req, resp);

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/views/adminSearch/search-countries.jsp");
        requestDispatcher.forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Const.setRightEncoding(req, resp);

        String countriesId = req.getParameter(Const.KEY_MOVIE_COUNTRIES);
        String countriesNames = req.getParameter(Const.KEY_MOVIE_COUNTRIES_NAMES);

        HttpSession session = req.getSession();
        session.setAttribute(Const.KEY_MOVIE_COUNTRIES, countriesId);
        session.setAttribute(Const.KEY_MOVIE_COUNTRIES_NAMES, countriesNames);

        resp.sendRedirect("/add-movie");
    }
}
