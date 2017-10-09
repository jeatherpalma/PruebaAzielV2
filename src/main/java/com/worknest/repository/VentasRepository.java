package com.worknest.repository;

import com.worknest.domain.Ventas;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Ventas entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VentasRepository extends JpaRepository<Ventas, Long> {

}
