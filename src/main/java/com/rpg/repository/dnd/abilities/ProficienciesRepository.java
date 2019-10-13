package com.rpg.repository.dnd.abilities;

import com.rpg.model.dnd.abilities.Proficiency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProficienciesRepository extends JpaRepository<Proficiency, Long> {
}
