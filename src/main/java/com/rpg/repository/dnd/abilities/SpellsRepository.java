package com.rpg.repository.dnd.abilities;

import com.rpg.model.application.Scenario;
import com.rpg.model.dnd.abilities.Spell;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SpellsRepository extends JpaRepository<Spell, Long> {
    List<Spell> findByNameIgnoreCaseContainingAndScenario(String name, Scenario scenario);
    List<Spell> findByScenario(Scenario scenario);
    List<Spell> findByNameIgnoreCaseContainingAndScenarioAndVisible(String name, Scenario scenario, boolean visible);
    List<Spell> findByScenarioAndVisible(Scenario scenario, boolean visible);
    boolean existsByNameAndScenario(String name, Scenario scenario);
    Optional<Spell> findByNameAndScenario(String name, Scenario scenario);
    void deleteByScenario(Scenario scenario);
}
