import { CommonModule } from '@angular/common';
import { Component, inject, OnInit } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MAT_DIALOG_DATA, MatDialogActions, MatDialogClose, MatDialogContent, MatDialogModule, MatDialogRef, MatDialogTitle } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatListModule } from '@angular/material/list';
import { MatSelectModule } from '@angular/material/select';
import { Review } from 'src/models/review';

@Component({
  selector: 'app-yes-no-dialog',
  templateUrl: './yes-no-dialog.component.html',
  styleUrls: ['./yes-no-dialog.component.scss'],
  imports: [CommonModule,
    FormsModule,
    MatDialogModule,
    MatSelectModule,
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    ReactiveFormsModule,
    MatDialogTitle,
    MatDialogContent,
    MatDialogActions,
    MatDialogClose,
    MatListModule
  ]
})
export class YesNoDialogComponent  implements OnInit {


  title: string = 'Modal-title';
  question: string = 'Modal-question';
  color: string = 'primary'
  deleteList: Review[] = [];

  readonly dialogRef = inject(MatDialogRef<YesNoDialogComponent>);
  readonly data = inject<any>(MAT_DIALOG_DATA);

  constructor() { }

  ngOnInit() {
    this.title = this.data.title;
    this.question = this.data.question;
    this.color = this.data.color;
    this.deleteList = this.data.deleteList;
  }

  validate():void {
    this.dialogRef.close(true);
  }

}
