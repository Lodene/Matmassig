import {Component, Inject, inject, Input, input, OnInit} from '@angular/core';
import {MAT_BOTTOM_SHEET_DATA, MatBottomSheetRef} from '@angular/material/bottom-sheet';
import {MatListModule} from '@angular/material/list';
import { BottomSheetInterface } from './link-interface';

@Component({
  selector: 'app-bottom-sheet',
  templateUrl: './bottom-sheet.component.html',
  styleUrls: ['./bottom-sheet.component.scss'],
  imports: [MatListModule],
})

export class BottomSheetComponent  implements OnInit {

  constructor(@Inject(MAT_BOTTOM_SHEET_DATA) public data: BottomSheetInterface[]) { }


  private _bottomSheetRef =
  inject<MatBottomSheetRef<BottomSheetComponent>>(MatBottomSheetRef);



  ngOnInit() {}

  openLink(event: MouseEvent, link: string): void {

    this._bottomSheetRef.dismiss(link);
    event.preventDefault();
  }
  cancel(event: MouseEvent) {
    this._bottomSheetRef.dismiss();
    event?.preventDefault();
  }

}