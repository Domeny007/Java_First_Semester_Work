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

@WebServlet(value = "/bind-actors")
public class BindActor extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Установка кодировки
        Const.setRightEncoding(req, resp);

        Loader.addCountries(req,Const.COUNTRIES_DEFAULT_MODE);

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/views/adminSearch/search-actors.jsp");
        requestDispatcher.forward(req,resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Const.setRightEncoding(req, resp);

        String actorsId = req.getParameter(Const.KEY_MOVIE_ACTORS);
        String actorsNames = req.getParameter(Const.KEY_MOVIE_ACTORS_NAMES);

        HttpSession session = req.getSession();
        session.setAttribute(Const.KEY_MOVIE_ACTORS, actorsId);
        session.setAttribute(Const.KEY_MOVIE_ACTORS_NAMES, actorsNames);

        resp.sendRedirect("/add-movie");

    }
}
