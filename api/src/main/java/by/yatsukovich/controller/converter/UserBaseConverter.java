package by.yatsukovich.controller.converter;

import by.yatsukovich.controller.request.UserCreateRequest;
import by.yatsukovich.domain.hibernate.User;
import org.springframework.core.convert.converter.Converter;

public abstract class UserBaseConverter<S, T>  implements Converter<S, T> {

    /*public User doConvert(User userToConvert, UserCreateRequest userCreateRequest) {
        userToConvert.s
    }*/
}
