import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Review } from 'src/models/review';

@Injectable({
  providedIn: 'root'
})
export class ReviewHttpService {

  constructor(private http: HttpClient) {
    
  }

  // deleteReviews(reviewsToDelete: Review[]) {
  //   return this.http.delete('http://localhost:8080/api/orchestrator/review/create')
  // }

  deleteReview(review: Review): Observable<any> {
    return this.http.delete('http://localhost:8080/api/orchestrator/review/'+ review.id);
  }

}
