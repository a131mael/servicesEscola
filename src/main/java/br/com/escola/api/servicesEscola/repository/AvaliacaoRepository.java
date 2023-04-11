package br.com.escola.api.servicesEscola.repository;

import java.util.List;

import org.escola.enums.Serie;
import org.escola.model.Avaliacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long>, AvaliacaoRepositoryCustom {

	@Query(value = "select * from Avaliacao  ava"
			+ "	left join Professor professor on professor.id = ava.professor_id"
			+ "	where "
			+ " professor.id = ?1"
			+ "	and ava.anoletivo = ?2"
			+ "	and ava.bimestre = ?3"
			+ "	and ava.disciplina = ?4"
			+ "	and ava.serie = ?5"
			+ " order by ava.id", nativeQuery = true)
	public List<Avaliacao> getAvaliacaoNative(Long idProfessor, Integer anoletivo, Integer bimestre, int disciplina, Integer serie);

	
}
