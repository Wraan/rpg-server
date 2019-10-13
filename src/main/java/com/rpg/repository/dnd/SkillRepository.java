package com.rpg.repository.dnd;

import com.rpg.model.dnd.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SkillRepository extends JpaRepository<Skill, Long> {
}
