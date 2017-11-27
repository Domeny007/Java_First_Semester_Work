package servlets.entityServlets.dopEntityServlets;

import database.dao.GenreDao;
import database.entity.Genre;
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

@WebServlet(value = "/add-genre")
public class AddGenre extends HttpServlet {

    private String name;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //Установка кодировки
        Const.setRightEncoding(req,resp);

        name = req.getParameter(Const.KEY_GENRE_NAME);

        addGenre(req);

        resp.sendRedirect(getServletContext().getContextPath() + "personal_page");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Const.setRightEncoding(req, resp);


        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/views/addInstance/addGenre.jsp");
        requestDispatcher.forward(req,resp);
    }

    private void addGenre(HttpServletRequest request) {

        try {

            HttpSession session = request.getSession();

            session.setAttribute(Const.POPUP_DATA,"Добавлен жанр");

            Genre genre = new Genre();

            genre.setName(name);

            GenreDao genreDao = new GenreDao(DbWrapper.getConnection());

            genreDao.save(genre);

        } catch (DbException e) {
            e.printStackTrace();
        }
    }
}
