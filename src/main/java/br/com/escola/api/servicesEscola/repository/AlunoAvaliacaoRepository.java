package br.com.escola.api.servicesEscola.repository;

import java.util.List;

import org.escola.enums.BimestreEnum;
import org.escola.enums.DisciplinaEnum;
import org.escola.enums.Serie;
import org.escola.model.Aluno;
import org.escola.model.AlunoAvaliacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AlunoAvaliacaoRepository extends JpaRepository<AlunoAvaliacao, Long> , AlunoAvaliacaoRepositoryCustom{

	@Query(value = "select * from alunoavaliacao  av"
			+ "	left join  aluno aluno on av.aluno_id = aluno.id"
			+ "	left join avaliacao avaliacao on av.avaliacao_id = avaliacao.id"
			+ "	left join AlunoTurma alunoTurma on alunoTurma.aluno_id = av.aluno_id"
			+ "	left join Turma turma on turma.id = alunoTurma.turma_id"
			+ "	left join Professor professor on professor.id = avaliacao.professor_id"
			+ "	where turma.id = ?1"
			+ "	and avaliacao.anoletivo = ?2"
			+ "	and avaliacao.bimestre = ?3"
			+ "	and avaliacao.disciplina = ?4"
			+ " order by aluno.nomealuno", nativeQuery = true)
	public List<AlunoAvaliacao> getAlunoAvaliacaoNative(Long idTurma, Integer anoletivo, Integer bimestre, int disciplina);
	
	
	
	@Query(value = "select distinct (aluno) from AlunoAvaliacao alunoavaliacao"
			+ "	left join alunoavaliacao.avaliacao avaliacao"
			+ "	left join alunoavaliacao.aluno aluno"
			+ "	where alunoavaliacao.anoLetivo = ?1"
			+ "	and avaliacao.serie = ?2 "
			+ " and disciplina = 1"
			+"  and bimestre = 3")
	public List<Aluno> getAlunoAvaliacaoNative(Integer anoletivo, Serie serie);
	
	
	
	@Query(value = "select * from alunoavaliacao  av"
			+ "	left join  aluno aluno on av.aluno_id = aluno.id"
			+ "	left join avaliacao avaliacao on av.avaliacao_id = avaliacao.id"
			+ "	left join AlunoTurma alunoTurma on alunoTurma.aluno_id = av.aluno_id"
			+ "	left join Turma turma on turma.id = alunoTurma.turma_id"
			+ "	left join Professor professor on professor.id = avaliacao.professor_id"
			+ "	where turma.id = ?1"
			+ "	and avaliacao.anoletivo = ?2"
			+ "	and avaliacao.bimestre = ?3"
			+ "	and aluno.id = ?4"
			+ "	and avaliacao.disciplina = ?5"
			+ " order by aluno.nomealuno", nativeQuery = true)
	public List<AlunoAvaliacao> getAlunoAvaliacaoNative(Long idTurma, Integer anoletivo, Integer bimestre, Long idAluno, int disciplina);
	
	@Query(value = "select * from alunoavaliacao  av"
			+ "	left join  aluno aluno on av.aluno_id = aluno.id"
			+ "	left join avaliacao avaliacao on av.avaliacao_id = avaliacao.id"
			+ "	left join AlunoTurma alunoTurma on alunoTurma.aluno_id = av.aluno_id"
			+ "	left join Turma turma on turma.id = alunoTurma.turma_id"
			+ "	left join Professor professor on professor.id = avaliacao.professor_id"
			+ "	where turma.id = ?1"
			+ "	and avaliacao.anoletivo = ?2"
			+ "	and avaliacao.bimestre = ?3"
			+ "	and aluno.id = ?4"
			+ "	and professor.id = ?5"
			+ "	and avaliacao.disciplina = ?6"
			+ " order by aluno.nomealuno", nativeQuery = true)
	public List<AlunoAvaliacao> getAlunoAvaliacaoNative(Long idTurma, Integer anoletivo, Integer bimestre, Long idAluno, Long idProfessor, int disciplina);

	@Query(value = "select * from alunoavaliacao  av"
			+ "	left join  aluno aluno on av.aluno_id = aluno.id"
			+ "	left join avaliacao avaliacao on av.avaliacao_id = avaliacao.id"
			+ "	left join AlunoTurma alunoTurma on alunoTurma.aluno_id = av.aluno_id"
			+ "	left join Turma turma on turma.id = alunoTurma.turma_id"
			+ "	left join Professor professor on professor.id = avaliacao.professor_id"
			+ "	where turma.id = ?1"
			+ "	and avaliacao.anoletivo = ?2"
			+ "	and avaliacao.bimestre = ?3"
			+ "	and professor.id = ?4"
			+ "	and avaliacao.disciplina = ?5"
			+ " order by aluno.nomealuno", nativeQuery = true)
	public List<AlunoAvaliacao> getAlunoAvaliacaoProfessorNative(Long idTurma, Integer anoletivo, Integer bimestre,	Long idProfessor, int disciplina);
	
	
	@Query(value = "select * from alunoavaliacao  av"
			+ "	left join  aluno aluno on av.aluno_id = aluno.id"
			+ "	left join avaliacao avaliacao on av.avaliacao_id = avaliacao.id"
			+ "	where  aluno.id = ?1"
			+ "	and avaliacao.disciplina = ?2"
			+ "	and avaliacao.bimestre = ?3"
			+ "	and avaliacao.recuperacao = ?4"
			+ " and avaliacao.anoletivo = ?5"
			+ " order by aluno.nomealuno", nativeQuery = true)
	public List<AlunoAvaliacao> getAlunoAvaliacaoProfessorNative(Long idAluno,int disciplina, int bimestre, boolean recuperacao,int anletivo);
}
