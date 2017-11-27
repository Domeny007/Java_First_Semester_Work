package servlets.entityServlets.actorServlets;

import database.dao.ActorDao;
import database.entity.Actor;
import exceptions.DbException;
import utils.CheckUtils.AddPeopleCheck;
import utils.Const;
import utils.DatabaseUtils.DbWrapper;
import utils.DatabaseUtils.Loader;
import utils.DateUtil;
import utils.SessionUtils.PeopleSessionUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(value = "/add-actor")
public class AddActor extends HttpServlet {

    private String name;

    private String surname;

    private String birthday;

    private Integer motherland;

    private String photo;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //Установка кодировки
        Const.setRightEncoding(req,resp);

        name = req.getParameter(Const.KEY_ACTOR_NAME);

        surname = req.getParameter(Const.KEY_ACTOR_SURNAME);

        birthday = req.getParameter(Const.KEY_ACTOR_BIRTHDAY);

        motherland = Integer.valueOf(req.getParameter(Const.KEY_ACTOR_MOTHERLAND));

        photo = req.getParameter(Const.KEY_ACTOR_PHOTO);

        if (AddPeopleCheck.checkPeopleData(req,name,surname, photo, birthday)){

            addActor(req);

            resp.sendRedirect(getServletContext().getContextPath() + "personal_page");

        } else {

            Loader.addCountries(req,Const.COUNTRIES_DEFAULT_MODE);

            new PeopleSessionUtil().addDataToSession(req,Const.KEY_ACTOR_TYPE);

            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/views/addInstance/addActor.jsp");
            requestDispatcher.forward(req,resp);
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Const.setRightEncoding(req, resp);

        Loader.addCountries(req, Const.COUNTRIES_DEFAULT_MODE);

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/views/addInstance/addActor.jsp");
        requestDispatcher.forward(req,resp);
    }


    private void addActor(HttpServletRequest request) {

        try {

            HttpSession session = request.getSession();

            session.setAttribute(Const.POPUP_DATA,"Добавлен новый актер");

            Actor actor = new Actor();

            actor.setName(name);
            actor.setSurname(surname);
            actor.setBirthday(DateUtil.parseYearDateFromStr(birthday));
            actor.setMotherland(motherland);
            actor.setPhoto(photo);

            ActorDao actorDao = new ActorDao(DbWrapper.getConnection());

            actorDao.save(actor);

            session.setAttribute(Const.KEY_CURRENT_ACTOR,actor);


        } catch (DbException e) {
            e.printStackTrace();
        }
    }

}