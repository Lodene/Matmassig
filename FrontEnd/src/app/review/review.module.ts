import { IonicModule } from '@ionic/angular';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ReviewPage } from './review.page';
import { ExploreContainerComponentModule } from '../explore-container/explore-container.module';

import { ReviewPageRoutingModule } from './review-routing.module';
import { MatDialogModule } from '@angular/material/dialog';
import { AddEditReviewDialogComponent } from './dialog/add-edit-review-dialog/add-edit-review-dialog.component';
import { StarReviewComponent } from '../generic-component/star-review/star-review.component';
import { MatIcon, MatIconModule } from '@angular/material/icon';
import { MatCheckboxModule } from '@angular/material/checkbox';

@NgModule({
  imports: [
    IonicModule,
    CommonModule,
    FormsModule,
    ExploreContainerComponentModule,
    ReviewPageRoutingModule,
    StarReviewComponent,
    MatIcon,
    MatIconModule,
    MatCheckboxModule
  ],
  declarations: [ReviewPage]
})
export class ReviewPageModule {}
