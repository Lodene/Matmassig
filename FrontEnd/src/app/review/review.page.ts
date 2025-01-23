import { Component, Inject, inject, Input, OnInit } from '@angular/core';
import { MatBottomSheet } from '@angular/material/bottom-sheet';
import { Review } from 'src/models/review';
import { BottomSheetComponent } from '../generic-component/bottom-sheet/bottom-sheet.component';
import { MatDialog } from '@angular/material/dialog';
import { AddEditReviewDialogComponent } from './dialog/add-edit-review-dialog/add-edit-review-dialog.component';
import { ReviewFormModel } from './models/reviewFormModel';
import { HttpClientService } from '../services/http-client.service';
import { MatIcon, MatIconModule } from '@angular/material/icon';
import { Recipe } from 'src/models/recipe';
import { YesNoDialogComponent } from '../generic-component/yes-no-dialog/yes-no-dialog.component';
import { WebSocketService } from '../websocket/web-socket.service';
import { concat, concatMap, Observable } from 'rxjs';

@Component({
  selector: 'app-review',
  templateUrl: 'review.page.html',
  styleUrls: ['review.page.scss'],
  standalone: false
})
export class ReviewPage implements OnInit {


  @Input() deleteMode: boolean = false;
  
  reviews: Review[] = [];
  //  query result
  results: Review[] = [];
  starRating: number = 0;
  nameQuery: string = '';
  readonly dialog = inject(MatDialog);
  private _bottomSheet = inject(MatBottomSheet);
  selectedReviewForDelete: Review[] = []


  review$: Observable<any> = new Observable;

  constructor(
    private webSocket: WebSocketService,
    private reviewHttpService: HttpClientService) {}
    
  ngOnInit(): void {
    this.mockUpReviews();
    this.results = [...this.reviews];
    this.review$ = this.webSocket.getReview().pipe()
    console.log(this.review$);
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
        r.comment.toLowerCase().includes(nameQuery));
    } else {
      this.results = this.reviews.filter((r) =>
      r.comment.toLowerCase().includes(nameQuery) &&
     r.rating === starQuery);  
    }
  }

  private mockUpReviews(): void {
      this.reviews = [
        new Review(0, "mail1", "review1", "content1", 5, 1, 123),
        new Review(1, "mail2", "review2", "content2", 2, 1, 123),
        new Review(2, "mail3", "review3", "content3", 3, 1, 123),
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
    dialogRef.afterClosed().subscribe((result: ReviewFormModel | undefined) => {
      console.log('The dialog was closed');
      if (result !== undefined) {
        this.reviewHttpService.postReview(result).subscribe((result: unknown) => {
          console.log(result);
        })
      }
    });
  }
  deleteModeActivation(): void {
    this.selectedReviewForDelete = [];
    this.deleteMode = !this.deleteMode;
  }
  getDeleteIcon() : string {
    return this.deleteMode ? 'cancel' : 'delete';
  }

  checkedRecipeDelete(review: Review) {
    if(!this.selectedReviewForDelete.includes(review)) 
    {
      this.selectedReviewForDelete.push(review);
    } else {
      this.selectedReviewForDelete = this.selectedReviewForDelete.filter(
        r => r.id !== review.id);
    }
  }


  deleteSelectedReviews():void {
    const dialogRef = this.dialog.open(YesNoDialogComponent, {
      data: {
        title: "Delete review",
        question: "Delete thoses reviews ?",
        color: 'danger',
        deleteList: this.selectedReviewForDelete
      },
    });
    dialogRef.afterClosed().subscribe((result: boolean | undefined) => {
      if (result === true) {
        
        for (let review of this.selectedReviewForDelete) {
          this.reviewHttpService.deleteReview(review).subscribe(deleteResult => {
              console.log(deleteResult);
          })
        }
      }
    });    
  }
}
