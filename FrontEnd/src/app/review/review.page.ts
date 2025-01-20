import { Component, inject, OnInit } from '@angular/core';
import { MatBottomSheet } from '@angular/material/bottom-sheet';
import { Reviews } from 'src/models/review';
import { BottomSheetComponent } from '../generic-component/bottom-sheet/bottom-sheet.component';
import { MatDialog } from '@angular/material/dialog';
import { AddEditReviewDialogComponent } from './dialog/add-edit-review-dialog/add-edit-review-dialog.component';

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
  readonly dialog = inject(MatDialog);
  private _bottomSheet = inject(MatBottomSheet);


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

  openBottomSheet() {
    this._bottomSheet.open(BottomSheetComponent, {
          data: [{
            name: 'Add a review',
            description: 'Add your a review for a recipe',
            url: '/tabs/recipe/add'
          }]
        }).afterDismissed().subscribe((data: any ) => {
          // redirect ou modale
          console.log(data);
          this.openDialog();
        });
  }

  openDialog(): void {
    const dialogRef = this.dialog.open(AddEditReviewDialogComponent, {
      data: {name: ""},
    });
    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
      if (result !== undefined) {
        console.log(result);
      }
    });
  }

}
