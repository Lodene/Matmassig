import { CommonModule } from '@angular/common';
import { Component, inject, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatDialogTitle, MatDialogModule, MatDialogContent, MatDialogActions, MatDialogClose, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { Ingredient } from 'src/models/ingredient';

@Component({
  selector: 'app-add-ingredient-modal',
  templateUrl: './add-ingredient-modal.component.html',
  styleUrls: ['./add-ingredient-modal.component.scss'],
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
      MatDialogClose]
})
export class AddIngredientModalComponent  implements OnInit {

  constructor() { }

  readonly dialogRef = inject(MatDialogRef<AddIngredientModalComponent>);
  readonly data = inject<DialogData>(MAT_DIALOG_DATA);
  private formBuilder = inject(FormBuilder);

  ingredientForm = this.formBuilder.group({
    ingredients: this.formBuilder.array([], Validators.required),  
  });

  ngOnInit() {}

  onSubmit(): void {
      if (this.ingredientForm.valid) {
        let data: DialogData = {
          ingredients: this.ingredients.value
        };
        this.dialogRef.close(data);
      } else {
        console.log("erreur");
      }
    }

    get ingredients() {    
      return this.ingredientForm.get('ingredients') as FormArray;  
    }

  addIngredient() {
    this.ingredients.push(this.formBuilder.group({
      ingredientName: ['', Validators.required],
      quantity: [1, Validators.required]
    }));
  }

  deleteIngredient(ingIndex: number) {
    this.ingredients.removeAt(ingIndex);
  }
}

export interface DialogData {
  ingredients: Array<Ingredient>
}