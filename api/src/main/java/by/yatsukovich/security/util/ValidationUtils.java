package by.yatsukovich.security.util;

import org.springframework.stereotype.Component;

@Component
public class ValidationUtils {

    public boolean positive(Long value) {
        return nonNull(value) && value > 0;
    }

    public boolean positive(Integer value) {
        return nonNull(value) && value > 0;
    }

    public boolean nonNull(Long value) {
        return value != null;
    }

    public boolean nonNull(Integer value) {
        return value != null;
    }
}
