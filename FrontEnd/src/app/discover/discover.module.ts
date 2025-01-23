import { IonicModule } from '@ionic/angular';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { DiscoverPage } from './discover.page';
import { ExploreContainerComponentModule } from '../explore-container/explore-container.module';

import { DiscoverPageRoutingModule } from './discover-routing.module';
import { AccountButtonComponent } from '../generic-component/account-button/account-button.component';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';

@NgModule({
  imports: [
    IonicModule,
    CommonModule,
    FormsModule,
    ExploreContainerComponentModule,
    DiscoverPageRoutingModule,
    AccountButtonComponent,
    MatProgressSpinnerModule
  ],
  declarations: [DiscoverPage]
})
export class DiscoverPageModule {}
