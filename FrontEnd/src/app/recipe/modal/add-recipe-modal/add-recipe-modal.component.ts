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
import {MatInputModule} from '@angular/material/input';

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
    ingredients: this.formBuilder.array([]),  
  });

  onNoClick(): void {
    this.dialogRef.close();
  }

  onSubmit(): void {
    if (this.profileForm.valid) {
      let data: DialogData = {
        recipeName: this.recipeName.value,
        recipeDescription: this.recipeDescription.value,
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

  addIngredient() {    
    this.ingredients.push(this.formBuilder.control(''));
  }

}

export interface DialogData {
  recipeName: string;
  recipeDescription: string;
  ingredients: Array<String>
}
