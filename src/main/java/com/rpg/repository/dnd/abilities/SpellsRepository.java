package com.rpg.repository.dnd.abilities;

import com.rpg.model.dnd.abilities.Spell;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpellsRepository extends JpaRepository<Spell, Long> {
}
