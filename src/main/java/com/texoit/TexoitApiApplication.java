package com.texoit;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.texoit.model.Movie;
import com.texoit.repository.MovieRepository;

@SpringBootApplication
public class TexoitApiApplication {

	public static void main(String[] args) {

		SpringApplication.run(TexoitApiApplication.class, args);
		
		try {
			loadCsvFile("movielist.csv");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void loadCsvFile(String diretorio) throws Exception {
		
		
		Path path = Paths.get(diretorio);
	
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
				String produtoes = colunas[3];
				boolean vencedor = false;
	
				if (colunas.length == 5 && colunas[4].toLowerCase().equals("yes")) {
					vencedor = true;
				}
				
				if(vencedor) {
					System.out.println(linha);
				}
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