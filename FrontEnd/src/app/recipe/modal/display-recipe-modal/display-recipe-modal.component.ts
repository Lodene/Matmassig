import { Component, inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { Recipe } from 'src/models/recipe';

@Component({
  selector: 'app-display-recipe-modal',
  templateUrl: './display-recipe-modal.component.html',
  styleUrls: ['./display-recipe-modal.component.scss'],
  standalone: false,
})
export class DisplayRecipeModalComponent implements OnInit {
  readonly dialogRef = inject(MatDialogRef<DisplayRecipeModalComponent>);
  readonly data = inject<any>(MAT_DIALOG_DATA);

  recipe: Recipe = {
    id: -1,
    title: '',
    description: '',
    ingredients: [],
    cookTime: -1,
    avgReviewScore: -1,
    instructions: '',
  };

  constructor() {}

  ngOnInit() {
    this.recipe = this.data.recipe;
    console.log(this.recipe);
  }

  addReview() {
    this.dialogRef.close(true);
  }
}
