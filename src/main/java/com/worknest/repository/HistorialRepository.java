package com.worknest.repository;

import com.worknest.domain.Historial;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Historial entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HistorialRepository extends JpaRepository<Historial, Long> {

}
