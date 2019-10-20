package com.rpg.repository.dnd.abilities;

import com.rpg.model.application.Scenario;
import com.rpg.model.dnd.abilities.Proficiency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProficienciesRepository extends JpaRepository<Proficiency, Long> {
    List<Proficiency> findByNameIgnoreCaseContaining(String name);
    List<Proficiency> findByNameIgnoreCaseContainingAndScenario(String name, Scenario scenario);
    List<Proficiency> findByScenario(Scenario scenario);
    boolean existsByNameAndScenario(String name, Scenario scenario);
}
