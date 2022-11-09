package com.texoit.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.texoit.model.DadosResult;
import com.texoit.model.Movie;
import com.texoit.model.Result;
import com.texoit.repository.MovieRepository;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

	@Autowired
	private MovieRepository movieRepository;

	@GetMapping
	public List<Movie> listar() {
		return movieRepository.findAll();
	}

	@GetMapping("/pesquisar")
	public Result listarPesquisa() {

		Integer[] intervalos = {-1,-1};

		Map<String, List<Integer>> produtores = carregaDados(movieRepository.findAll());
		intervalos = buscaIntervalos(intervalos, produtores);
		
		Integer minGeral = intervalos[0];
		Integer maxGeral = intervalos[1];

		Result retorno = new Result();

		if (!produtores.isEmpty()) {
			
			for (String key : produtores.keySet()) {

				List<Integer> listaAnos = produtores.get(key);

				if (listaAnos.size() > 1) {

					Collections.sort(listaAnos);

					for (int x = 0; x < (listaAnos.size() - 1); x++) {

						Integer diferenca = (listaAnos.get(x + 1) - listaAnos.get(x));

						if (diferenca == minGeral) {
							DadosResult dadosMin = new DadosResult(key, diferenca, listaAnos.get(x), listaAnos.get(x + 1));
							retorno.addMin(dadosMin);
						}
						if (diferenca == maxGeral) {
							DadosResult dadosMax = new DadosResult(key, diferenca, listaAnos.get(x), listaAnos.get(x + 1));
							retorno.addMax(dadosMax);
						}
					}
				}
			}

		}
		return retorno;
	}

	private Integer[] buscaIntervalos(Integer[] intervalos, Map<String, List<Integer>> produtores) {

		Integer minGeral = intervalos[0]; 
		Integer maxGeral = intervalos[1];
		
		for (String produtor : produtores.keySet()) {

			List<Integer> listaAnos = produtores.get(produtor);

			if (listaAnos.size() > 1) {

				Collections.sort(listaAnos);

				for (int x = 0; x < (listaAnos.size() - 1); x++) {

					Integer diferenca = (listaAnos.get(x + 1) - listaAnos.get(x));

					if (minGeral == -1 && maxGeral == -1) {
						minGeral = diferenca;
						maxGeral = diferenca;
					} else {
						if (minGeral > diferenca) {
							minGeral = diferenca;
						}
						if (maxGeral < diferenca) {
							maxGeral = diferenca;
						}
					}
				}
			}
		}
		intervalos[0] = minGeral;
		intervalos[1] = maxGeral;
				
		return intervalos;
	}

	private Map<String, List<Integer>> carregaDados(List<Movie> listaFilmes) {

		Map<String, List<Integer>> dataSet = new HashMap<String, List<Integer>>();
		
		for (Movie filme : listaFilmes) {
			
			if(filme.isVencedor()) {

				String produtores[] = filme.getProdutor().split(",");
				
				for (String key : produtores) {
					
					if(!key.equals("") && !key.isEmpty()) {
						if (dataSet.containsKey(key)) {
							dataSet.get(key).add(filme.getAno());
						} else {
							List<Integer> anosPremiados = new ArrayList<>();
							anosPremiados.add(filme.getAno());
							dataSet.put(key, anosPremiados);
						}
					}
				}
			}
		}
		return dataSet;
	}

	@GetMapping("{id}")
	public Optional<Movie> consultarPorId(@PathVariable Long id) {
		return movieRepository.findById(id);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Movie adicionar(@RequestBody Movie movie) {
		return movieRepository.save(movie);
	}

	@DeleteMapping("{id}")
	public void deletar(@PathVariable Long id) {
		movieRepository.deleteById(id);
	}
}
