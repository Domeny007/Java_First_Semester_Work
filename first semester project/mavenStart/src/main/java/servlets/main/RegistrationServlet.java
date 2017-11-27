package servlets.main;

import database.entity.User;
import database.dao.UserDao;
import exceptions.DbException;
import utils.CheckUtils.RegistrationCheckUtil;
import utils.DatabaseUtils.Loader;
import utils.SecurityUtil;
import utils.SessionUtils.UserSessionUtil;
import utils.Const;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;

import static utils.DatabaseUtils.DbWrapper.getConnection;

@WebServlet(name = "registration_serv", value = "/registration_serv")
public class RegistrationServlet extends HttpServlet {

    private String email;
    private String username;
    private String password;
    private Integer country;
    private String gender;

    private String cookieId;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        // защита от повтора post запроса
        resp.setStatus(HttpServletResponse.SC_FOUND);
        resp.setHeader("Location", getServletContext().getContextPath() + "index.jsp");

        //Установка кодировки
        Const.setRightEncoding(req, resp);

        //Забираем данные из формы
        email = req.getParameter(Const.KEY_EMAIL);
        username = req.getParameter(Const.KEY_USERNAME);
        password = req.getParameter(Const.KEY_PASSWORD);
        String passwordAgain = req.getParameter(Const.KEY_PASSWORD_AGAIN);
        country = Integer.valueOf(req.getParameterValues(Const.KEY_COUNTRY)[0]);
        gender = req.getParameterValues(Const.KEY_GENDER)[0];

        UserSessionUtil.saveRegistrSession(req);

        //Проверка на введенные данные
        if (RegistrationCheckUtil.checkRegistrationData(email, password, passwordAgain, req, resp)) {

            UserSessionUtil.removeRegistrSession(req);

            password = SecurityUtil.hashPassword(password);
            cookieId = SecurityUtil.generateRandomCookieId();

            saveUser();

            HttpSession session = req.getSession();
            session.setAttribute(Const.POPUP_DATA,"Вы успешно зарегестрированы");
        }
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Const.setRightEncoding(req, resp);

        Loader.addCountries(req, Const.COUNTRIES_DEFAULT_MODE);

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/views/main/registration.jsp");
        requestDispatcher.forward(req,resp);
    }

    private void saveUser() {

        User user = new User();

        fillDataInUser(user);

        try {

            UserDao userDao = new UserDao(getConnection());

            userDao.save(user);


        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    private void fillDataInUser(User user){
        user.setEmail(email);
        user.setPassword(password);
        user.setUsername(username);
        user.setCountry(country);
        user.setGender(gender);
        user.setCookieId(cookieId);
    }
}
