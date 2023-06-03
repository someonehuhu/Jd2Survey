package by.yatsukovich.controller.mapper;

import by.yatsukovich.controller.request.AnswerCreateRequest;
import by.yatsukovich.domain.hibernate.view.AnswerView;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(
        componentModel = "spring"
)
public interface AnswerViewMapper {

    List<AnswerView> answerCreateRequestsToAnswerView(List<AnswerCreateRequest> answerCreateRequests);
}
