package br.com.renanribeiro.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.PostConstruct;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import br.com.renanribeiro.controller.ProjetoController;
import br.com.renanribeiro.model.Projeto;
import br.com.renanribeiro.util.StatusEnum;

@Service
public class StatusService {
//TODO: Em análise, análise realizada, análise aprovada, iniciado, planejado, em andamento, encerrado e cancelado

	public List<StatusEnum> statuses = new ArrayList<StatusEnum>();
	
	@PostConstruct
	private void init() {
		statuses.add(StatusEnum.EmAnalise);
		statuses.add(StatusEnum.AnaliseRealizada);
		statuses.add(StatusEnum.AnaliseAprovada);
		statuses.add(StatusEnum.Iniciado);
		statuses.add(StatusEnum.Planejado);
		statuses.add(StatusEnum.EmAndamento);
		statuses.add(StatusEnum.Encerrado);
		statuses.add(StatusEnum.Cancelado);		
	}
	
	@Scheduled(fixedRate = 30 * 1000) // 30s * 1000ms
	public void changeProjectsStatus() {
		Random random = new Random();
		for (Projeto projeto : ProjetoController._projetos) {
			StatusEnum selected = statuses.get(random.nextInt(statuses.size()));
			projeto.setStatus(selected);
		}
	}
}
