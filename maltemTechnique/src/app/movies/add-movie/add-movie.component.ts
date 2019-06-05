import { Component, OnInit, Input, Output, OnChanges, EventEmitter } from '@angular/core';
import { NgForm, FormControl, FormGroup, Validators, FormBuilder } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Movie } from '../../models/movie';
import { MoviesService } from '../../services/movies.service';
import * as moment from 'moment';

@Component({
  selector: 'app-add-movie',
  templateUrl: './add-movie.component.html',
  styleUrls: ['./add-movie.component.css']
})
export class AddMovieComponent implements OnInit {

  @Input() movie: Movie;
  @Output() returnValue = new EventEmitter();
  public movieManaged = new Movie();
  public formMovie: FormGroup;

  constructor(
    private formBuilder: FormBuilder,
    private moviesService: MoviesService,
    private activateRoute: ActivatedRoute,
    private router: Router) { }

  ngOnInit() {
    this.formMovie = this.formBuilder.group({
      title: [ "", Validators.required ],
      director: [ "", Validators.required ],
      releaseDate: [ "", Validators.required ],
      type: [ "", Validators.required ]
    })
    this.initForm();
  }

  ngOnChanges() {
    if (this.movie) {
      this.movieManaged.title = this.movie.title;
      this.movieManaged.director = this.movie.director;
      this.movieManaged.releaseDate = this.movie.releaseDate;
      this.movieManaged.type = this.movie.type;
      this.initForm();
    }
  }

  initForm() {
    if (this.formMovie) {
      if (this.movie) {
        this.formMovie.patchValue({
          title: this.movieManaged.title,
          director: this.movieManaged.director,
          releaseDate: String(moment.unix(<number> <any>this.movie.releaseDate).format("dd/MM/yyyy")),
          type: this.movieManaged.type
        });
      }
    }
  }

  onSubmit() {
    let movieEdited = {
      title: <string> this.formMovie.value.title,
      director: <string> this.formMovie.value.director,
      releaseDate: new Date(this.formMovie.value.birthdate).getTime() / 1000,
      type: <string> this.formMovie.value.type
    }

    if (this.movie) {
      this.activateRoute.params.subscribe(route => {
        this.moviesService.updateMovie(route.id, movieEdited);
      });
    } else {
      this.moviesService.createMovie(movieEdited);
    }
    this.router.navigate(['movies']);
  }

  return() {
    this.returnValue.emit(null);
    this.router.navigate(['movies']);
  }

}
