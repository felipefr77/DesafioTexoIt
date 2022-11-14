package com.texoit.controller;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.texoit.model.DadosResult;
import com.texoit.model.Movie;
import com.texoit.model.Result;
import com.texoit.repository.MovieRepository;

import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;

@WebMvcTest
public class MovieControllerTest {
	
	@Autowired
	private MovieController movieController;
	
	@MockBean
	private MovieRepository movieRepository;
	
	@BeforeEach
	public void setUp() {
		RestAssuredMockMvc.standaloneSetup(this.movieController);
	}
	
	@Autowired
	private WebApplicationContext wac;
	
	@Test
	public void deveRetornarObjetoValido_QuandoPesquisarFilmesPremiados( ) throws JsonProcessingException, Exception {
		
		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
		MockMvc mockMvc = builder.build();
		
		List<Movie> filmes = carregaFilmes();
		
		Mockito.when(this.movieRepository.findAll())
			.thenReturn(filmes);

		ObjectMapper objectMapper = new ObjectMapper();
		
		ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/movies/pesquisar")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON));
		
		String stringJson = response.andReturn().getResponse().getContentAsString();
		
		Result result = objectMapper.readValue(stringJson, Result.class);
	
		for( DadosResult dados : result.getMin() ){
			assertEquals("Ciclano da Silva", dados.getProducer());
			assertEquals(1, dados.getInterval());
			assertEquals(2007, dados.getPreviousWin());
			assertEquals(2008, dados.getFollowingWin());
		}
		
		for( DadosResult dados : result.getMax() ){
			assertEquals("Fulano da Silva", dados.getProducer());
			assertEquals(20, dados.getInterval());
			assertEquals(2000, dados.getPreviousWin());
			assertEquals(2020, dados.getFollowingWin());
		}
	}
	
	@Test
	public void deveRetornarSucesso_QuandoPesquisarFilmesPremiados() {
				
		List<Movie> filmes = carregaFilmes();
		
		Mockito.when(this.movieRepository.findAll())
			.thenReturn(filmes);
		
		RestAssuredMockMvc.given()
			.accept(ContentType.JSON)
		.when()
			.get("api/movies/pesquisar")
		.then()
			.expect(result -> status().isOk());
	}
	
	@Test
	public void deveRetornarInstanciaResult_QuandoPesquisarFilmesPremiados() {
				
		List<Movie> filmes = carregaFilmes();
		
		Mockito.when(this.movieRepository.findAll())
			.thenReturn(filmes);
		
		RestAssuredMockMvc.given()
			.accept(ContentType.JSON)
		.when()
			.get("api/movies/pesquisar")
		.then()
			.expect(result -> instanceOf(Result.class));
	}
	
	private List<Movie> carregaFilmes() {
		
		Movie filme1 = new Movie();
		filme1.setAno(2000);
		filme1.setTitulo("Meu filme 1");
		filme1.setEstudio("Estudio Teste");
		filme1.setProdutor("Fulano da Silva");
		filme1.setVencedor(true);
		
		Movie filme2 = new Movie();
		filme2.setAno(2020);
		filme2.setTitulo("Meu filme 2");
		filme2.setEstudio("Estudio Teste 2");
		filme2.setProdutor("Fulano da Silva");
		filme2.setVencedor(true);
		
		Movie filme3 = new Movie();
		filme3.setAno(2007);
		filme3.setTitulo("Meu filme 3");
		filme3.setEstudio("Estudio Teste 3");
		filme3.setProdutor("Ciclano da Silva");
		filme3.setVencedor(true);
		
		Movie filme4 = new Movie();
		filme4.setAno(2008);
		filme4.setTitulo("Meu filme 4");
		filme4.setEstudio("Estudio Teste 4");
		filme4.setProdutor("Ciclano da Silva");
		filme4.setVencedor(true);
		
		Movie filme5 = new Movie();
		filme5.setAno(2010);
		filme5.setTitulo("Meu filme 5");
		filme5.setEstudio("Estudio Teste 5");
		filme5.setProdutor("Beltrano da Silva");
		filme5.setVencedor(true);
		
		Movie filme6 = new Movie();
		filme6.setAno(2015);
		filme6.setTitulo("Meu filme 6");
		filme6.setEstudio("Estudio Teste 6");
		filme6.setProdutor("Beltrano da Silva");
		filme6.setVencedor(true);
		
		List<Movie> filmes = new ArrayList<>();
		filmes.add(filme1);
		filmes.add(filme2);
		filmes.add(filme3);
		filmes.add(filme4);
		filmes.add(filme5);
		filmes.add(filme6);
		
		return filmes;
	}
}
