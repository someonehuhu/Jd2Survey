package by.yatsukovich.controller.mapper;

import by.yatsukovich.controller.dto.MailingDto;
import by.yatsukovich.controller.request.MailingCreateRequest;
import by.yatsukovich.domain.hibernate.Mailing;
import by.yatsukovich.domain.hibernate.Survey;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.Collections;
import java.util.List;

@Mapper(
        componentModel = "spring"
)
public interface MailingMapper {

    MailingDto mailingToMailingDto(Mailing mailing);

    Mailing mailingCreateRequestToMailing(MailingCreateRequest mailingCreateRequest);

    @Named("MailingsWithSurvey")
    default List<Mailing> questionResourcesToQuestions(List<MailingCreateRequest> sources, @Context Survey survey) {
        List<Mailing> mailings;
        if (sources != null) {
            mailings = sources.stream()
                    .map(questionCreateRequest -> {
                        Mailing mailing = mailingCreateRequestToMailing(questionCreateRequest);
                        mailing.setSurvey(survey);
                        return mailing;
                    })
                    .toList();
        } else {
            mailings = Collections.emptyList();
        }

        return mailings;
    }
}
