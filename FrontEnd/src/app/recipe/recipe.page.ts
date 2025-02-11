import {
  AfterContentInit,
  AfterViewChecked,
  AfterViewInit,
  Component,
  inject,
  OnInit,
} from '@angular/core';
import { Ingredient } from 'src/models/ingredient';
import { Recipe } from 'src/models/recipe';
import {
  MatBottomSheet,
  MatBottomSheetModule,
  MatBottomSheetRef,
} from '@angular/material/bottom-sheet';
import { MatListModule } from '@angular/material/list';
import { MatButtonModule } from '@angular/material/button';
import { BottomSheetComponent } from '../generic-component/bottom-sheet/bottom-sheet.component';
import { AddRecipeModalComponent } from './modal/add-recipe-modal/add-recipe-modal.component';
import { MatDialog } from '@angular/material/dialog';
import { RecipeHttpService } from '../services/recipe-http.service';
import { WebSocketService } from '../websocket/web-socket.service';
import { DisplayRecipeModalComponent } from './modal/display-recipe-modal/display-recipe-modal.component';
import { AddEditReviewDialogComponent } from '../review/dialog/add-edit-review-dialog/add-edit-review-dialog.component';
import { ReviewFormModel } from '../review/models/reviewFormModel';
import { ReviewHttpService } from '../services/review.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-recipe',
  templateUrl: 'recipe.page.html',
  styleUrls: ['recipe.page.scss'],
  standalone: false,
})
export class RecipePage implements OnInit, AfterContentInit {
  results: Recipe[] = [];
  recipes: Recipe[] = [];
  nameQuery: string = '';

  private _bottomSheet = inject(MatBottomSheet);
  private readonly dialogAdd = inject(MatDialog);
  private readonly recipeModal = inject(MatDialog);
  readonly dialogAddReview = inject(MatDialog);
  private _snackBar = inject(MatSnackBar);

  constructor(
    private recipeHttpService: RecipeHttpService,
    private webSocket: WebSocketService,
    private reviewHttpService: ReviewHttpService
  ) {
    this.retrieveRecipeByUser();
    this.createdReviewEvent();
  }
  ngAfterContentInit(): void {
    this.retrieveRecipeByUser();
  }

  ngOnInit(): void {
    // this.mockUpRecipe();
    this.retrieveRecipeByUser();
  }

  // mockUpRecipe(): void {
  //   this.recipes = [
  //     new Recipe(
  //       0,
  //       'Pâtes à la bolognaisePâtes à la bolognaisePâtes à la bolognaise 1',
  //       "I'm a very long recipe, look at how long I am. In fact, i'm so long that i'm starting to fall. Actually, I'm not that long and i'm starting to have no idea what to write to make this text any longer",
  //       [
  //         new Ingredient(
  //           0,
  //           'carrot',
  //           2,
  //           'https://minecraft.wiki/images/Carrot_JE3_BE2.png?5e9c8'
  //         ),
  //         new Ingredient(
  //           1,
  //           'potato',
  //           5,
  //           'https://minecraft.wiki/images/Potato_JE3_BE2.png?27685'
  //         ),
  //       ],
  //       90,
  //       4.2
  //     ),
  //     new Recipe(
  //       0,
  //       'Recipe 1',
  //       "I'm a very long recipe, look at how long I am. In fact, i'm so long that i'm starting to fall. Actually, I'm not that long and i'm starting to have no idea what to write to make this text any longer",
  //       [
  //         new Ingredient(
  //           0,
  //           'carrot',
  //           2,
  //           'https://minecraft.wiki/images/Carrot_JE3_BE2.png?5e9c8'
  //         ),
  //         new Ingredient(
  //           1,
  //           'potato',
  //           5,
  //           'https://minecraft.wiki/images/Potato_JE3_BE2.png?27685'
  //         ),
  //       ],
  //       90,
  //       4.2
  //     ),
  //   ];
  //   this.results = [...this.recipes];
  // }

  retrieveRecipeByUser(): void {
    this.recipeHttpService
      .getRecipeByUser('1')
      .subscribe((recipes: unknown) => {
        // console.log(recipes);
        this.webSocket
          .getRecipes()
          .pipe()
          .subscribe((result) => {
            const recipes = JSON.parse(
              '{' + result.message.substring(1, result.message.length)
            );
            console.log(recipes.list);
            this.recipes = [...recipes.list];
            console.log(this.recipes);
            this.results = [...this.recipes];
          });
      });
  }

  handleInput(event: Event): void {
    const target = event.target as HTMLIonSearchbarElement;
    this.nameQuery = target.value?.toLowerCase() || '';
    this.filterReviews(this.nameQuery);
  }

  filterReviews(nameQuery: string) {
    this.results = this.recipes.filter(
      (r) =>
        r.title
          .toLowerCase()
          .normalize('NFD')
          .replace(/\p{Diacritic}/gu, '')
          .includes(nameQuery.toLowerCase()) ||
        r.title.toLowerCase().includes(nameQuery.toLowerCase())
    );
  }

  openBottomSheet(): void {
    this._bottomSheet
      .open(BottomSheetComponent, {
        data: [
          {
            name: 'Add a recipe',
            description: 'Add your favorite new recipe',
            url: 'recipe/add',
          },
        ],
      })
      .afterDismissed()
      .subscribe((data: any) => {
        if (data !== undefined) {
          switch (data) {
            case 'recipe/add':
              this.openDialogAdd();
              break;
            default:
              console.log('nothing');
          }
        }
      });
  }

  openDialogAdd(): void {
    const dialogRef = this.recipeModal.open(AddRecipeModalComponent);

    dialogRef.afterClosed().subscribe((result) => {
      console.log('The dialog was closed');
      if (result !== undefined) {
        console.log(result);
      }
    });
  }
  openRecipe(result: Recipe): void {
    const dialogRef = this.dialogAdd.open(DisplayRecipeModalComponent, {
      data: { recipe: result },
    });
    dialogRef.afterClosed().subscribe((mode) => {
      if (!!mode && mode) {
        // add a review
        this.openReview(result);
      }
    });
  }

  openReview(recipe: Recipe) {
    const dialogRef = this.dialogAddReview.open(AddEditReviewDialogComponent, {
      data: { recipe: recipe },
    });
    dialogRef.afterClosed().subscribe((result: any) => {
      console.log('The dialog was closed');
      if (result !== undefined) {
        this.reviewHttpService
          .postReview(result)
          .subscribe((result: unknown) => {
            console.log(result);
          });
      }
    });
  }

  createdReviewEvent(): void {
    this.webSocket
      .createdReviewEvent()
      .pipe()
      .subscribe((event) => {
        this.openSnackBar(event.message, 'Nice!');
      });
  }

  openSnackBar(message: string, action: string) {
    this._snackBar.open(message, action);
  }
}
