package com.rpg.repository.dnd.types;

import com.rpg.model.application.Scenario;
import com.rpg.model.dnd.types.DamageType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DamageTypesRepository extends JpaRepository<DamageType, Long> {
    List<DamageType> findByNameIgnoreCaseContaining(String name);
    List<DamageType> findByNameIgnoreCaseContainingAndScenario(String name, Scenario scenario);
    List<DamageType> findByScenario(Scenario scenario);
    boolean existsByNameAndScenario(String name, Scenario scenario);
    Optional<DamageType> findByNameAndScenario(String name, Scenario scenario);
}
