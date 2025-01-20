import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, OnInit, Output, ViewEncapsulation } from '@angular/core';
import { MatIconButton } from '@angular/material/button';
import { MatError } from '@angular/material/form-field';
import { MatIcon, MatIconModule } from '@angular/material/icon';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatTooltipModule } from '@angular/material/tooltip';

@Component({
  selector: 'app-star-review',
  templateUrl: './star-review.component.html',
  styleUrls: ['./star-review.component.scss'],
  imports: [MatTooltipModule, MatIconButton, MatError, CommonModule, MatIconModule, MatIcon],
  encapsulation: ViewEncapsulation.Emulated
})
export class StarReviewComponent  implements OnInit {
  
  @Input('rating') rating: number = 0;
  @Input('starCount') starCount: number = 5;
  @Input('color') color: string = 'success';
  
  @Output() ratingUpdated = new EventEmitter();

  snackBarDuration: number = 2000;
  ratingArr: any[] = [];
  
  constructor(private snackBar: MatSnackBar) {}

  ngOnInit() {
    console.log(this.rating, this.color, this.starCount);
    for (let index = 0; index < this.starCount; index++) {
      this.ratingArr.push(index);
    }
  }
  onClick(rating: any) {
    this.snackBar.open('You rated ' + rating + ' / ' + this.starCount, '', {
      duration: this.snackBarDuration
    });
    this.ratingUpdated.emit(rating);
    return false;
  }
  showIcon(index:number) {
    if (this.rating >= index + 1) {
      return 'star';
    } else {
      return 'star_border';
    }
  }
}

export enum StarRatingColor {
  primary = "primary",
  accent = "accent",
  warn = "warn",
  golden = "success"
}