package com.rpg.repository.dnd.abilities;

import com.rpg.model.dnd.abilities.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LanguagesRepository extends JpaRepository<Language, Long> {
}
