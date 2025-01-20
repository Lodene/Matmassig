import { Component, OnInit } from '@angular/core';
import { Reviews } from 'src/models/review';

@Component({
  selector: 'app-review',
  templateUrl: 'review.page.html',
  styleUrls: ['review.page.scss'],
  standalone: false,
})
export class ReviewPage implements OnInit {

  reviews: Reviews[] = [];
  //  query result
  results: Reviews[] = [];
  starRating: number = 0;
  nameQuery: string = '';
  constructor() {}
  ngOnInit(): void {
    this.mockUpReviews();
    this.results = [...this.reviews];
  }

  handleInput(event: Event) {
    const target = event.target as HTMLIonSearchbarElement;
    this.nameQuery = target.value?.toLowerCase() || '';
    this.filterReviews(this.nameQuery, this.starRating);
  }
  handleRatingInput(event: any) {
    const value = event.detail.value;
    this.starRating = Number(value.toLowerCase()) || 0;
    this.filterReviews(this.nameQuery, this.starRating);
  }
  handleRatingCancel() {

  }
  handleRatingDismiss() {

  }

  filterReviews(nameQuery: string, starQuery: number): void {
    if (starQuery === 0) {
      this.results = this.reviews.filter((r) =>
        r.title.toLowerCase().includes(nameQuery));
    } else {
      this.results = this.reviews.filter((r) =>
      r.title.toLowerCase().includes(nameQuery) &&
     r.rating === starQuery);  
    }
  }

  private mockUpReviews(): void {
      this.reviews = [
        new Reviews("review1", "content1", 5),
        new Reviews("review2", "content2", 2),
        new Reviews("review3", "content3", 3),
      ]
  }


}
