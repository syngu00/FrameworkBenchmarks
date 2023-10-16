package hello.service;

import hello.mapper.FortuneMapper;
import hello.mapper.WorldMapper;
import hello.model.Fortune;
import hello.model.World;
import hello.repository.FortuneRepository;
import hello.repository.WorldRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JpaHelloService implements HelloService {
    private final WorldRepository worldRepository;
    private final FortuneRepository fortuneRepository;
    private final WorldMapper worldMapper;
    private final FortuneMapper fortuneMapper;

    @Autowired
    public JpaHelloService(WorldRepository worldRepository, FortuneRepository fortuneRepository, WorldMapper worldMapper, FortuneMapper fortuneMapper) {
        this.worldRepository = worldRepository;
        this.fortuneRepository = fortuneRepository;
        this.worldMapper = worldMapper;
        this.fortuneMapper = fortuneMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public World getWorld(int id) {
        return worldRepository.findById(id).map(worldMapper::toDto).orElse(null);
    }

    @Override
    public World updateWorld(World world) {
        worldRepository.setNewValue(world.getId(), world.getRandomNumber());
        return world;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Fortune> fortunes() {
        return fortuneRepository.findAll().stream().parallel().map(fortuneMapper::toDto).collect(Collectors.toList());
    }
}
