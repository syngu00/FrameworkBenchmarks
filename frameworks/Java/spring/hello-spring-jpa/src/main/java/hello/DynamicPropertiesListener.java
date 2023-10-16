package hello;

import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.MapPropertySource;

import java.util.Map;

public class DynamicPropertiesListener implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {
    private static final int MAXIMUM_POOL_SIZE = Runtime.getRuntime().availableProcessors() * 2;

    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
        event.getEnvironment()
                .getPropertySources()
                .addFirst(new MapPropertySource("dynamic-source", Map.of("spring.datasource.hikari.maximum-pool-size", MAXIMUM_POOL_SIZE)));
    }
}
