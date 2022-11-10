package com.texoit;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

import com.texoit.model.Movie;
import com.texoit.repository.MovieRepository;

@Service
public class MovieService implements ApplicationRunner{

	private final MovieRepository repository;
	
	@Value("${api.file.path}")
	private String caminho;
	
	public MovieService(MovieRepository repository) {
		this.repository = repository;
	}
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		
		Path path = Paths.get(caminho);
		
		try (Stream<String> linhas = Files.lines(path, StandardCharsets.UTF_8)) {
	
			Iterator<String> iterator = linhas.iterator();
			iterator.next();
	
			while (iterator.hasNext()) {
	
				String linha = iterator.next();
				String[] colunas = linha.split(";");
				validaColunas(colunas);
	
				Integer nrAno = Integer.parseInt(colunas[0]);
				String titulo = colunas[1];
				String estudios = colunas[2];
				String produtores = colunas[3];
	
				Movie movie = new Movie();
				movie.setAno(nrAno);
				movie.setTitulo(titulo);
				movie.setEstudio(estudios);
				movie.setProdutor(produtores.trim().replaceAll(" and ", ",").replaceAll(", ", ","));
				
				if (colunas.length == 5 && colunas[4].toLowerCase().equals("yes")) {
					movie.setVencedor(true);
				}
				repository.save(movie);
			}
		}
	}
	
	private static void validaColunas(String[] colunas) throws Exception {
	
		if (colunas.length > 5) {
			throw new Exception("Quantidade de colunas " + colunas.length + " acima da esperada");
		}
		if (colunas.length < 4) {
			throw new Exception("Quantidade de colunas " + colunas.length + " abaixo da esperada");
		}
		if (colunas[0].equals("") || colunas[1].equals("") || colunas[2].equals("") || colunas[3].equals("")) {
			throw new Exception("Colunas 1, 2, 3 e 4 são obrigatórias");
		}
	}
}
