import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { NewAccountPageRoutingModule } from './new-account-routing.module';

import { NewAccountPage } from './new-account.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    IonicModule,
    NewAccountPageRoutingModule
  ],
  declarations: [NewAccountPage]
})
export class NewAccountPageModule {}
