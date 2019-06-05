import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Movie } from '../models/movie';

import * as urls from '../urls';

@Injectable({
  providedIn: 'root'
})
export class MoviesService {

  constructor(private httpClient: HttpClient) { }

  public getMovies() {
    return this.httpClient.get<Movie[]>(`${urls.API_REQUEST_MOVIES}`);
  }

  public getMovie(id) {
    return this.httpClient.get<Movie>(`${urls.API_REQUEST_MOVIES}/${id}`);
  }

  public updateMovie(id, movie) {
    return this.httpClient.put(`${urls.API_REQUEST_MOVIES}/${id}`, movie);
  }

  public createMovie(movie) {
    return this.httpClient.post(`${urls.API_REQUEST_MOVIES}`, movie);
  }
}
