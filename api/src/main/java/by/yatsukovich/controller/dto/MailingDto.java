package by.yatsukovich.controller.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class MailingDto {

    private Long id;

    private String mailAddress;
}
