package servlets.entityServlets.actorServlets;

import database.dao.ActorDao;
import database.entity.Actor;
import database.entity.Review;
import database.entity.User;
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
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "actorContent", value = "/actor_content")
public class ActorContent extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //Установка кодировки
        Const.setRightEncoding(req,resp);

        Object movieObjId = req.getSession().getAttribute(Const.KEY_ACTOR_ID);

        Integer actorId;

        if(movieObjId == null){
            actorId = Integer.valueOf(req.getParameter(Const.KEY_ACTOR_ID));
        } else {
            actorId = (Integer) movieObjId;
        }

        Actor actor = getActor(actorId);

        setGeneralActorInf(actor,req);


        HttpSession session = req.getSession();

        session.setAttribute(Const.KEY_CURRENT_ACTOR, actor);

        List<Review> reviews = actor.getReviews();

        session.setAttribute(Const.KEY_ACTOR_REVIEWS,reviews);


        User user = (User) session.getAttribute(Const.KEY_CURRENT_USER);

        Integer userMark = actor.getUserMark(user.getId());

        session.setAttribute(Const.KEY_USER_MOVIE_MARK,userMark);

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/views/actors/actorContent.jsp");
        requestDispatcher.forward(req,resp);

    }

    private Actor getActor(Integer actorId) {

        Actor actor = null;

        try {

            ActorDao actorDao = new ActorDao(DbWrapper.getConnection());

            actor = actorDao.find(actorId);

        } catch (DbException e) {
            e.printStackTrace();
        }

        return actor;
    }

    private void setGeneralActorInf(Actor actor, HttpServletRequest request){

        String birthday = actor.getBirthdayStr();

        String motherland = actor.getMotherland().getName();

        List<Integer> numList = new ArrayList<>();

        for(int i = 1; i <= 10; i++){
            numList.add(i);
        }

        HttpSession session = request.getSession();

        session.setAttribute(Const.KEY_ACTOR_BIRTHDAY,birthday);
        session.setAttribute(Const.KEY_ACTOR_MOTHERLAND,motherland);
        session.setAttribute(Const.KEY_NUMLIST,numList);

    }
}
