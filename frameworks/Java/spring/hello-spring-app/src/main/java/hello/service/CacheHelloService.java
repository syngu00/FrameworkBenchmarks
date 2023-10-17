package hello.service;

import hello.model.World;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service

public class CacheHelloService {

    private final HelloService helloService;

    @Autowired
    public CacheHelloService(HelloService helloService) {
        this.helloService = helloService;
    }

    @Cacheable("word")
    public World getWorld(int id) {
        return helloService.getWorld(id);
    }
}
