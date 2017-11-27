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
import java.util.regex.Pattern;

public class RegistrationCheckUtil {

    public static boolean checkRegistrationData(String email, String password, String passwordAgain,
                                                HttpServletRequest req, HttpServletResponse resp){

        Pattern patternPassword = Pattern.compile("[A-z0-9_-]{5}[A-z0-9_-]+");
        Pattern patternEmail = Pattern.compile("[A-z0-9-_]+@[A-z0-9-_]+\\.{1}[A-z0-9-_]+");
        boolean checked = true;
        try {
            if(!checkEmail(email)){
                throw new EnterException("Данный email уже занят");
            }
            if(!patternEmail.matcher(email).matches() ||  email.length() == 0){
                throw new EnterException("The email should consist of english characters, digits, '_','-' " +
                        "and be longer than 5 characters");
            }
            if (!patternPassword.matcher(password).matches() ||  password.length() == 0) {
                throw new EnterException("The password should consist of english characters, digits, '_','-' " +
                        "and be longer than 5 characters");
            }

            if (!password.equals(passwordAgain)) {
                throw new EnterException("Entered passwords must match");
            }

        } catch (EnterException enterException) {
            checked = false;

            registrationFailed(enterException,req,resp);

        }
        return checked;
    }

    private static void registrationFailed(EnterException enterException,
                                           HttpServletRequest req, HttpServletResponse resp){

        //создаем новую страницу,перенаправляем снова на авторизацию
        String errorMessage = enterException.getMessage();

        HttpSession session = req.getSession();
        session.setAttribute(Const.POPUP_DATA,errorMessage);

        // ошибка,форма не сохранена
        resp.setStatus(HttpServletResponse.SC_FOUND);
        resp.setHeader("Location","/registration_serv");

    }

    private static boolean checkEmail(String email){
        boolean checkEmail = true;
        try {
            List<String> emails = new UserDao(DbWrapper.getConnection()).findAllEmails();
            for(String emailStr : emails){
                if(email.equals(emailStr)){
                    checkEmail = false;
                    break;
                }
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
        return checkEmail;

    }
}
