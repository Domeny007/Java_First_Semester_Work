package utils.SessionUtils;

import utils.Const;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class PeopleSessionUtil {

    private String name;

    private String surname;

    private String birthday;

    private Integer motherland;

    private String photo;

    private void getParameters(HttpServletRequest req,String type){

        if(type.equals(Const.KEY_ACTOR_TYPE)) {

            name = req.getParameter(Const.KEY_ACTOR_NAME);

            surname = req.getParameter(Const.KEY_ACTOR_SURNAME);

            birthday = req.getParameter(Const.KEY_ACTOR_BIRTHDAY);

            motherland = Integer.valueOf(req.getParameter(Const.KEY_ACTOR_MOTHERLAND));

            photo = req.getParameter(Const.KEY_ACTOR_PHOTO);
        }else{
            name = req.getParameter(Const.KEY_DIRECTOR_NAME);

            surname = req.getParameter(Const.KEY_DIRECTOR_SURNAME);

            birthday = req.getParameter(Const.KEY_DIRECTOR_BIRTHDAY);

            motherland = Integer.valueOf(req.getParameter(Const.KEY_DIRECTOR_MOTHERLAND));

            photo = req.getParameter(Const.KEY_DIRECTOR_PHOTO);
        }
    }

    public void addDataToSession(HttpServletRequest req,String type){
        HttpSession session = req.getSession();

        getParameters(req,type);

        if(type.equals(Const.KEY_ACTOR_TYPE)) {
            session.setAttribute(Const.KEY_ACTOR_NAME, name);
            session.setAttribute(Const.KEY_ACTOR_SURNAME, surname);
            session.setAttribute(Const.KEY_ACTOR_MOTHERLAND, motherland);
            session.setAttribute(Const.KEY_ACTOR_BIRTHDAY, birthday);
            session.setAttribute(Const.KEY_ACTOR_PHOTO, photo);
        }else{
            session.setAttribute(Const.KEY_DIRECTOR_NAME, name);
            session.setAttribute(Const.KEY_DIRECTOR_SURNAME, surname);
            session.setAttribute(Const.KEY_DIRECTOR_MOTHERLAND, motherland);
            session.setAttribute(Const.KEY_DIRECTOR_BIRTHDAY, birthday);
            session.setAttribute(Const.KEY_DIRECTOR_PHOTO, photo);
        }
    }
}
