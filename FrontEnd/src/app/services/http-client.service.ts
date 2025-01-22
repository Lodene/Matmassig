import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ReviewFormModel } from '../review/models/reviewFormModel';
import { Observable } from 'rxjs';
import { Review } from 'src/models/review';

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


  deleteReview(review: Review): Observable<any> {

    const reviewRequest: ReviewRequest = {
      id: review.id,
      userId: review.recipeId
    }

    const options = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      }),
      body: 
      reviewRequest
    };


    return this.http.delete('http://localhost:8080/api/orchestrator/review/delete', options);
  }

  // getReviewByUser(): Observable<any> {
  //   return this.http.get('http:')
  // }

}

// used to build request to backend
interface ReviewRequest {
  id: number;
  userId: number;
}