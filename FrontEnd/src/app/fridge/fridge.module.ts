import { IonicModule } from '@ionic/angular';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { FridgePage } from './fridge.page';
import { ExploreContainerComponentModule } from '../explore-container/explore-container.module';

import { FridgePageRoutingModule } from './fridge-routing.module';
import { AccountButtonComponent } from '../generic-component/account-button/account-button.component';

@NgModule({
  imports: [
    IonicModule,
    CommonModule,
    FormsModule,
    ExploreContainerComponentModule,
    FridgePageRoutingModule,
    AccountButtonComponent
  ],
  declarations: [FridgePage]
})
export class FridgePageModule {}
