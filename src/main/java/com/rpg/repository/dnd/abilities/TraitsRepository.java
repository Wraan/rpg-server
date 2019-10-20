package com.rpg.repository.dnd.abilities;

import com.rpg.model.application.Scenario;
import com.rpg.model.dnd.abilities.Trait;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TraitsRepository extends JpaRepository<Trait, Long> {
    List<Trait> findByNameIgnoreCaseContaining(String name);
    List<Trait> findByNameIgnoreCaseContainingAndScenario(String name, Scenario scenario);
    List<Trait> findByScenario(Scenario scenario);
    boolean existsByNameAndScenario(String name, Scenario scenario);
}
