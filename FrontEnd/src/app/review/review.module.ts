import { IonicModule } from '@ionic/angular';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ReviewPage } from './review.page';
import { ExploreContainerComponentModule } from '../explore-container/explore-container.module';

import { ReviewPageRoutingModule } from './review-routing.module';
import { MatDialogModule } from '@angular/material/dialog';
import { AddEditReviewDialogComponent } from './dialog/add-edit-review-dialog/add-edit-review-dialog.component';
import { StarReviewComponent } from '../generic-component/star-review/star-review.component';
import { MatIcon, MatIconModule } from '@angular/material/icon';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { NgxMatSelectSearchModule } from 'ngx-mat-select-search';
import { MatSelectModule } from '@angular/material/select';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';

@NgModule({
  imports: [
    IonicModule,
    CommonModule,
    FormsModule,
    ExploreContainerComponentModule,
    ReviewPageRoutingModule,
    StarReviewComponent,
    MatIconModule,
    MatCheckboxModule,
    ReactiveFormsModule,
    NgxMatSelectSearchModule,
    MatDialogModule,
    MatSelectModule,
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule
  ],
  declarations: [ReviewPage, AddEditReviewDialogComponent]
})
export class ReviewPageModule {}
