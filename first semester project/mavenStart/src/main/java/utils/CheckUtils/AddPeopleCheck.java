package utils.CheckUtils;

import exceptions.AddError;
import utils.Const;
import utils.DateUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.sql.Date;

public class AddPeopleCheck {

    public static boolean checkPeopleData(HttpServletRequest req,String name,String surname, String photo, String birthday){
      boolean check = true;
        try {
            checkInitials(name,surname);
            checkPhoto(req,photo);
            checkYear(birthday);
        } catch (AddError addError) {
            req.getSession().setAttribute(Const.POPUP_DATA,addError.getMessage());
            check = false;
        }
        return check;
    }

    private static void checkInitials(String name,String surname) throws AddError {

        if(name.trim().equals("")) {
            throw new AddError("Должно быть введено не пустое имя");
        }

        if(surname.trim().equals("")) {
            throw new AddError("Должна быть введена не пустая фамилия");
        }
    }

    private static void checkYear(String year) throws AddError {
       Date inputDate =  DateUtil.parseYearDateFromStr(year);
       if(inputDate.after(new java.util.Date())) {
           throw new AddError("Введенная дата должна быть меньше текущей");
       }
    }

    private static void checkPhoto(HttpServletRequest req, String photo) throws AddError {
        File file = new File(req.getServletContext().getRealPath(photo));
        if(!file.exists()){
            throw  new AddError("Изображения нет в базе,введите верный путь");
        }

    }
}
