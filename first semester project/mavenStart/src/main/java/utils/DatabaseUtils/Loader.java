package utils.DatabaseUtils;

import database.dao.CountryDao;
import database.entity.Country;
import exceptions.DbException;
import utils.Const;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

public class Loader {

    public static void addCountries(HttpServletRequest req,String type){
        String countriesKey = null;
        switch (type){
            case "movie":
                countriesKey = Const.KEY_MOVIE_COUNTRIES;
                break;

            case "usual":
                countriesKey = Const.KEY_COUNTRIES;
                break;
        }
        List<Country> countries = new ArrayList<>();
        try {
            CountryDao countryDao = new CountryDao(DbWrapper.getConnection());
            countries = countryDao.findAll();
        } catch (DbException e) {
            e.printStackTrace();
        }

        HttpSession session = req.getSession();
        session.setAttribute(countriesKey,countries);

    }

}
