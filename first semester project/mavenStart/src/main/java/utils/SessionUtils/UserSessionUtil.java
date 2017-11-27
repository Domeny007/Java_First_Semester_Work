package utils.SessionUtils;

import utils.Const;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class UserSessionUtil {

    public static void saveInSession(HttpServletRequest req) {
        HttpSession session = req.getSession();

        String email = req.getParameter(Const.KEY_EMAIL);
        String[] checkbox = req.getParameterValues(Const.KEY_REMEMBER_COOKIE);
        boolean remember = checkbox != null;

        session.setAttribute(Const.KEY_EMAIL, email);
        session.setAttribute(Const.KEY_REMEMBER_COOKIE, remember);

    }

    public static void removeInSession(HttpServletRequest req) {
        HttpSession session = req.getSession();
        session.removeAttribute(Const.KEY_EMAIL);
        session.removeAttribute(Const.KEY_REMEMBER_COOKIE);
    }

    public static void removeRegistrSession(HttpServletRequest req) {
        HttpSession session = req.getSession();
        session.removeAttribute(Const.KEY_EMAIL);
        session.removeAttribute(Const.KEY_USERNAME);
        session.removeAttribute(Const.KEY_COUNTRY);
        session.removeAttribute(Const.KEY_GENDER);
        session.removeAttribute(Const.KEY_COUNTRIES);
    }

    public static void saveRegistrSession(HttpServletRequest req){

        String email = req.getParameter(Const.KEY_EMAIL);
        String username = req.getParameter(Const.KEY_USERNAME);
        Integer country = Integer.valueOf(req.getParameterValues(Const.KEY_COUNTRY)[0]);
        String gender = req.getParameterValues(Const.KEY_GENDER)[0];

        HttpSession session = req.getSession();
        session.setAttribute(Const.KEY_EMAIL,email);
        session.setAttribute(Const.KEY_USERNAME,username);
        session.setAttribute(Const.KEY_COUNTRY,country);
        session.setAttribute(Const.KEY_GENDER,gender);
    }
}
