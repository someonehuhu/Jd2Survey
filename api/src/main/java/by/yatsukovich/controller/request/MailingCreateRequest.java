package by.yatsukovich.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Pattern;

@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Validated
public class MailingCreateRequest {

    @Pattern(regexp = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")
    private String mailAddress;
}
