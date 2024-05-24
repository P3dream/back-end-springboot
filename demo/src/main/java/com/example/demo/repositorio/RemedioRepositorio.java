package com.example.demo.repositorio;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.model.Remedio;

public interface RemedioRepositorio extends JpaRepository<Remedio, Long>{

	List<Remedio> findAllByAtivoTrue();
	
}
