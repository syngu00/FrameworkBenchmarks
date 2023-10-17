package hello.controller;

import hello.model.Fortune;
import hello.model.World;
import hello.service.CacheHelloService;
import hello.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.stream.IntStream;

import static java.util.Comparator.comparing;

@RestController
public final class HelloController {

    private static final int MIN_WORLD_NUMBER = 1;
    private static final int MAX_WORLD_NUMBER_PLUS_ONE = 10_001;

    private final HelloService helloService;
    private final CacheHelloService cacheHelloService;

    @Autowired
    public HelloController(HelloService helloService, CacheHelloService cacheHelloService) {
        this.helloService = helloService;
        this.cacheHelloService = cacheHelloService;
    }

    @GetMapping(value = "/plaintext", produces = MediaType.TEXT_PLAIN_VALUE)
    String plaintext() {
        return "Hello, World!";
    }

    @GetMapping("/json")
    Message json() {
        return new Message("Hello, World!");
    }

    @GetMapping("/db")
    World db() {
        return helloService.getWorld(randomWorldNumber());
    }

    @GetMapping("/queries")
    World[] queries(@RequestParam(required = false) String queries) {
        return queries(parseQueryCount(queries), helloService::getWorld);

    }

    @GetMapping("/cached_queries")
    World[] cacheQueries(@RequestParam(required = false) String queries) {
        return queries(parseQueryCount(queries), cacheHelloService::getWorld);
    }

    World[] queries(int queries, IntFunction<World> func) {
        return randomWorldNumbers().mapToObj(func)
                .limit(queries)
                .toArray(World[]::new);
    }

    @GetMapping("/updates")
    World[] updates(@RequestParam(required = false) String queries) {
        return randomWorldNumbers().mapToObj(helloService::getWorld).map(world -> {
            // Ensure that the new random number is not equal to the old one.
            // That would cause the JPA-based implementation to avoid sending the
            // UPDATE query to the database, which would violate the test
            // requirements.

            // Locally the records doesn't exist, maybe in the yours is ok but we need to
            // make this check
            if (world == null) {
                return null;
            }

            int newRandomNumber;
            do {
                newRandomNumber = randomWorldNumber();
            } while (newRandomNumber == world.getRandomNumber());

            world.setRandomNumber(newRandomNumber);

            return helloService.updateWorld(world);
        }).limit(parseQueryCount(queries)).toArray(World[]::new);
    }

    @GetMapping("/fortunes")
    @ModelAttribute("fortunes")
    List<Fortune> fortunes() {
        List<Fortune> fortunes = helloService.fortunes();

        fortunes.add(new Fortune(0, "Additional fortune added at request time."));
        fortunes.sort(comparing(Fortune::getMessage));
        return fortunes;
    }

    private static int randomWorldNumber() {
        return ThreadLocalRandom.current().nextInt(MIN_WORLD_NUMBER, MAX_WORLD_NUMBER_PLUS_ONE);
    }

    private static IntStream randomWorldNumbers() {
        return ThreadLocalRandom.current().ints(MIN_WORLD_NUMBER, MAX_WORLD_NUMBER_PLUS_ONE)
                // distinct() allows us to avoid using Hibernate's first-level cache in
                // the JPA-based implementation. Using a cache like that would bypass
                // querying the database, which would violate the test requirements.
                .distinct();
    }

    private static int parseQueryCount(String textValue) {
        if (textValue == null) {
            return 1;
        }
        int parsedValue;
        try {
            parsedValue = Integer.parseInt(textValue);
        } catch (NumberFormatException e) {
            return 1;
        }
        return Math.min(500, Math.max(1, parsedValue));
    }

    record Message(String message) {
    }
}
