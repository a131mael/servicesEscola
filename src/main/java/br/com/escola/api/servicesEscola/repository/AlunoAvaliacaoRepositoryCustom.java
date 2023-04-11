package br.com.escola.api.servicesEscola.repository;

import java.util.List;

import org.escola.model.AlunoAvaliacao;

public interface AlunoAvaliacaoRepositoryCustom {

	public List<AlunoAvaliacao> getAlunoAvaliacao(Long idTurma, int bimestre);
	
	public List<AlunoAvaliacao> getAlunoAvaliacao(Long idTurma, int bimestre, Long idAluno);

	
}