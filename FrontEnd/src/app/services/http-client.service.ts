import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ReviewFormModel } from '../review/models/reviewFormModel';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class HttpClientService {

  constructor(private http: HttpClient) {

  }

  // getRecipe() {
  //   this.http.get<unknown>('127.0.0.1/8080/').subscribe(config => {
  //     console.log(config);
  //   });
  // }

  postReview(reviewForm: ReviewFormModel): Observable<any> {
    return this.http.post('http://localhost:8080/api/orchestrator/review/create', reviewForm)
  } 

}
