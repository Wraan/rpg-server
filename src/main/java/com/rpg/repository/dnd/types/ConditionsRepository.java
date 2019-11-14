package com.rpg.repository.dnd.types;

import com.rpg.model.application.Scenario;
import com.rpg.model.dnd.types.Condition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConditionsRepository extends JpaRepository<Condition, Long> {
    List<Condition> findByNameIgnoreCaseContaining(String name);
    List<Condition> findByNameIgnoreCaseContainingAndScenario(String name, Scenario scenario);
    List<Condition> findByScenario(Scenario scenario);
    boolean existsByNameAndScenario(String name, Scenario scenario);
    Condition findByNameAndScenario(String name, Scenario scenario);
}
