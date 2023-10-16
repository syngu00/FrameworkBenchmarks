package hello.mapper;

import hello.document.FortuneDocument;
import org.springframework.stereotype.Component;

@Component
public class FortuneMapper {

    public FortuneDocument toEntity(hello.model.Fortune from) {
        return new FortuneDocument(from.getId(), from.getMessage());
    }

    public hello.model.Fortune toDto(FortuneDocument from) {
        return new hello.model.Fortune(from.getId(), from.getMessage());
    }

}
