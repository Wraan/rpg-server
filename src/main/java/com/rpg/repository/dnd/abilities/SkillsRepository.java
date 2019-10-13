package com.rpg.repository.dnd.abilities;

import com.rpg.model.dnd.abilities.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkillsRepository extends JpaRepository<Skill, Long> {
}
