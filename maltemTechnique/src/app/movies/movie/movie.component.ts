import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { MoviesService } from '../../services/movies.service';
import { Movie } from '../../models/movie';

@Component({
  selector: 'app-movie',
  templateUrl: './movie.component.html',
  styleUrls: ['./movie.component.css']
})
export class MovieComponent implements OnInit {
  public movie: Movie;

  constructor(
    private router: ActivatedRoute,
    private moviesService: MoviesService) {
    this.router.params.subscribe(route => {
      this.moviesService.getMovie(route.id).subscribe(movie => {
        this.movie = movie;
      })
    });
  }

  ngOnInit() {
  }

}
