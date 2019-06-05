package com.ismacou.core;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Movie {

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Long id;
	private String title;
	private String director;
	private Date releaseDate;
	private String type;

	public Movie() {
	}

	public Movie(String title, String director, Date releaseDate, String type) {
		this.title = title;
		this.director = director;
		this.releaseDate = releaseDate;
		this.type = type;
	}

	public Movie(Long id, String title, String director, Date releaseDate, String type) {
		this.id = id;
		this.title = title;
		this.director = director;
		this.releaseDate = releaseDate;
		this.type = type;
	}

	public Long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Movie{" +
				"id=" + id +
				", title='" + title + '\'' +
				", director='" + director + '\'' +
				", releaseDate=" + releaseDate +
				", type='" + type + '\'' +
				'}';
	}
}
