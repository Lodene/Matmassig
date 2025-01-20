import { Component, OnInit, Inject, ViewChild, AfterViewInit} from '@angular/core';
import {
  MAT_DIALOG_DATA,
  MatDialog,
  MatDialogActions,
  MatDialogClose,
  MatDialogContent,
  MatDialogModule,
  MatDialogRef,
  MatDialogTitle,
} from '@angular/material/dialog';
import {MatSelect, MatSelectModule} from '@angular/material/select';
import {FormControl, FormGroup, FormsModule, ReactiveFormsModule} from '@angular/forms';
import {MatButtonModule} from '@angular/material/button';
import { NgxMatSelectSearchModule } from 'ngx-mat-select-search';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import { CommonModule } from '@angular/common';
import { ReplaySubject, Subject, take, takeUntil } from 'rxjs';
import { StarRatingColor, StarReviewComponent } from 'src/app/generic-component/star-review/star-review.component';





@Component({
  selector: 'app-add-edit-review-dialog',
  templateUrl: './add-edit-review-dialog.component.html',
  styleUrls: ['./add-edit-review-dialog.component.scss'],
  imports: [StarReviewComponent, ReactiveFormsModule, NgxMatSelectSearchModule, CommonModule, FormsModule, MatDialogModule, MatSelectModule, MatButtonModule, MatFormFieldModule, MatInputModule]
})
export class AddEditReviewDialogComponent  implements OnInit, AfterViewInit {

  // star rating config
  rating:number = 2;
  starCount:number = 5;
  starColor:StarRatingColor = StarRatingColor.accent;
  starColorP:StarRatingColor = StarRatingColor.primary;
  starColorW:StarRatingColor = StarRatingColor.warn;
  starColorG:StarRatingColor = StarRatingColor.golden;

  protected recipes: any[] = [
  'Recette 1',
  'Recette 2',
  'Recette 3',
  'Recette 4'
  ];

  public RecipeFormControl: FormControl<any> = new FormControl<any>(null);
  public RecipeFilterCtrl: FormControl<any> = new FormControl<string>('');
  public filteredRecipes: ReplaySubject<any[]> = new ReplaySubject<any[]>(1);

  ratingControl = new FormControl('');
  descriptionControl = new FormControl('');

  form = new FormGroup({
    recipeControl: this.RecipeFormControl,
    ratingControl: this.ratingControl,
    descriptionControl: this.descriptionControl,    
});

  @ViewChild('singleSelect', { static: true })
  singleSelect!: MatSelect;

  /** Subject that emits when the component has been destroyed. */
  protected _onDestroy = new Subject<void>();


  selectedRecipes = [...this.recipes];

  constructor(@Inject(MAT_DIALOG_DATA) public data: {name: string},
  private dialogRef: MatDialogRef<AddEditReviewDialogComponent>) { }

  ngOnInit() {
    this.RecipeFormControl.setValue('');
    this.filteredRecipes.next(this.recipes.slice());
    this.RecipeFilterCtrl.valueChanges
    .pipe(takeUntil(this._onDestroy))
    .subscribe(() => {
      this.filterRecipe();
    });
  }
  ngAfterViewInit() {
    this.setInitialValue();
  }

  protected setInitialValue() {
    this.filteredRecipes
      .pipe(take(1), takeUntil(this._onDestroy))
      .subscribe(() => {
        // setting the compareWith property to a comparison function
        // triggers initializing the selection according to the initial value of
        // the form control (i.e. _initializeSelection())
        // this needs to be done after the filteredBanks are loaded initially
        // and after the mat-option elements are available
        this.singleSelect.compareWith = (a: any, b: any) => a && b && a.id === b.id;
      });
  }

  protected filterRecipe() {
    if (!this.recipes) {
      return;
    }
    // get the search keyword
    let search = this.RecipeFilterCtrl.value;
    console.log(search);
    if (!search) {
      this.filteredRecipes.next(this.recipes.slice());
      return;
    } else {
      search = search.toLowerCase();
    }
    // filter the banks
    this.filteredRecipes.next(
      this.recipes.filter(recipe => recipe.toLowerCase().indexOf(search) > -1)
    );
  }

  
  onKey(value: any) { 
    this.selectedRecipes = this.search(value.target.value as string);
  }

  triggerEvent(event: any) {
    console.log(event);
    if (this.selectedRecipes.length === 0) {
      this.selectedRecipes = [...this.recipes];
    }
  }

  search(value: string) {
    let filter = value.toLowerCase();
  return this.recipes.filter(option => option.toLowerCase().includes(filter));
  }

  onRatingChanged(rating: number){
    this.rating = rating;
    this.form.get('ratingControl')?.setValue(String(rating))
  }

  // //     recipeControl: this.RecipeFormControl,
  //   ratingControl: this.ratingControl,
  //   descriptionControl: this.descriptionControl,  
  onSubmit() {
    if (this.form.valid) {
      this.dialogRef.close({
        recipe: this.form.get('recipeControl')?.value,
        rating: this.form.get('ratingControl')?.value,
        description: this.form.get('descriptionControl')?.value
      })
    }
  }

}
