package com.rpg.repository;

import com.rpg.model.security.OAuthClientDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OAuthClientDetailsRepository  extends JpaRepository<OAuthClientDetails, String> {
}
