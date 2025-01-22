import { CommonModule } from '@angular/common';
import { Component, inject, OnInit } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatDialogTitle, MatDialogModule, MatDialogContent, MatDialogActions, MatDialogClose, MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';

@Component({
  selector: 'app-ok-modal',
  templateUrl: './ok-modal.component.html',
  styleUrls: ['./ok-modal.component.scss'],
    imports: [CommonModule,
      MatFormFieldModule,
      MatInputModule,
      FormsModule,
      MatButtonModule,
      ReactiveFormsModule,
      MatDialogModule,
      MatDialogContent,
      MatDialogActions,
      MatDialogClose,]
})
export class OkModalComponent  implements OnInit {



  constructor() { }

  message: string = 'Modal-message';

  readonly dialogRef = inject(MatDialogRef<OkModalComponent>);
  readonly data = inject<any>(MAT_DIALOG_DATA);

  ngOnInit() {
    this.message = this.data.message;
  }

}
