import { Component, OnInit, Input, Output, OnChanges, EventEmitter } from '@angular/core';
import { NgForm, FormControl, FormGroup, Validators, FormBuilder } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Movie } from '../../models/movie';
import { MoviesService } from '../../services/movies.service';
import * as moment from 'moment';

@Component({
  selector: 'app-movie',
  templateUrl: './movie.component.html',
  styleUrls: ['./movie.component.css']
})
export class MovieComponent implements OnInit {

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
      }, {validator: this.date("releaseDate")})
      this.initForm();
    }

    date(releaseDate: string) {
      return (group: FormGroup): {[key: string]: any} => {
        let date = group.controls[releaseDate];
        if (date.value === "Invalid date") {
          return {
            date: "Not valid"
          };
        }
        return {};
      }
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
            releaseDate: moment(new Date(this.movie.releaseDate).getTime()).format("YYYY-MM-DD"),
            type: this.movieManaged.type
          });
        }
      }
    }

    onSubmit() {
      let movieEdited = {
        title: <string> this.formMovie.value.title,
        director: <string> this.formMovie.value.director,
        releaseDate: moment(this.formMovie.value.releaseDate).format("DD/MM/YYYY"),
        type: <string> this.formMovie.value.type
      }
      if (this.movie.id) {
        this.moviesService.updateMovie(this.movie.id, movieEdited).subscribe(
          success => {
            this.return();
          }
        );
      } else {
        this.moviesService.createMovie(movieEdited).subscribe(
          success => {
            this.return();
          }
        );
      }
      this.return();
    }

    return() {
      this.returnValue.emit(null);
      this.router.navigate(['movies']);
    }

    delete() {
      this.moviesService.deleteMovie(this.movie.id).subscribe(
        success => {
          this.return();
        }
      );
    }

  }
