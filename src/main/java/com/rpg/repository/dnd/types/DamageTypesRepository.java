package com.rpg.repository.dnd.types;

import com.rpg.model.dnd.types.DamageType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DamageTypesRepository extends JpaRepository<DamageType, Long> {
    List<DamageType> findByNameIgnoreCaseContaining(String name);
}
