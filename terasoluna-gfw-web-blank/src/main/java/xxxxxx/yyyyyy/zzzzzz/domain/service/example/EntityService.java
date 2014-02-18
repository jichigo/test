package xxxxxx.yyyyyy.zzzzzz.domain.service.example;

import java.util.List;

import xxxxxx.yyyyyy.zzzzzz.domain.model.Entity;

public interface EntityService {
    List<Entity> getEntities();

    Entity getEntity(Integer id);

    Entity saveEntity(Entity entity);

}
