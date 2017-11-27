package utils.CheckUtils;

import database.dao.UserDao;
import exceptions.DbException;
import exceptions.EnterException;
import utils.Const;
import utils.DatabaseUtils.DbWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class AuthenticationCheckUtil {

    public static boolean checkData(String cookieId,HttpServletRequest req, HttpServletResponse resp){

        boolean checked = false;

        try {
//          считываем cookieId
            String cookieIdFromDb = "";
            UserDao userDao  = new UserDao(DbWrapper.getConnection());
            List<String> cookiesId = userDao.findAllCookiesId();
            for(String cookieID : cookiesId){
                cookieIdFromDb = cookieID;
                if(cookieIdFromDb.equals(cookieId)){
                    checked = true;
                    break;
                }
            }
            if(!checked){
                throw  new EnterException("You have to write correct email and password");
            }

        }catch(EnterException enterException) {

            checked = false;

            authenticationFailed(enterException,req,resp);

        } catch (DbException e) {
            e.getMessage();
        }

        return checked;
    }

    public static boolean checkData(String email,String password,HttpServletRequest req, HttpServletResponse resp){

        boolean checked = false;


        try {

            if(email == null){
                throw  new EnterException("You have to write correct email and password");
            }

//          находим пароль
            UserDao userDao  = new UserDao(DbWrapper.getConnection());
            String findPassword = userDao.findPassword(email);
            checked = findPassword.equals(password);
            if(!checked){
                throw  new EnterException("You have to write correct email and password");
            }

        }catch(EnterException enterException) {

            checked = false;

            authenticationFailed(enterException,req,resp);

        } catch (DbException e) {
            e.getMessage();
        }

        return checked;
    }


    private static void authenticationFailed(EnterException enterException,
                                             HttpServletRequest req, HttpServletResponse resp){

        // ошибка,форма не сохранена
        resp.setStatus(HttpServletResponse.SC_FOUND);
        resp.setHeader("Location",req.getServletContext().getContextPath() + "index.jsp");

        //создаем новую страницу,перенаправляем снова на авторизацию
        String errorMessage = enterException.getMessage();
        HttpSession session = req.getSession();
        session.setAttribute(Const.POPUP_DATA,errorMessage);

    }

}
