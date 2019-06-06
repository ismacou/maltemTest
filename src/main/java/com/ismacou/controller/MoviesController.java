package com.ismacou.controller;

import com.ismacou.core.Movie;
import com.ismacou.core.MoviesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@RequestMapping("/movies")
public class MoviesController {

	@Autowired
	MoviesRepository moviesRepository;

	@GetMapping
	public ResponseEntity<Object> getAllMovies(){
		Object response = new LinkedHashMap<>();
		try {
			List<Movie> movies = new ArrayList<>();
			moviesRepository.findAll().forEach(movie -> movies.add(movie));
			response = movies;
			return new ResponseEntity(response, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<Object> getMovie(@PathVariable String id){
		Object response = new LinkedHashMap<>();
		try {
			Movie movie = moviesRepository.findById(Long.valueOf(id)).get();
			response = movie;
			return new ResponseEntity(response, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping
	public ResponseEntity<Object> createMovie(@RequestBody Map<String, String> params) {
		HashMap<String, Object> response = new LinkedHashMap<>();
		try {
			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			Date dateMovie = formatter.parse(params.get("releaseDate"));

			Movie movieToCreate = new Movie(params.get("title"), params.get("director"), dateMovie, params.get("type"));
			moviesRepository.save(movieToCreate);
			response.put("", "");
			return new ResponseEntity(response, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<Object> updateMovie(@PathVariable String id, @RequestBody Map<String, String> params) {
		HashMap<String, java.lang.Object> response = new LinkedHashMap<>();
		try {
			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			Date dateMovie = formatter.parse(params.get("releaseDate"));

			Movie movieToUpdate = new Movie(Long.valueOf(id), params.get("title"), params.get("director"), dateMovie, params.get("type"));
			moviesRepository.save(movieToUpdate);
			response.put("", "");
			return new ResponseEntity(response, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteMovie(@PathVariable String id) {
		HashMap<String, Object> response = new LinkedHashMap<>();
		try {
			moviesRepository.deleteById(Long.valueOf(id));
			response.put("", "");
			return new ResponseEntity(response, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
