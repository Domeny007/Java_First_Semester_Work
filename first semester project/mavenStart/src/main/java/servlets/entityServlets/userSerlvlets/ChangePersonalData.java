package servlets.entityServlets.userSerlvlets;

import database.entity.User;
import utils.Const;
import utils.DatabaseUtils.DbWrapper;
import database.dao.UserDao;
import exceptions.DbException;
import utils.DatabaseUtils.Loader;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(value = "/change-data")
public class ChangePersonalData extends HttpServlet {

    private String username;

    private Integer country;

    private String gender;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //Установка кодировки
        Const.setRightEncoding(req, resp);

        username = req.getParameter(Const.KEY_USERNAME);
        country = Integer.valueOf(req.getParameterValues(Const.KEY_COUNTRY)[0]);
        gender = req.getParameterValues(Const.KEY_GENDER)[0];

        updateUser(req);

        HttpSession session = req.getSession();

        session.setAttribute("changedData", "Данные изменены успешно");

        resp.sendRedirect(getServletContext().getContextPath() + "personal_page");

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Const.setRightEncoding(req, resp);

        Loader.addCountries(req,Const.COUNTRIES_DEFAULT_MODE);

        HttpSession session = req.getSession();

        User user = (User) session.getAttribute(Const.KEY_CURRENT_USER);

        session.setAttribute(Const.KEY_COUNTRY,user.getCountry().getId());
        session.setAttribute(Const.KEY_GENDER,user.getGender());

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/views/user/changeData.jsp");
        requestDispatcher.forward(req,resp);
    }


    private void updateUser(HttpServletRequest req){
        HttpSession session = req.getSession();

        try {

            User user = (User) session.getAttribute(Const.KEY_CURRENT_USER);

            user.setUsername(username);
            user.setCountry(country);
            user.setGender(gender);

            //обновление
            UserDao userDao = new UserDao(DbWrapper.getConnection());
            userDao.update(user);

            req.getSession().setAttribute(Const.KEY_CURRENT_USER, user);

        } catch (DbException e) {
            e.printStackTrace();
        }
    }
}
