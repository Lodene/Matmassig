import { IonicModule } from '@ionic/angular';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { FridgePage } from './fridge.page';
import { ExploreContainerComponentModule } from '../explore-container/explore-container.module';

import { FridgePageRoutingModule } from './fridge-routing.module';

@NgModule({
  imports: [
    IonicModule,
    CommonModule,
    FormsModule,
    ExploreContainerComponentModule,
    FridgePageRoutingModule
  ],
  declarations: [FridgePage]
})
export class FridgePageModule {}
