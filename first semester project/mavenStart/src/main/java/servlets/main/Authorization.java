package servlets.main;

import database.entity.User;
import utils.Const;
import utils.DatabaseUtils.DbWrapper;
import database.dao.UserDao;
import exceptions.DbException;
import utils.CheckUtils.AuthenticationCheckUtil;
import utils.CookieUtil;
import utils.SecurityUtil;
import utils.SessionUtils.UserSessionUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.HashMap;

@WebServlet(name = "authorization", value = "/authorization")
public class Authorization extends HttpServlet {

    private String password;
    private String email;
    private boolean remember;

    private Cookie[] cookies;
    private String cookieId;
    private boolean cookieIdExists;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // защита от повтора post запроса
        resp.setStatus(HttpServletResponse.SC_FOUND);
        resp.setHeader("Location",getServletContext().getContextPath() + "/personal_page");

        resp.setHeader( "Cache-Control","no-cache, max-age=0, must-revalidate, no-store");

        Const.setRightEncoding(req,resp);

        cookieIdExists = false; // есть ли куки "запомнить"
        cookieId = null;  // хеш-функция для авторизации(через куки или формы)

//      Забираем данные из cookie
        cookies = req.getCookies();
//      Проверяем куки "запомнить пользователя"
        HashMap<String,String> hashMapCookie = CookieUtil.checkAndSaveCookieRemember(cookies);
        cookieIdExists = Boolean.valueOf(hashMapCookie.get(Const.KEY_REMEMBER_COOKIE));
        if(cookieIdExists){
            cookieId = hashMapCookie.get(Const.KEY_COOKIE_ID);
        }

        // Забираем данные из формы
        if(!cookieIdExists) {
            email = req.getParameter(Const.KEY_EMAIL);
            password = req.getParameter(Const.KEY_PASSWORD);
            String[] checkbox = req.getParameterValues(Const.KEY_REMEMBER_COOKIE);
            remember = checkbox != null;

            UserSessionUtil.saveInSession(req);
        }

        //Выбираем способ авторизации
        boolean authCheck;

        if(cookieId != null){
            authCheck = AuthenticationCheckUtil.checkData(cookieId,req,resp);
        }
        else {
            password = SecurityUtil.hashPassword(password);
            authCheck = AuthenticationCheckUtil.checkData(email,password,req,resp);
        }

        // проверка аутентификации и создание сессии
        if(authCheck) {

            if(!cookieIdExists) {
                UserSessionUtil.removeInSession(req);
            }

            //создаем сессию,добавляем атрибуты в виде авторизоаванного пользователя
            HttpSession session = req.getSession();
            session.setAttribute(Const.KEY_REMEMBER_WHILE_SESSION,true);

            addUserInSession(req,resp, cookieId,email);

        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    private void addUserInSession(HttpServletRequest req,HttpServletResponse resp,String cookieId,String email){

        try {
            UserDao userDao = new UserDao(DbWrapper.getConnection());
            User currentUser;
            if(cookieId != null){
               currentUser = userDao.findByCookieId(cookieId);
            } else {
                currentUser = userDao.findByEmail(email);
            }

            //зашли через пароль и добавили галочку "Запомнить"
            if(!cookieIdExists && remember) {
                cookieId = currentUser.getCookieId();
                CookieUtil.createCookieRemember(resp,remember, cookieId);
            }
            req.getSession().setAttribute(Const.KEY_CURRENT_USER,currentUser);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

}
