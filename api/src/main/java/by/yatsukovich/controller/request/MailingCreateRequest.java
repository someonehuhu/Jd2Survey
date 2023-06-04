package by.yatsukovich.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Object containing mail address")
public class MailingCreateRequest {

    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "test@mail.com", type = "string", description = "mail address to send notifications")
    @Pattern(regexp = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")
    private String mailAddress;
}
