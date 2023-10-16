package hello.mapper;

import hello.entity.FortuneEntity;
import hello.model.Fortune;
import org.springframework.stereotype.Component;

@Component
public class FortuneMapper {

    public FortuneEntity toEntity(Fortune from) {
        return new FortuneEntity(from.getId(), from.getMessage());
    }

    public Fortune toDto(FortuneEntity from) {
        return new Fortune(from.getId(), from.getMessage());
    }

}
