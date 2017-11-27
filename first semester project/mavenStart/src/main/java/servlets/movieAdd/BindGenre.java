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

@WebServlet(value = "/bind-genres")
public class BindGenre extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Установка кодировки
        Const.setRightEncoding(req, resp);

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/views/adminSearch/search-genres.jsp");
        requestDispatcher.forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Const.setRightEncoding(req, resp);

        String genresId = req.getParameter(Const.KEY_MOVIE_GENRES);
        String genresNames = req.getParameter(Const.KEY_MOVIE_GENRES_NAMES);

        HttpSession session = req.getSession();
        session.setAttribute(Const.KEY_MOVIE_GENRES, genresId);
        session.setAttribute(Const.KEY_MOVIE_GENRES_NAMES, genresNames);

        resp.sendRedirect("/add-movie");

    }
}
