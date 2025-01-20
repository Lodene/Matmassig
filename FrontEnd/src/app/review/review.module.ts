import { IonicModule } from '@ionic/angular';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ReviewPage } from './review.page';
import { ExploreContainerComponentModule } from '../explore-container/explore-container.module';

import { ReviewPageRoutingModule } from './review-routing.module';
import { MatDialogModule } from '@angular/material/dialog';
import { AddEditReviewDialogComponent } from './dialog/add-edit-review-dialog/add-edit-review-dialog.component';

@NgModule({
  imports: [
    IonicModule,
    CommonModule,
    FormsModule,
    ExploreContainerComponentModule,
    ReviewPageRoutingModule 
  ],
  declarations: [ReviewPage]
})
export class ReviewPageModule {}
