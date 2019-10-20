package com.rpg.repository.dnd.abilities;

import com.rpg.model.application.Scenario;
import com.rpg.model.dnd.abilities.Skill;
import com.rpg.model.dnd.abilities.Trait;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SkillsRepository extends JpaRepository<Skill, Long> {
    List<Skill> findByNameIgnoreCaseContaining(String name);
    List<Skill> findByNameIgnoreCaseContainingAndScenario(String name, Scenario scenario);
    List<Skill> findByScenario(Scenario scenario);
    boolean existsByNameAndScenario(String name, Scenario scenario);
}
