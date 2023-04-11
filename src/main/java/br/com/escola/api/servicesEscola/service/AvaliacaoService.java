package br.com.escola.api.servicesEscola.service;

import org.escola.model.Avaliacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.escola.api.servicesEscola.repository.AvaliacaoRepository;

@Service
public class AvaliacaoService {

	@Autowired
	private AvaliacaoRepository avaliacaoRepository;
	
	public Avaliacao salvar(Avaliacao avaliacao) {
		return avaliacaoRepository.save(avaliacao);
	}

}
