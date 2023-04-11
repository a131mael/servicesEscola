package br.com.escola.api.servicesEscola.service;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.escola.enums.BimestreEnum;
import org.escola.enums.DisciplinaEnum;
import org.escola.enums.Serie;
import org.escola.model.Aluno;
import org.escola.model.Configuracao;
import org.escola.model.HistoricoAluno;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.escola.api.servicesEscola.repository.HistoricoAlunoRepository;

@Service
public class HistoricoAlunoService {

	@Autowired
	private HistoricoAlunoRepository historicoAlunoRepository;
	
	public HistoricoAluno salvar(HistoricoAluno historicoAluno) {
		return historicoAlunoRepository.save(historicoAluno);
	}

	
	@Autowired
	private AlunoAvaliacaoService alunoAvaliacaoService;
	
	@Autowired
	private AlunoService alunoService;
	
	@Autowired
	private br.com.escola.api.servicesEscola.repository.ConfiguracaoRepository configuracaoRepository;

	public void finalizarAnoLetivoAnterior() {
		int anoLetivoAtual = getConfiguracao().getAnoLetivo() - 1;

		Set<Serie> series = Arrays.stream(Serie.values()).filter(Serie::isGeraHistorico).collect(Collectors.toSet());

		for (Serie serie : series) {
			editarrAlunos(anoLetivoAtual, serie);
		}

	}


