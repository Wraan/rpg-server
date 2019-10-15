package com.rpg.repository.dnd.abilities;

import com.rpg.model.dnd.abilities.Skill;
import com.rpg.model.dnd.abilities.Trait;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SkillsRepository extends JpaRepository<Skill, Long> {
    List<Skill> findByNameIgnoreCaseContaining(String name);
}
