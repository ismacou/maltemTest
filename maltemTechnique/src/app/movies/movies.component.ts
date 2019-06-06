import { Component, OnInit, DoCheck } from '@angular/core';
import { NgForm, FormControl, FormGroup, Validators, FormBuilder } from '@angular/forms';
import { MoviesService } from '../services/movies.service';
import { Movie } from '../models/movie';

@Component({
  selector: 'app-movies',
  templateUrl: './movies.component.html',
  styleUrls: ['./movies.component.css']
})
export class MoviesComponent implements OnInit, DoCheck {
  private movies: Movie[];
  private moviesToSee: Movie[];
  public movieToEdited = null;
  public formMovie: FormGroup;

  constructor(
    private moviesService: MoviesService,
    private formBuilder: FormBuilder) { }

  ngOnInit() {
    this.formMovie = this.formBuilder.group({
      titleSearch: ["", ""],
      directorSearch: ["", ""],
      typeSearch: ["", ""],
      yearSearch: ["", ""]
    });
    this.formMovie.setValue({
      titleSearch: "",
      directorSearch: "",
      typeSearch: "",
      yearSearch: ""
    });
    this.movieToEdited = null;
    this.moviesService.getMovies().subscribe(movies => {
      this.movies = movies;
    })
  }

  ngDoCheck() {
      if (this.formMovie.value.titleSearch) {
        this.moviesToSee = this.movies.filter(movie => movie.title.toLowerCase().search(this.formMovie.value.titleSearch.toLowerCase()) !== -1);
      } else if (this.formMovie.value.directorSearch) {
        this.moviesToSee = this.movies.filter(movie => movie.director.toLowerCase().search(this.formMovie.value.directorSearch.toLowerCase()) !== -1);
      } else if (this.formMovie.value.typeSearch) {
        this.moviesToSee = this.movies.filter(movie => movie.type.toLowerCase().search(this.formMovie.value.typeSearch.toLowerCase()) !== -1);
      } else if (this.formMovie.value.yearSearch) {
        this.moviesToSee = this.movies.filter(movie => movie.releaseDate.toString().split("-")[0].search(this.formMovie.value.yearSearch) !== -1);
      } else {
        this.moviesToSee = this.movies;
      }
  }

  sortByTitle() {
    this.movies.sort((a, b) => a.title.localeCompare(b.title))
  }

  selectedMovie(event) {
    this.movieToEdited = event;
  }

  initialize(event) {
    this.moviesService.getMovies().subscribe(movies => {
      this.movies = movies;
    })
    this.movieToEdited = null;
  }

}
