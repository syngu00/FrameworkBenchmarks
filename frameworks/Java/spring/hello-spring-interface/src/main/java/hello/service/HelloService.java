package hello.service;

import hello.model.Fortune;
import hello.model.World;

import java.util.List;

public interface HelloService {
	World getWorld(int id);

	World updateWorld(World world);

	List<Fortune> fortunes();
}
