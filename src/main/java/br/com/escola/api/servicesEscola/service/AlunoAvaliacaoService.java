package br.com.escola.api.servicesEscola.service;

import java.util.List;

import org.escola.enums.BimestreEnum;
import org.escola.enums.DisciplinaEnum;
import org.escola.enums.Serie;
import org.escola.model.Aluno;
import org.escola.model.AlunoAvaliacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.escola.api.servicesEscola.repository.AlunoAvaliacaoRepository;

@Service
public class AlunoAvaliacaoService {

	@Autowired
	private AlunoAvaliacaoRepository alunoAvaliacaoRepository;
	
	
	public List<AlunoAvaliacao> getAlunoAvaliacaoNative(Long idTurma, Integer anoletivo, Integer bimestre, int disciplina){
		return alunoAvaliacaoRepository.getAlunoAvaliacaoNative(idTurma, anoletivo, bimestre, disciplina);
	}
	
	public List<AlunoAvaliacao> getAlunoAvaliacaoNative(Long idTurma, Integer anoletivo, Integer bimestre, Long idAluno, int disciplina){
		return alunoAvaliacaoRepository.getAlunoAvaliacaoNative(idTurma, anoletivo, bimestre, idAluno, disciplina);
	}
	
	public List<AlunoAvaliacao> getAlunoAvaliacaoNative(Long idTurma, Integer anoletivo, Integer bimestre, Long idAluno, Long idProfessor, int disciplina){
		return alunoAvaliacaoRepository.getAlunoAvaliacaoNative(idTurma, anoletivo, bimestre, idAluno, idProfessor, disciplina);
	}

	public List<AlunoAvaliacao> getAlunoAvaliacaoProfessorNative(Long idTurma, Integer anoletivo, Integer bimestre,	Long idProfessor, int disciplina) {
		return alunoAvaliacaoRepository.getAlunoAvaliacaoProfessorNative(idTurma, anoletivo, bimestre, idProfessor, disciplina);
	}
	
	
	public List<Aluno> getAlunoAvaliacaoNative(Integer anoletivo,Serie serie){
		return alunoAvaliacaoRepository.getAlunoAvaliacaoNative(anoletivo, serie);
	}
	
	
	public float getNota(Long idAluno, DisciplinaEnum disciplina, BimestreEnum bimestre, boolean recupecacao, int ano) {
		try {
	
			List<AlunoAvaliacao> notas = alunoAvaliacaoRepository.getAlunoAvaliacaoProfessorNative(idAluno, disciplina.ordinal(), bimestre.ordinal(), recupecacao,ano);
			Float soma = 0F;
			Float pesos = 0F;
			if (notas != null && !notas.isEmpty()) {
				for (AlunoAvaliacao avas : notas) {
					soma += avas.getNota() * avas.getAvaliacao().getPeso();
					pesos += avas.getAvaliacao().getPeso();
				}
			}

			return soma / pesos;

		} catch (Exception e) {
			return 0f;
		}
	}
	
	
	public AlunoAvaliacao salvar(AlunoAvaliacao av) {
		return alunoAvaliacaoRepository.save(av);
	}
}
