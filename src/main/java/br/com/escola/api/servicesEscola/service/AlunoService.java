package br.com.escola.api.servicesEscola.service;

import java.util.ArrayList;
import java.util.List;

import org.escola.enums.BimestreEnum;
import org.escola.enums.DisciplinaEnum;
import org.escola.enums.Serie;
import org.escola.model.Aluno;
import org.escola.model.AlunoTurma;
import org.escola.model.Avaliacao;
import org.escola.model.Configuracao;
import org.escola.model.ProfessorTurma;
import org.escola.model.Turma;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.escola.api.servicesEscola.repository.AlunoRepository;
import br.com.escola.api.servicesEscola.repository.AvaliacaoRepository;
import br.com.escola.api.servicesEscola.repository.ConfiguracaoRepository;
import br.com.escola.api.servicesEscola.repository.ProfessorRepository;
import br.com.escola.api.servicesEscola.repository.TurmaRepository;

@Service
public class AlunoService {

	@Autowired
	private AlunoRepository alunoRepository;
	
	@Autowired
	private TurmaRepository turmaRepository;
	
	@Autowired
	private ConfiguracaoRepository configuracaoRepository;
	
	@Autowired
	private AvaliacaoRepository avaliacaoRepository;
	
	@Autowired
	private ProfessorRepository professorRepository;
	
	@Autowired
	private AlunoAvaliacaoService alunoAvaliacaoService;
	
	
	public List<Turma> getTurmas(){
		return turmaRepository.findAll();
	}

	public Configuracao getConfiguracao(){
		return configuracaoRepository.findAll().get(0);
	}
	
	public List<Avaliacao> getAvaliacoes(Long idProfessor,int anoletivo,int bimestre,int disciplina, Serie serie){
		return avaliacaoRepository.getAvaliacaoNative(idProfessor, anoletivo, bimestre, disciplina,serie.ordinal());
	}
	
	public List<ProfessorTurma> getProfessoresTurma(Long idTurma){
		return professorRepository.getProfessorTurmaNative(idTurma);
	}
	
	public Aluno salvarSerie(Aluno aluno,Serie serie,int anoLetivo) {
		Aluno al = alunoRepository.findById(aluno.getId()).get();
		al.setSerie(serie);
		al.setAnoLetivo(anoLetivo);
		return alunoRepository.save(al);
	}

	public float getNota(Long idAluno, DisciplinaEnum disciplina, BimestreEnum bimestre, boolean recupecacao, int ano) {
		return alunoAvaliacaoService.getNota(idAluno, disciplina, bimestre, recupecacao, ano);
	}
	
}