	public void editarrAlunos(int anoLetivoAtual, Serie serie) {
		System.out.println("Iniciando a serie : " + serie.getName());

		List<Aluno> alunos = alunoAvaliacaoService.getAlunoAvaliacaoNative(anoLetivoAtual, serie);

		System.out.println("Total de alunos na serie : " + alunos.size());
		
		for (int i = 0; i < alunos.size(); i++) {
			try {
				System.out.println("Nome : " + alunos.get(i).getNomeAluno() + "    -   " + i);
				try {
					gerarHistorico2(alunos.get(i), anoLetivoAtual, serie);

				} catch (Exception e) {

				}

				try {
					alunoService.salvarSerie(alunos.get(i), Serie.values()[alunos.get(i).getSerie().ordinal() + 1],anoLetivoAtual+1);
				} catch (Exception e) {
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void gerarHistorico2(Aluno aluno, int anoletivo, Serie serie) {
		gerarHistorico(aluno, anoletivo, false, serie);
	}

	public void gerarHistorico(Aluno aluno, int ano, boolean mesmoano, Serie serie) {
		if (aluno != null && aluno.getNomeAluno() != null) {
			HistoricoAluno historico = new HistoricoAluno();
			historico.setNomeAluno(aluno.getNomeAluno());
			historico.setAluno(aluno);
			historico.setAno(ano);
			historico.setEscola("Centro Educacional ADONAI");
			historico.setEstado("Santa Catarina");

			double frequencia = ((200 - faltas(aluno)) / 200) * 100;
			if (frequencia == 0) {
				frequencia = 100;
			}
			historico.setFrequencia(frequencia + "%");

			historico.setMunicipio("Palhoça");
			historico.setPeriodo(aluno.getPeriodo());

			if (serie != null) {
				historico.setSerie(serie);
			} else {
				if (mesmoano) {
					historico.setSerie(aluno.getSerie());
				} else {
					if (aluno.getRematricular() != null && aluno.getRematricular()) {
						historico.setSerie(Serie.values()[aluno.getSerie().ordinal()]);
					} else {
						historico.setSerie(Serie.values()[aluno.getSerie().ordinal() - 1]);
					}
				}
			}

			// Artes
			float notaArtes1 = alunoService.getNota(aluno.getId(), DisciplinaEnum.ARTES, BimestreEnum.PRIMEIRO_BIMESTRE,
					false, ano);
			float notaArtes1Rec = alunoService.getNota(aluno.getId(), DisciplinaEnum.ARTES,
					BimestreEnum.PRIMEIRO_BIMESTRE, true, ano);
			float notaArtes2 = alunoService.getNota(aluno.getId(), DisciplinaEnum.ARTES, BimestreEnum.SEGUNDO_BIMESTRE,
					false, ano);
			float notaArtes2Rec = alunoService.getNota(aluno.getId(), DisciplinaEnum.ARTES,
					BimestreEnum.SEGUNDO_BIMESTRE, true, ano);
			float notaArtes3 = alunoService.getNota(aluno.getId(), DisciplinaEnum.ARTES, BimestreEnum.TERCEIRO_BIMESTRE,
					false, ano);
			float notaArtes3Rec = alunoService.getNota(aluno.getId(), DisciplinaEnum.ARTES,
					BimestreEnum.TERCEIRO_BIMESTRE, true, ano);
			float notaArtes4 = alunoService.getNota(aluno.getId(), DisciplinaEnum.ARTES, BimestreEnum.QUARTO_BIMESTRE,
					false, ano);
			float notaArtes4Rec = alunoService.getNota(aluno.getId(), DisciplinaEnum.ARTES,
					BimestreEnum.QUARTO_BIMESTRE, true, ano);
			Float notaArtes = media(maior(notaArtes1, notaArtes1Rec), maior(notaArtes2, notaArtes2Rec),
					maior(notaArtes3, notaArtes3Rec), maior(notaArtes4, notaArtes4Rec));
			historico.setNotaArtes(mostraNotas(notaArtes));

			// Ciencias
			float notaCiencias1 = alunoService.getNota(aluno.getId(), DisciplinaEnum.CIENCIAS,
					BimestreEnum.PRIMEIRO_BIMESTRE, false, ano);
			float notaCiencias1Rec = alunoService.getNota(aluno.getId(), DisciplinaEnum.CIENCIAS,
					BimestreEnum.PRIMEIRO_BIMESTRE, true, ano);
			float notaCiencias2 = alunoService.getNota(aluno.getId(), DisciplinaEnum.CIENCIAS,
					BimestreEnum.SEGUNDO_BIMESTRE, false, ano);
			float notaCiencias2Rec = alunoService.getNota(aluno.getId(), DisciplinaEnum.CIENCIAS,
					BimestreEnum.SEGUNDO_BIMESTRE, true, ano);
			float notaCiencias3 = alunoService.getNota(aluno.getId(), DisciplinaEnum.CIENCIAS,
					BimestreEnum.TERCEIRO_BIMESTRE, false, ano);
			float notaCiencias3Rec = alunoService.getNota(aluno.getId(), DisciplinaEnum.CIENCIAS,
					BimestreEnum.TERCEIRO_BIMESTRE, true, ano);
			float notaCiencias4 = alunoService.getNota(aluno.getId(), DisciplinaEnum.CIENCIAS,
					BimestreEnum.QUARTO_BIMESTRE, false, ano);
			float notaCiencias4Rec = alunoService.getNota(aluno.getId(), DisciplinaEnum.CIENCIAS,
					BimestreEnum.QUARTO_BIMESTRE, true, ano);
			Float notaCiencias = media(maior(notaCiencias1, notaCiencias1Rec), maior(notaCiencias2, notaCiencias2Rec),
					maior(notaCiencias3, notaCiencias3Rec), maior(notaCiencias4, notaCiencias4Rec));
			historico.setNotaCiencias(notaCiencias);

			// EdFisica
			float notaEdFisica1 = alunoService.getNota(aluno.getId(), DisciplinaEnum.EDUCACAO_FISICA,
					BimestreEnum.PRIMEIRO_BIMESTRE, false, ano);
			float notaEdFisica1Rec = alunoService.getNota(aluno.getId(), DisciplinaEnum.EDUCACAO_FISICA,
					BimestreEnum.PRIMEIRO_BIMESTRE, true, ano);
			float notaEdFisica2 = alunoService.getNota(aluno.getId(), DisciplinaEnum.EDUCACAO_FISICA,
					BimestreEnum.SEGUNDO_BIMESTRE, false, ano);
			float notaEdFisica2Rec = alunoService.getNota(aluno.getId(), DisciplinaEnum.EDUCACAO_FISICA,
					BimestreEnum.SEGUNDO_BIMESTRE, true, ano);
			float notaEdFisica3 = alunoService.getNota(aluno.getId(), DisciplinaEnum.EDUCACAO_FISICA,
					BimestreEnum.TERCEIRO_BIMESTRE, false, ano);
			float notaEdFisica3Rec = alunoService.getNota(aluno.getId(), DisciplinaEnum.EDUCACAO_FISICA,
					BimestreEnum.TERCEIRO_BIMESTRE, true, ano);
			float notaEdFisica4 = alunoService.getNota(aluno.getId(), DisciplinaEnum.EDUCACAO_FISICA,
					BimestreEnum.QUARTO_BIMESTRE, false, ano);
			float notaEdFisica4Rec = alunoService.getNota(aluno.getId(), DisciplinaEnum.EDUCACAO_FISICA,
					BimestreEnum.QUARTO_BIMESTRE, true, ano);
			Float notaEdFisica = media(maior(notaEdFisica1, notaEdFisica1Rec), maior(notaEdFisica2, notaEdFisica2Rec),
					maior(notaEdFisica3, notaEdFisica3Rec), maior(notaEdFisica4, notaEdFisica4Rec));
			historico.setNotaEdFisica(notaEdFisica);

			// FORM CRISTA
			float notaFORMACAO_CRISTA1 = alunoService.getNota(aluno.getId(), DisciplinaEnum.FORMACAO_CRISTA,
					BimestreEnum.PRIMEIRO_BIMESTRE, false, ano);
			float notaFORMACAO_CRISTA1Rec = alunoService.getNota(aluno.getId(), DisciplinaEnum.FORMACAO_CRISTA,
					BimestreEnum.PRIMEIRO_BIMESTRE, true, ano);
			float notaFORMACAO_CRISTA2 = alunoService.getNota(aluno.getId(), DisciplinaEnum.FORMACAO_CRISTA,
					BimestreEnum.SEGUNDO_BIMESTRE, false, ano);
			float notaFORMACAO_CRISTA2Rec = alunoService.getNota(aluno.getId(), DisciplinaEnum.FORMACAO_CRISTA,
					BimestreEnum.SEGUNDO_BIMESTRE, true, ano);
			float notaFORMACAO_CRISTA3 = alunoService.getNota(aluno.getId(), DisciplinaEnum.FORMACAO_CRISTA,
					BimestreEnum.TERCEIRO_BIMESTRE, false, ano);
			float notaFORMACAO_CRISTA3Rec = alunoService.getNota(aluno.getId(), DisciplinaEnum.FORMACAO_CRISTA,
					BimestreEnum.TERCEIRO_BIMESTRE, true, ano);
			float notaFORMACAO_CRISTA4 = alunoService.getNota(aluno.getId(), DisciplinaEnum.FORMACAO_CRISTA,
					BimestreEnum.QUARTO_BIMESTRE, false, ano);
			float notaFORMACAO_CRISTA4Rec = alunoService.getNota(aluno.getId(), DisciplinaEnum.FORMACAO_CRISTA,
					BimestreEnum.QUARTO_BIMESTRE, true, ano);
			Float notaFORMACAO_CRISTA = media(maior(notaFORMACAO_CRISTA1, notaFORMACAO_CRISTA1Rec),
					maior(notaFORMACAO_CRISTA2, notaFORMACAO_CRISTA2Rec),
					maior(notaFORMACAO_CRISTA3, notaFORMACAO_CRISTA3Rec),
					maior(notaFORMACAO_CRISTA4, notaFORMACAO_CRISTA4Rec));
			historico.setNotaformacaoCrista(notaFORMACAO_CRISTA);

			// GEOGRAFIA
			float notaGEOGRAFIA1 = alunoService.getNota(aluno.getId(), DisciplinaEnum.GEOGRAFIA,
					BimestreEnum.PRIMEIRO_BIMESTRE, false, ano);
			float notaGEOGRAFIA1Rec = alunoService.getNota(aluno.getId(), DisciplinaEnum.GEOGRAFIA,
					BimestreEnum.PRIMEIRO_BIMESTRE, true, ano);
			float notaGEOGRAFIA2 = alunoService.getNota(aluno.getId(), DisciplinaEnum.GEOGRAFIA,
					BimestreEnum.SEGUNDO_BIMESTRE, false, ano);
			float notaGEOGRAFIA2Rec = alunoService.getNota(aluno.getId(), DisciplinaEnum.GEOGRAFIA,
					BimestreEnum.SEGUNDO_BIMESTRE, true, ano);
			float notaGEOGRAFIA3 = alunoService.getNota(aluno.getId(), DisciplinaEnum.GEOGRAFIA,
					BimestreEnum.TERCEIRO_BIMESTRE, false, ano);
			float notaGEOGRAFIA3Rec = alunoService.getNota(aluno.getId(), DisciplinaEnum.GEOGRAFIA,
					BimestreEnum.TERCEIRO_BIMESTRE, true, ano);
			float notaGEOGRAFIA4 = alunoService.getNota(aluno.getId(), DisciplinaEnum.GEOGRAFIA,
					BimestreEnum.QUARTO_BIMESTRE, false, ano);
			float notaGEOGRAFIA4Rec = alunoService.getNota(aluno.getId(), DisciplinaEnum.GEOGRAFIA,
					BimestreEnum.QUARTO_BIMESTRE, true, ano);
			Float notaGEOGRAFIA = media(maior(notaGEOGRAFIA1, notaGEOGRAFIA1Rec),
					maior(notaGEOGRAFIA2, notaGEOGRAFIA2Rec), maior(notaGEOGRAFIA3, notaGEOGRAFIA3Rec),
					maior(notaGEOGRAFIA4, notaGEOGRAFIA4Rec));
			historico.setNotaGeografia(notaGEOGRAFIA);

			// HITORIA
			float notaHISTORIA1 = alunoService.getNota(aluno.getId(), DisciplinaEnum.HISTORIA,
					BimestreEnum.PRIMEIRO_BIMESTRE, false, ano);
			float notaHISTORIA1Rec = alunoService.getNota(aluno.getId(), DisciplinaEnum.HISTORIA,
					BimestreEnum.PRIMEIRO_BIMESTRE, true, ano);
			float notaHISTORIA2 = alunoService.getNota(aluno.getId(), DisciplinaEnum.HISTORIA,
					BimestreEnum.SEGUNDO_BIMESTRE, false, ano);
			float notaHISTORIA2Rec = alunoService.getNota(aluno.getId(), DisciplinaEnum.HISTORIA,
					BimestreEnum.SEGUNDO_BIMESTRE, true, ano);
			float notaHISTORIA3 = alunoService.getNota(aluno.getId(), DisciplinaEnum.HISTORIA,
					BimestreEnum.TERCEIRO_BIMESTRE, false, ano);
			float notaHISTORIA3Rec = alunoService.getNota(aluno.getId(), DisciplinaEnum.HISTORIA,
					BimestreEnum.TERCEIRO_BIMESTRE, true, ano);
			float notaHISTORIA4 = alunoService.getNota(aluno.getId(), DisciplinaEnum.HISTORIA,
					BimestreEnum.QUARTO_BIMESTRE, false, ano);
			float notaHISTORIA4Rec = alunoService.getNota(aluno.getId(), DisciplinaEnum.HISTORIA,
					BimestreEnum.QUARTO_BIMESTRE, true, ano);
			Float notaHISTORIA = media(maior(notaHISTORIA1, notaHISTORIA1Rec), maior(notaHISTORIA2, notaHISTORIA2Rec),
					maior(notaHISTORIA3, notaHISTORIA3Rec), maior(notaHISTORIA4, notaHISTORIA4Rec));
			historico.setNotaHistoria(notaHISTORIA);

			// Ingles
			float notaINGLES1 = alunoService.getNota(aluno.getId(), DisciplinaEnum.INGLES,
					BimestreEnum.PRIMEIRO_BIMESTRE, false, ano);
			float notaINGLES1Rec = alunoService.getNota(aluno.getId(), DisciplinaEnum.INGLES,
					BimestreEnum.PRIMEIRO_BIMESTRE, true, ano);
			float notaINGLES2 = alunoService.getNota(aluno.getId(), DisciplinaEnum.INGLES,
					BimestreEnum.SEGUNDO_BIMESTRE, false, ano);
			float notaINGLES2Rec = alunoService.getNota(aluno.getId(), DisciplinaEnum.INGLES,
					BimestreEnum.SEGUNDO_BIMESTRE, true, ano);
			float notaINGLES3 = alunoService.getNota(aluno.getId(), DisciplinaEnum.INGLES,
					BimestreEnum.TERCEIRO_BIMESTRE, false, ano);
			float notaINGLES3Rec = alunoService.getNota(aluno.getId(), DisciplinaEnum.INGLES,
					BimestreEnum.TERCEIRO_BIMESTRE, true, ano);
			float notaINGLES4 = alunoService.getNota(aluno.getId(), DisciplinaEnum.INGLES, BimestreEnum.QUARTO_BIMESTRE,
					false, ano);
			float notaINGLES4Rec = alunoService.getNota(aluno.getId(), DisciplinaEnum.INGLES,
					BimestreEnum.QUARTO_BIMESTRE, true, ano);
			Float notaINGLES = media(maior(notaINGLES1, notaINGLES1Rec), maior(notaINGLES2, notaINGLES2Rec),
					maior(notaINGLES3, notaINGLES3Rec), maior(notaINGLES4, notaINGLES4Rec));
			historico.setNotaIngles(notaINGLES);

			// Espanhol
			float notaEspanhols1 = alunoService.getNota(aluno.getId(), DisciplinaEnum.ESPANHOL,
					BimestreEnum.PRIMEIRO_BIMESTRE, false, ano);
			float notaEspanhols1Rec = alunoService.getNota(aluno.getId(), DisciplinaEnum.ESPANHOL,
					BimestreEnum.PRIMEIRO_BIMESTRE, true, ano);
			float notaEspanhols2 = alunoService.getNota(aluno.getId(), DisciplinaEnum.ESPANHOL,
					BimestreEnum.SEGUNDO_BIMESTRE, false, ano);
			float notaEspanhols2Rec = alunoService.getNota(aluno.getId(), DisciplinaEnum.ESPANHOL,
					BimestreEnum.SEGUNDO_BIMESTRE, true, ano);
			float notaEspanhols3 = alunoService.getNota(aluno.getId(), DisciplinaEnum.ESPANHOL,
					BimestreEnum.TERCEIRO_BIMESTRE, false, ano);
			float notaEspanhols3Rec = alunoService.getNota(aluno.getId(), DisciplinaEnum.ESPANHOL,
					BimestreEnum.TERCEIRO_BIMESTRE, true, ano);
			float notaEspanhols4 = alunoService.getNota(aluno.getId(), DisciplinaEnum.ESPANHOL,
					BimestreEnum.QUARTO_BIMESTRE, false, ano);
			float notaEspanhols4Rec = alunoService.getNota(aluno.getId(), DisciplinaEnum.ESPANHOL,
					BimestreEnum.QUARTO_BIMESTRE, true, ano);
			Float notaEspanhols = media(maior(notaEspanhols1, notaEspanhols1Rec),
					maior(notaEspanhols2, notaEspanhols2Rec), maior(notaEspanhols3, notaEspanhols3Rec),
					maior(notaEspanhols4, notaEspanhols4Rec));
			historico.setNotaEspanhol(notaEspanhols);

			// HITORIA
			float notaMATEMATICA1 = alunoService.getNota(aluno.getId(), DisciplinaEnum.MATEMATICA,
					BimestreEnum.PRIMEIRO_BIMESTRE, false, ano);
			float notaMATEMATICA1Rec = alunoService.getNota(aluno.getId(), DisciplinaEnum.MATEMATICA,
					BimestreEnum.PRIMEIRO_BIMESTRE, true, ano);
			float notaMATEMATICA2 = alunoService.getNota(aluno.getId(), DisciplinaEnum.MATEMATICA,
					BimestreEnum.SEGUNDO_BIMESTRE, false, ano);
			float notaMATEMATICA2Rec = alunoService.getNota(aluno.getId(), DisciplinaEnum.MATEMATICA,
					BimestreEnum.SEGUNDO_BIMESTRE, true, ano);
			float notaMATEMATICA3 = alunoService.getNota(aluno.getId(), DisciplinaEnum.MATEMATICA,
					BimestreEnum.TERCEIRO_BIMESTRE, false, ano);
			float notaMATEMATICA3Rec = alunoService.getNota(aluno.getId(), DisciplinaEnum.MATEMATICA,
					BimestreEnum.TERCEIRO_BIMESTRE, true, ano);
			float notaMATEMATICA4 = alunoService.getNota(aluno.getId(), DisciplinaEnum.MATEMATICA,
					BimestreEnum.QUARTO_BIMESTRE, false, ano);
			float notaMATEMATICA4Rec = alunoService.getNota(aluno.getId(), DisciplinaEnum.MATEMATICA,
					BimestreEnum.QUARTO_BIMESTRE, true, ano);
			Float notaMATEMATICA = media(maior(notaMATEMATICA1, notaMATEMATICA1Rec),
					maior(notaMATEMATICA2, notaMATEMATICA2Rec), maior(notaMATEMATICA3, notaMATEMATICA3Rec),
					maior(notaMATEMATICA4, notaMATEMATICA4Rec));
			historico.setNotaMatematica(notaMATEMATICA);

			// HITORIA
			float notaPORTUGUES1 = alunoService.getNota(aluno.getId(), DisciplinaEnum.PORTUGUES,
					BimestreEnum.PRIMEIRO_BIMESTRE, false, ano);
			float notaPORTUGUES1Rec = alunoService.getNota(aluno.getId(), DisciplinaEnum.PORTUGUES,
					BimestreEnum.PRIMEIRO_BIMESTRE, true, ano);
			float notaPORTUGUES2 = alunoService.getNota(aluno.getId(), DisciplinaEnum.PORTUGUES,
					BimestreEnum.SEGUNDO_BIMESTRE, false, ano);
			float notaPORTUGUES2Rec = alunoService.getNota(aluno.getId(), DisciplinaEnum.PORTUGUES,
					BimestreEnum.SEGUNDO_BIMESTRE, true, ano);
			float notaPORTUGUES3 = alunoService.getNota(aluno.getId(), DisciplinaEnum.PORTUGUES,
					BimestreEnum.TERCEIRO_BIMESTRE, false, ano);
			float notaPORTUGUES3Rec = alunoService.getNota(aluno.getId(), DisciplinaEnum.PORTUGUES,
					BimestreEnum.TERCEIRO_BIMESTRE, true, ano);
			float notaPORTUGUES4 = alunoService.getNota(aluno.getId(), DisciplinaEnum.PORTUGUES,
					BimestreEnum.QUARTO_BIMESTRE, false, ano);
			float notaPORTUGUES4Rec = alunoService.getNota(aluno.getId(), DisciplinaEnum.PORTUGUES,
					BimestreEnum.QUARTO_BIMESTRE, true, ano);
			Float notaPORTUGUES = media(maior(notaPORTUGUES1, notaPORTUGUES1Rec),
					maior(notaPORTUGUES2, notaPORTUGUES2Rec), maior(notaPORTUGUES3, notaPORTUGUES3Rec),
					maior(notaPORTUGUES4, notaPORTUGUES4Rec));
			historico.setNotaPortugues(notaPORTUGUES);

			this.salvar(historico);

		}
	}

	private int faltas(Aluno aluno) {
		int faltas = 0;
		if (aluno.getFaltas1Bimestre() != null) {
			faltas += aluno.getFaltas1Bimestre();
		}
		if (aluno.getFaltas2Bimestre() != null) {
			faltas += aluno.getFaltas1Bimestre();
			;
		}
		if (aluno.getFaltas3Bimestre() != null) {
			faltas += aluno.getFaltas3Bimestre();
			;
		}
		if (aluno.getFaltas4Bimestre() != null) {
			faltas += aluno.getFaltas4Bimestre();
		}

		return faltas;
	}
	
	private Float mostraNotas(Float nota) {
		if (nota == null || nota == 0 || Float.isNaN(nota)) {
			return 0F;
		} else {
			DecimalFormat df = new DecimalFormat("0.##");
			String dx = df.format(nota);

			dx = dx.replace(",", ".");

			return (float) (Math.round(Float.parseFloat(dx) / 0.5) * 0.5D);
		}
	}
	
	private Float media(Float... notas) {
		int qtade = 0;
		float sum = 0;
		for (Float f : notas) {
			sum += f;
			qtade++;
		}
		return qtade > 0 ? sum / qtade : 0;
	}

	private Float maior(Float float1, Float float2) {
		if (Float.isNaN(float1)) {
			return float2;
		}
		if (Float.isNaN(float2)) {
			return float1;
		}

		return float1 > float2 ? float1 : float2;
	}
	
	public Configuracao getConfiguracao() {
		return configuracaoRepository.findAll().get(0);
	}
	
	
	
	
}
