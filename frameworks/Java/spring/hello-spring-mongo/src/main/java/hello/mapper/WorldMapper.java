package hello.mapper;

import hello.document.WorldDocument;
import org.springframework.stereotype.Component;

@Component
public class WorldMapper {

    public WorldDocument toEntity(hello.model.World from) {
        return new WorldDocument(from.getId(), from.getRandomNumber());
    }

    public hello.model.World toDto(WorldDocument from) {
        return new hello.model.World(from.getId(), from.getRandomNumber());
    }

}
