package com.app.vetsystemweb.config;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.stereotype.Service;

@Service
public class Util {

    final static Logger LOG = Logger.getLogger(Util.class.getName());
    
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
                    LOG.warning("Error to parsing date -> " + aux.toString());
                    return null;
                }
                //LOG.info("Error to parsing date -> " + aux.toString());
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
                LOG.warning("Error to parsing integer -> " + aux.toString());
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
                LOG.warning("Error to parsing Long number -> " + aux.toString());
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
                LOG.warning("Error to parsing float number -> " + aux.toString());
                return null;
            }
        }
        return null;
    }
}
