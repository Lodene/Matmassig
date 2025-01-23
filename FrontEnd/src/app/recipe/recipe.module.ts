import { IonicModule } from '@ionic/angular';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormGroup, FormsModule } from '@angular/forms';
import { RecipePage } from './recipe.page';
import { ExploreContainerComponentModule } from '../explore-container/explore-container.module';

import { RecipePageRoutingModule } from './recipe-routing.module';
import { MatIconModule } from '@angular/material/icon';
import { MatMenuModule } from '@angular/material/menu';
import { MatButtonModule } from '@angular/material/button';
import { AddRecipeModalComponent } from './modal/add-recipe-modal/add-recipe-modal.component';
import { DisplayRecipeModalComponent } from './modal/display-recipe-modal/display-recipe-modal.component';
import { MatDialogModule } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatCardModule } from '@angular/material/card';

@NgModule({
  imports: [
    IonicModule,
    CommonModule,
    FormsModule,
    ExploreContainerComponentModule,
    RecipePageRoutingModule,
    MatIconModule,
    MatMenuModule,
    MatButtonModule,
    AddRecipeModalComponent,
    MatDialogModule,
    MatFormFieldModule,
    MatCardModule,
  ],
  declarations: [RecipePage, DisplayRecipeModalComponent],
})
export class RecipePageModule {}
