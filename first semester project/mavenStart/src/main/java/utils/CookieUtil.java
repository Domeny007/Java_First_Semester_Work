package utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;

public class CookieUtil {

    public static void createCookieRemember(HttpServletResponse resp, boolean remember,
                                      String cookieId){
        Cookie cookieRemember = new Cookie(Const.KEY_REMEMBER_COOKIE, String.valueOf(remember));
        cookieRemember.setMaxAge(60 * 60 * 24 * 30);
        resp.addCookie(cookieRemember);

        Cookie cookieUserId;
        try {
        cookieUserId = new Cookie(Const.KEY_COOKIE_ID, URLEncoder.encode(cookieId,"UTF-8"));
        cookieUserId.setMaxAge(60 * 60 * 24 * 30);
        resp.addCookie(cookieUserId);
        } catch (UnsupportedEncodingException e) {
             e.getMessage();
        }
    }

    public static HashMap<String,String> checkAndSaveCookieRemember(Cookie [] cookies) throws UnsupportedEncodingException {
        boolean cookieMD5KeyExists = false;
        String cookieId;
        HashMap<String,String> hashMapCookie = new HashMap<>();
        for (Cookie cookie : cookies) {

            switch (cookie.getName()){

                case Const.KEY_COOKIE_ID:
                    cookie.setMaxAge(60*60*24*365);

                    cookieId = URLDecoder.decode(cookie.getValue(),"UTF-8");
                    hashMapCookie.put(Const.KEY_COOKIE_ID,cookieId);
                    cookieMD5KeyExists = true;
                    break;

                case Const.KEY_REMEMBER_COOKIE:
                    cookie.setMaxAge(60*60*24*365);
                    break;

            }

        }
        hashMapCookie.put(Const.KEY_REMEMBER_COOKIE, String.valueOf(cookieMD5KeyExists));
        return hashMapCookie;

    }
}
