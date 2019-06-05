package com.ismacou.configuration;

import com.ismacou.core.Movie;
import com.ismacou.core.MoviesRepository;
import org.json.JSONArray;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@org.springframework.context.annotation.Configuration
public class Configuration {

	@Bean
	public CommandLineRunner importData(MoviesRepository repository) {
		return (args) -> {
			File moviesFile = new File(String.valueOf(Paths.get(new File("movies.json").getAbsolutePath())));
			String moviesString = new String(
					Files.readAllBytes(moviesFile.toPath()));
			JSONArray movies= new JSONArray(moviesString);
			for (int i = 0; i < movies.length(); i++) {
				String title = movies.getJSONObject(i).getString("title");
				String director = movies.getJSONObject(i).getString("director");

				String releaseDate = movies.getJSONObject(i).getString("releaseDate");
				DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
				Date dateMovie = formatter.parse(releaseDate);

				String type = movies.getJSONObject(i).getString("type");
				repository.save(new Movie(title, director, dateMovie, type));
			}
		};
	}
}
