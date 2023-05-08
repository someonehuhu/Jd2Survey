package by.yatsukovich.domain.converter;

import by.yatsukovich.domain.hibernate.QuestionType;

import javax.persistence.AttributeConverter;

public class QuestionTypeConverter implements AttributeConverter<QuestionType, Integer> {
    @Override
    public Integer convertToDatabaseColumn(QuestionType attribute) {
        return null;
    }

    @Override
    public QuestionType convertToEntityAttribute(Integer dbData) {
        return null;
    }
}
