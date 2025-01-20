import { IonicModule } from '@ionic/angular';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RecipePage } from './recipe.page';
import { ExploreContainerComponentModule } from '../explore-container/explore-container.module';

import { RecipePageRoutingModule } from './recipe-routing.module';
import { MatIconModule } from '@angular/material/icon';
import { MatMenuModule } from '@angular/material/menu';
import { MatButtonModule } from '@angular/material/button';

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
  ],
  declarations: [RecipePage]
})
export class RecipePageModule {}
