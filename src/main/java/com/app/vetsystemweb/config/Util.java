package com.app.vetsystemweb.config;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.stereotype.Service;

@Service
public class Util {

    public String castString(Object aux) {
        //Object aux = r.get(colName);
        if (aux != null) {
            return aux.toString();
        }
        return null;
    }

    public Date castDate(Object aux) {
        //Object aux = r.get(colName);
        if (aux != null) {
            try {
                return DateUtils.parseDate(aux.toString(), new String[] { "dd/MM/yyyy", "dd-MM-yyyy", "yyyy/MM/dd", "yyyy-MM-dd" });
                //return new SimpleDateFormat("dd/MM/yyyy").parse(aux.toString());
            } catch (ParseException ex) {
                try {
                    return new SimpleDateFormat("yyyy/MM/dd").parse(aux.toString());
                } catch (ParseException ex1) {
                    System.out.println("Error to parsing date -> " + aux.toString());
                    return null;
                }
                //System.out.println("Error to parsing date -> " + aux.toString());
                //return null;
            }
        }
        return null;
    }

    public Integer castInteger(Object aux) {
        //Object aux = r.get(colName);
        if (aux != null) {
            try {
                return Integer.parseInt(aux.toString());
            } catch (NumberFormatException ex) {
                System.out.println("Error to parsing integer -> " + aux.toString());
                return null;
            }
        }
        return null;
    }

    public Long castLong(Object aux) {
        //Object aux = r.get(colName);
        if (aux != null) {
            try {
                return Long.parseLong(aux.toString());
            } catch (NumberFormatException ex) {
                System.out.println("Error to parsing Long number -> " + aux.toString());
                return null;
            }
        }
        return null;
    }

    public Float castfloat(Object aux) {
        //Object aux = r.get(colName);
        if (aux != null) {
            try {
                return Float.parseFloat(aux.toString());
            } catch (NumberFormatException ex) {
                System.out.println("Error to parsing float number -> " + aux.toString());
                return null;
            }
        }
        return null;
    }
}
