import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Review } from 'src/models/review';

@Injectable({
  providedIn: 'root',
})
export class ReviewHttpService {
  constructor(private http: HttpClient) {}

  // deleteReviews(reviewsToDelete: Review[]) {
  //   return this.http.delete('http://localhost:8080/api/orchestrator/review/create')
  // }

  deleteReview(review: any): Observable<any> {
    const reviewDTO = new Review(review.id, review.email, review.title, review.comment, review.rating, review.recipeId, review.userId)
    const token = localStorage.getItem('auth-user');
    const options = {
      headers: new HttpHeaders({
        responseType: 'application/json',
        Authorization: 'Bearer ' + token,
      }),
      body: reviewDTO,
    };
    return this.http.delete(
      'http://localhost:8080/api/orchestrator/review/delete', options
    );
  }

  postReview(review: any): Observable<any> {
    const token = localStorage.getItem('auth-user');
    const options = {
      headers: new HttpHeaders({
        responseType: 'application/json',
        Authorization: 'Bearer ' + token,
      }),
    };
    return this.http.post(
      'http://localhost:8080/api/orchestrator/review/create',
      review,
      options
    );
  }

  getReview(): Observable<any> {
    const token = localStorage.getItem('auth-user');
    const options = {
      headers: new HttpHeaders({
        responseType: 'application/json',
        Authorization: 'Bearer ' + token,
      }),
    };
    return this.http.get("http://localhost:8080/api/orchestrator/review/getbyuser", options);
  }
}
