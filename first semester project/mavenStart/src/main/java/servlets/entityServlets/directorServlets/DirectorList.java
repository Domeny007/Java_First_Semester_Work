package servlets.entityServlets.directorServlets;

import database.dao.DirectorDao;
import database.entity.Director;
import exceptions.DbException;
import utils.DatabaseUtils.DbWrapper;
import utils.Const;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "directorList", value = "/directorList")
public class DirectorList extends HttpServlet {

    private List<Director> directorList;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //Установка кодировки
        Const.setRightEncoding(req,resp);

        gettingListFromDB(Const.START_PAGE);

        // создаем таблицу с данными из cookie
        req.setAttribute("directors",directorList);
        req.setAttribute(Const.KEY_NUM_PAGE,Const.START_PAGE);

        RequestDispatcher requestDispatcher = req.getRequestDispatcher(getServletContext().getContextPath() + "/WEB-INF/views/directors/directorsList.jsp");
        requestDispatcher.forward(req,resp);

    }

    private void gettingListFromDB(Integer numPage) {

        try {

            DirectorDao directorDao = new DirectorDao(DbWrapper.getConnection());

            directorList =  directorDao.findMostPopular(numPage);

        } catch (DbException e) {
            e.printStackTrace();
        }

    }
}
