import { CommonModule } from '@angular/common';
import { Component, inject, model, OnInit } from '@angular/core';
import { FormBuilder, FormArray, FormsModule, ReactiveFormsModule, Validators, FormControl } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import {
  MAT_DIALOG_DATA,
  MatDialogActions,
  MatDialogClose,
  MatDialogContent,
  MatDialogModule,
  MatDialogRef,
  MatDialogTitle,
} from '@angular/material/dialog';
import {MatFormFieldModule} from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import {MatInputModule} from '@angular/material/input';
import { Ingredient } from 'src/models/ingredient';

@Component({
  selector: 'app-add-recipe-modal',
  templateUrl: './add-recipe-modal.component.html',
  styleUrls: ['./add-recipe-modal.component.scss'],
  imports: [CommonModule,
    MatFormFieldModule,
    MatInputModule,
    FormsModule,
    MatButtonModule,
    ReactiveFormsModule,
    MatIconModule,
    MatDialogTitle,
    MatDialogModule,
    MatDialogContent,
    MatDialogActions,
    MatDialogClose,]
})
export class AddRecipeModalComponent implements OnInit {

  constructor() { }

  ngOnInit() {}

  readonly dialogRef = inject(MatDialogRef<AddRecipeModalComponent>);
  readonly data = inject<DialogData>(MAT_DIALOG_DATA);

  private formBuilder = inject(FormBuilder);

  profileForm = this.formBuilder.group({    
    recipeName: ['', Validators.required],    
    recipeDescription: [''],
    recipeCooking: [''],
    ingredients: this.formBuilder.array([], Validators.required),  
  });

  onNoClick(): void {
    this.dialogRef.close();
  }

  onSubmit(): void {
    if (this.profileForm.valid) {
      let data: DialogData = {
        recipeName: this.recipeName.value,
        recipeDescription: this.recipeDescription.value,
        recipeCooking: this.recipeCooking.value,
        ingredients: this.ingredients.value
      };
      this.dialogRef.close(data);
    } else {
      console.log("erreur");
    }
  }

  get ingredients() {    
    return this.profileForm.get('ingredients') as FormArray;  
  }

  get recipeName() {
    return this.profileForm.get('recipeName') as FormControl;
  }

  get recipeDescription() {
    return this.profileForm.get('recipeDescription') as FormControl;
  }

  get recipeCooking() {
    return this.profileForm.get('recipeCooking') as FormControl;
  }

  addIngredient() {
    const ingredientForm = this.formBuilder.group({
      ingredientName: ['', Validators.required],
      quantity: [1, Validators.required]
    });
    this.ingredients.push(ingredientForm);
  }

  deleteIngredient(ingIndex: number) {
    this.ingredients.removeAt(ingIndex);
  }

}

export interface DialogData {
  recipeName: string;
  recipeDescription: string;
  recipeCooking: string
  ingredients: Array<Ingredient>
}
