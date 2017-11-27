package servlets.entityServlets.directorServlets;

import database.dao.DirectorDao;
import database.entity.Director;
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

@WebServlet(value = "/add-director")
public class AddDirector extends HttpServlet {

    private String name;

    private String surname;

    private String birthday;

    private Integer motherland;

    private String photo;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //Установка кодировки
        Const.setRightEncoding(req,resp);

        name = req.getParameter(Const.KEY_DIRECTOR_NAME);

        surname = req.getParameter(Const.KEY_DIRECTOR_SURNAME);

        birthday = req.getParameter(Const.KEY_DIRECTOR_BIRTHDAY);

        motherland = Integer.valueOf(req.getParameter(Const.KEY_DIRECTOR_MOTHERLAND));

        photo = req.getParameter(Const.KEY_DIRECTOR_PHOTO);

        if (AddPeopleCheck.checkPeopleData(req,name,surname, photo, birthday)){

            addDirector(req);

            resp.sendRedirect(getServletContext().getContextPath() + "personal_page");

        } else {

            Loader.addCountries(req, Const.COUNTRIES_DEFAULT_MODE);

            new PeopleSessionUtil().addDataToSession(req,Const.KEY_DIRECTOR_TYPE);

            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/views/addInstance/addDirector.jsp");
            requestDispatcher.forward(req,resp);
        }



    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Const.setRightEncoding(req, resp);

        Loader.addCountries(req,Const.COUNTRIES_DEFAULT_MODE);

        req.getSession().removeAttribute(Const.KEY_DIRECTOR_MOTHERLAND);

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/views/addInstance/addDirector.jsp");
        requestDispatcher.forward(req,resp);
    }

    private void addDirector(HttpServletRequest request) {

        try {

            HttpSession session = request.getSession();

            session.setAttribute(Const.POPUP_DATA,"Добавлен новый режиссер");

            Director director = new Director();

            director.setName(name);
            director.setSurname(surname);
            director.setBirthday(DateUtil.parseYearDateFromStr(birthday));
            director.setMotherland(motherland);
            director.setPhoto(photo);

            DirectorDao directorDao = new DirectorDao(DbWrapper.getConnection());

            directorDao.save(director);

            session.setAttribute(Const.KEY_CURRENT_DIRECTOR,director);


        } catch (DbException e) {
            e.printStackTrace();
        }
    }

}