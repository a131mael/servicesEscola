package br.com.escola.api.servicesEscola.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.escola.api.servicesEscola.service.HistoricoAlunoService;


@RestController
@RequestMapping("/api")
public class AlunoController {

	@Autowired
	HistoricoAlunoService historicoAlunoService;

	@GetMapping("/anoLetivoFinalizado")
	public ResponseEntity<String> finalizarAnoLetivo() {
		
		try {

			historicoAlunoService.finalizarAnoLetivoAnterior();
			
			return new ResponseEntity<>("ok", HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
