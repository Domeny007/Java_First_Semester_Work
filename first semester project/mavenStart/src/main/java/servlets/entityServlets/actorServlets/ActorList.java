package servlets.entityServlets.actorServlets;

import database.dao.ActorDao;
import database.entity.Actor;
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

@WebServlet(name = "actorList", value = "/actorList")
public class ActorList extends HttpServlet {

    private List<Actor> actorsList;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //Установка кодировки
        Const.setRightEncoding(req,resp);

        getListFromDB(Const.START_PAGE);

        req.setAttribute("actors", actorsList);
        req.setAttribute(Const.KEY_NUM_PAGE,Const.START_PAGE);

        RequestDispatcher requestDispatcher = req.getRequestDispatcher(getServletContext().getContextPath() + "/WEB-INF/views/actors/actorsList.jsp");
        requestDispatcher.forward(req,resp);

    }

    private void getListFromDB(Integer numPage) {

        try {

            ActorDao actorDao = new ActorDao(DbWrapper.getConnection());

            actorsList =  actorDao.findMostPopular(numPage);

        } catch (DbException e) {
            e.printStackTrace();
        }
    }
}
