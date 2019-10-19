package com.rpg.repository.dnd.types;

import com.rpg.model.application.Scenario;
import com.rpg.model.dnd.types.MagicSchool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MagicSchoolsRepository  extends JpaRepository<MagicSchool, Long> {
    List<MagicSchool> findByNameIgnoreCaseContaining(String name);
    List<MagicSchool> findByNameIgnoreCaseContainingAndScenario(String name, Scenario scenario);
    List<MagicSchool> findByScenario(Scenario scenario);
    boolean existsByNameAndScenario(String name, Scenario scenario);
}
