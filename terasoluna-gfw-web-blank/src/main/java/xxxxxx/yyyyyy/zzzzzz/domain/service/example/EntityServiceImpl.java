package xxxxxx.yyyyyy.zzzzzz.domain.service.example;

import java.util.List;

import javax.inject.Inject;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import xxxxxx.yyyyyy.zzzzzz.domain.model.Entity;
import xxxxxx.yyyyyy.zzzzzz.domain.repository.example.EntityRepository;

@Service
@Transactional
public class EntityServiceImpl implements EntityService {

    private static final Sort sortForFindAll = new Sort(Direction.DESC, "id");

    @Inject
    EntityRepository entityRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Entity> getEntities() {
        return entityRepository.findAll(sortForFindAll);
    }

    @Override
    @Transactional(readOnly = true)
    public Entity getEntity(Integer id) {
        return entityRepository.findOne(id);
    }

    @Override
    public Entity saveEntity(Entity entity) {
        return entityRepository.save(entity);
    }

}
