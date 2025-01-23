import { Component, inject, OnInit } from '@angular/core';
import { MatBottomSheet } from '@angular/material/bottom-sheet';
import { Ingredient } from 'src/models/ingredient';
import { BottomSheetComponent } from '../generic-component/bottom-sheet/bottom-sheet.component';
import { MatDialog } from '@angular/material/dialog';
import { AddIngredientModalComponent } from './add-ingredient-modal/add-ingredient-modal.component';
import { InventoryHttpService } from '../services/inventory-http.service';

@Component({
  selector: 'app-fridge',
  templateUrl: 'fridge.page.html',
  styleUrls: ['fridge.page.scss'],
  standalone: false,
})
export class FridgePage implements OnInit {


  results: Ingredient[] = [];
  ingredients : Ingredient[] = []

  private _bottomSheet = inject(MatBottomSheet);
  private readonly dialogAdd = inject(MatDialog);

  constructor(
    private inventoryHttpService: InventoryHttpService
  ) {}
  ngOnInit(): void {
    this.mockUpFridge();
    this.results = [...this.ingredients];
    console.log(this.results)
  }

  mockUpFridge(): void {
    this.ingredients = [
      new Ingredient(0, "carrot", 2, "https://minecraft.wiki/images/Carrot_JE3_BE2.png?5e9c8"),
      new Ingredient(1, "potato", 5, "https://minecraft.wiki/images/Potato_JE3_BE2.png?27685")
    ];
    
  }


  openBottomSheet(): void {
      this._bottomSheet
        .open(BottomSheetComponent, {
          data: [
            {
              name: 'Add an ingredient',
              url: 'ingredient/add',
            },
          ],
        })
        .afterDismissed()
        .subscribe((data: any) => {
          if (data !== undefined) {
            switch (data) {
              case 'ingredient/add':
                this.openDialogAdd();
                break;
              default:
                console.log('nothing');
            }
          }
        });
    }
  
    openDialogAdd(): void {
      const dialogRef = this.dialogAdd.open(AddIngredientModalComponent);
  
      dialogRef.afterClosed().subscribe((result) => {
        console.log(result);
        // console.log('The dialog was closed');
        if (result !== undefined) {
          for (let i = 0; i < result.ingredients.length; i++) {
            this.inventoryHttpService.postUserItem(result.ingredients[i]).subscribe(msg => {
              console.log(msg);
            });
          }
        }
      });
    }

}
