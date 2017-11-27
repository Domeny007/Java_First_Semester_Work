package utils.CheckUtils;

import exceptions.AddError;
import utils.Const;
import utils.DateUtil;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.sql.Date;

public class AddMovieCheck {

    public static boolean checkMovieData(HttpServletRequest req,String name,
                                         String content, String photo, String budget,
                                         String money, String year,String actors,
                                         String directors,String countries,
                                         String genres){

        boolean check = true;
        try {
            checkInitials(name,content);
            checkPhoto(req,photo);
            checkMoney(budget,money);
            checkYear(year);
            checkOther(actors,directors,countries,genres);
        } catch (AddError addError) {
            req.getSession().setAttribute(Const.POPUP_DATA,addError.getMessage());
            check = false;
        }
        return check;
    }

    private static void checkInitials(String name,String surname) throws AddError {

        if(name.trim().equals("")) {
            throw new AddError("Должно быть введено не пустое название");
        }

        if(surname.trim().equals("")) {
            throw new AddError("Должно быть заполено описание к фильму");
        }
    }

    private static void checkMoney(String budget, String money) throws AddError {
        try {
            Integer.valueOf(budget);
        }catch (NumberFormatException ex){
            throw new AddError("Бюджет указан неверно,возможно,введено не целое число");
        }
        try {
            Integer.valueOf(money);
        }catch (NumberFormatException ex){
            throw new AddError("Касса указана неверно,возможно,введено не целое число");
        }
    }

    private static void checkOther(String actors, String directors,String countries,String genres) throws AddError {
        if(actors.equals("")){
            throw new AddError("Актеры не добавлены");
        }
        if(directors.equals("")){
            throw new AddError("Страны не добавлены");
        }
        if(countries.equals("")){
            throw new AddError("Режиссеры не добавлены");
        }
        if(genres.equals("")){
            throw new AddError("Жанры не добавлены");
        }

    }

    private static void checkYear(String year) throws AddError {
        Date inputDate = DateUtil.parseYearDateFromStr(year);
        if (inputDate.after(new java.util.Date())) {
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
