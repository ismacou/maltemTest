package com.ismacou.controller;

import com.ismacou.core.Movie;
import com.ismacou.core.MoviesRepository;
import org.json.JSONArray;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.annotation.Resource;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RunWith(MockitoJUnitRunner.class)
public class MoviesControllerTest {

	@InjectMocks
	private MoviesController moviesController;

	@Mock
	MoviesRepository moviesRepository;

	@Before
	public void init() throws ParseException {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void getAllMovies_test_Ok() throws ParseException {
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date dateMovie1 = formatter.parse("12/12/2012");
		Date dateMovie2 = formatter.parse("20/02/2002");

		ResponseEntity<Object> movies = moviesController.getAllMovies();
		Assert.assertTrue(movies.getStatusCode() == HttpStatus.OK );
	}

	@Test
	public void getMovie_test_Ok() throws ParseException {
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date dateMovie1 = formatter.parse("12/12/2012");

		Movie movieData = new Movie("title1", "director1", dateMovie1, "type1");


		Mockito.when(moviesRepository.findById(Long.valueOf(1))).thenReturn(java.util.Optional.of(movieData));

		ResponseEntity<Object> movie = moviesController.getMovie("1");
		Assert.assertTrue(movie.getStatusCode() == HttpStatus.OK );
	}

	@Test
	public void createMovie_test_Ok() throws ParseException {

		Map<String, String> movieParams = new HashMap<>();
		movieParams.put("title", "title1");
		movieParams.put("director", "director1");
		movieParams.put("releaseDate", "12/12/2012");
		movieParams.put("type", "type1");

		ResponseEntity<Object> movie = moviesController.createMovie(movieParams);
		Assert.assertTrue(movie.getStatusCode() == HttpStatus.CREATED );
	}

	@Test
	public void updateMovie_test_Ok() throws ParseException {

		Map<String, String> movieParams = new HashMap<>();
		movieParams.put("title", "title1");
		movieParams.put("director", "director1");
		movieParams.put("releaseDate", "12/12/2012");
		movieParams.put("type", "type1");

		ResponseEntity<Object> movie = moviesController.updateMovie("1", movieParams);
		Assert.assertTrue(movie.getStatusCode() == HttpStatus.OK );
	}

	@Test
	public void delete_test_Ok() throws ParseException {

		ResponseEntity<Object> movie = moviesController.deleteMovie("1");
		Assert.assertTrue(movie.getStatusCode() == HttpStatus.OK );
	}
}
