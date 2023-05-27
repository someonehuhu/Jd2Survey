package by.yatsukovich.controller.mapper;

import by.yatsukovich.controller.dto.SurveyDto;
import by.yatsukovich.controller.request.SurveyCreateRequest;
import by.yatsukovich.domain.hibernate.Survey;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(
        componentModel = "spring",
        uses = {
                QuestionMapper.class,
                MailingMapper.class
        }
)
public interface SurveyMapper {

    SurveyDto surveyToSurveyDTO(Survey source);

    @Mapping(target = "questions", qualifiedByName = "QuestionsWithSurvey")
    @Mapping(target = "mailings", qualifiedByName = "MailingsWithSurvey")
    Survey surveyCreateRequestToSurvey(SurveyCreateRequest surveyCreateRequest, @MappingTarget Survey survey, @Context Survey context);

    default Survey surveyCreateRequestToSurvey(SurveyCreateRequest surveyCreateRequest, @MappingTarget Survey survey) {
        return surveyCreateRequestToSurvey(surveyCreateRequest, survey, survey);
    }


}
