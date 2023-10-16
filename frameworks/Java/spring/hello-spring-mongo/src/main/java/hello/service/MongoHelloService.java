package hello.service;

import hello.document.WorldDocument;
import hello.mapper.FortuneMapper;
import hello.mapper.WorldMapper;
import hello.model.Fortune;
import hello.model.World;
import hello.repository.FortuneRepository;
import hello.repository.WorldRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MongoHelloService implements HelloService {
    private final WorldRepository worldRepository;
    private final FortuneRepository fortuneRepository;
    private final WorldMapper worldMapper;
    private final FortuneMapper fortuneMapper;

    @Autowired
    public MongoHelloService(WorldRepository worldRepository, FortuneRepository fortuneRepository, WorldMapper worldMapper, FortuneMapper fortuneMapper) {
        this.worldRepository = worldRepository;
        this.fortuneRepository = fortuneRepository;
        this.worldMapper = worldMapper;
        this.fortuneMapper = fortuneMapper;
    }

    @Override
    public World getWorld(int id) {
        return worldRepository.findById(id).map(worldMapper::toDto).orElse(null);
    }

    @Override
    public World updateWorld(World world) {
        WorldDocument entity = worldMapper.toEntity(world);

        return worldMapper.toDto(worldRepository.save(entity));
    }

    @Override
    public List<Fortune> fortunes() {
        return fortuneRepository.findAll().stream().parallel().map(fortuneMapper::toDto).collect(Collectors.toList());
    }
}
