package hello.mapper;

import hello.entity.WorldEntity;
import hello.model.World;
import org.springframework.stereotype.Component;

@Component
public class WorldMapper {

    public WorldEntity toEntity(World from) {
        return new WorldEntity(from.getId(), from.getRandomNumber());
    }

    public World toDto(WorldEntity from) {
        return new World(from.getId(), from.getRandomNumber());
    }

}
