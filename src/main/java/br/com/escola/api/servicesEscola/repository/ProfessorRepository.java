package br.com.escola.api.servicesEscola.repository;

import java.util.List;

import org.escola.model.ProfessorTurma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProfessorRepository extends JpaRepository<ProfessorTurma, Long> {

	
	@Query(value = "select * from ProfessorTurma professorTurma"
			+ "	left join Professor professor on professor.id = professorTurma.professor_id"
			+ "	left join Turma turma on turma.id = professorTurma.turma_id"
			+ "	where turma.id = ?1", nativeQuery = true)
	public List<ProfessorTurma> getProfessorTurmaNative(Long idTurma);
	

}
