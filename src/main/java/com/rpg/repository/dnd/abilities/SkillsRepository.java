package com.rpg.repository.dnd.abilities;

import com.rpg.model.application.Scenario;
import com.rpg.model.dnd.abilities.Skill;
import com.rpg.model.dnd.abilities.Trait;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SkillsRepository extends JpaRepository<Skill, Long> {
    List<Skill> findByNameIgnoreCaseContainingAndScenario(String name, Scenario scenario);
    List<Skill> findByScenario(Scenario scenario);
    List<Skill> findByNameIgnoreCaseContainingAndScenarioAndVisible(String name, Scenario scenario, boolean visible);
    List<Skill> findByScenarioAndVisible(Scenario scenario, boolean visible);
    boolean existsByNameAndScenario(String name, Scenario scenario);
    Optional<Skill> findByNameAndScenario(String name, Scenario scenario);
    void deleteByScenario(Scenario scenario);
}
