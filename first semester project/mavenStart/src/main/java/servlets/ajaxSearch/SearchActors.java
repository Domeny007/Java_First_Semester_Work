package servlets.ajaxSearch;

import database.dao.ActorDao;
import database.entity.Actor;
import exceptions.DbException;
import utils.Const;
import utils.DatabaseUtils.DbWrapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "searchActors" , value = "/searchActors")
public class SearchActors extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String queryActor = req.getParameter("actor");

        String searchType = req.getParameter("searchType");

        Integer numPage = Integer.valueOf(req.getParameter(Const.KEY_NUM_PAGE));

        List<Actor> actors = new ArrayList<>();
        try {
            if (queryActor != null && !queryActor.trim().equals("")) {
                actors = new ActorDao(DbWrapper.getConnection()).findActorsByName(queryActor, numPage);
                req.setAttribute("title","Найденные по данному имени");
            } else {
                actors = new ActorDao(DbWrapper.getConnection()).findMostPopular(numPage);
                req.setAttribute("title","Самые популярные");
            }

        } catch (DbException e) {
            e.printStackTrace();
        }

        req.setAttribute("actors", actors);
        req.setAttribute("size",actors.size());

        switch (searchType){
            case "user":
                req.getRequestDispatcher("/WEB-INF/xml/user/user_actors.jsp").forward(req, resp);
                break;

            case "movie":
                req.getRequestDispatcher("/WEB-INF/xml/actors.jsp").forward(req, resp);
                break;

        }

    }
}
