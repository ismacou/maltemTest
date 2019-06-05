import { Component, OnInit } from '@angular/core';
import { MoviesService } from '../services/movies.service';
import { Movie } from '../models/movie';

@Component({
  selector: 'app-movies',
  templateUrl: './movies.component.html',
  styleUrls: ['./movies.component.css']
})
export class MoviesComponent implements OnInit {
  private movies: Movie[];
  public movieToEdited = null;

  constructor(private moviesService: MoviesService) { }

  ngOnInit() {
    this.movieToEdited = null;
    this.moviesService.getMovies().subscribe(movies => {
      this.movies = movies;
    })
  }

  sortByTitle() {
    this.movies.sort((a, b) => a.title.localeCompare(b.title))
  }

  selectedMovie(event) {
    this.movieToEdited = event;
  }

  initialize(event) {
    this.movieToEdited = null;
  }

}
