package hello;

import hello.model.Fortune;
import hello.model.World;
import hello.service.HelloService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JdbcHelloService implements HelloService {
    private final JdbcTemplate jdbcTemplate;

    public JdbcHelloService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public World getWorld(int id) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM world WHERE id = ?",
                    (rs, rn) -> new World(rs.getInt("id"), rs.getInt("randomnumber")), id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public World updateWorld(World world) {
        jdbcTemplate.update("UPDATE world SET randomnumber = ? WHERE id = ?", world.getRandomNumber(), world.getId());
        return world;
    }

    @Override
    public List<Fortune> fortunes() {
        return jdbcTemplate.query("SELECT * FROM fortune",
                (rs, rn) -> new Fortune(rs.getInt("id"), rs.getString("message")));
    }
}
