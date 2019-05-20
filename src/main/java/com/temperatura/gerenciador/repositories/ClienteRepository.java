package com.temperatura.gerenciador.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.temperatura.gerenciador.entities.Cliente;;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long>{

	Cliente findById(Long id);
	
}
