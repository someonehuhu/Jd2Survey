package by.yatsukovich.util;

import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Calendar;

@Component
public class TimestampUtil {

    public long getCurrentTimeMillis() {
        return Calendar.getInstance().getTimeInMillis();
    }

    public Timestamp now() {
        return new Timestamp(getCurrentTimeMillis());
    }
}
