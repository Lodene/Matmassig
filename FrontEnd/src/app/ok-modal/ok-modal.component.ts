import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatDialogTitle, MatDialogModule, MatDialogContent, MatDialogActions, MatDialogClose } from '@angular/material/dialog';
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
      MatDialogTitle,
      MatDialogModule,
      MatDialogContent,
      MatDialogActions,
      MatDialogClose,]
})
export class OkModalComponent  implements OnInit {

  constructor() { }

  ngOnInit() {}

}
