package com.rpg.repository.dnd.abilities;

import com.rpg.model.dnd.abilities.Trait;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TraitsRepository extends JpaRepository<Trait, Long> {
}