package com.rpg.repository.dnd.types;

import com.rpg.model.dnd.types.MagicSchool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MagicSchoolsRepository  extends JpaRepository<MagicSchool, Long> {
}
