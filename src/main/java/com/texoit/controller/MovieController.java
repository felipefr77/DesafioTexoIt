package com.texoit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.texoit.model.Movie;
import com.texoit.repository.MovieRepository;

@RestController
@RequestMapping("/movies")
public class MovieController {
	
	@Autowired
	private MovieRepository movieRepository;
	
	@GetMapping
	public List<Movie> listar() {
		return movieRepository.findAll();
	}
	
	@PostMapping
	public Movie adicionar(@RequestBody Movie movie) {
		System.out.println("Cheguei aqui");
		return movieRepository.saveAndFlush(movie);
	}
}
