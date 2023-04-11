package br.com.escola.api.servicesEscola.repository;

import java.util.List;

import org.escola.model.AlunoAvaliacao;
import org.springframework.stereotype.Repository;

@Repository
public class AlunoAvaliacaoRepositoryImpl implements AlunoAvaliacaoRepositoryCustom {

	@Override
	public List<AlunoAvaliacao> getAlunoAvaliacao(Long idTurma, int bimestre) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AlunoAvaliacao> getAlunoAvaliacao(Long idTurma, int bimestre, Long idAluno) {
		// TODO Auto-generated method stub
		return null;
	}

}
