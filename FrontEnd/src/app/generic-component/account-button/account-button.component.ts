import { Component, inject, OnInit } from '@angular/core';
import { MatBottomSheet } from '@angular/material/bottom-sheet';
import { MatIconModule } from '@angular/material/icon';
import { BottomSheetComponent } from '../bottom-sheet/bottom-sheet.component';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-account-button',
  templateUrl: './account-button.component.html',
  styleUrls: ['./account-button.component.scss'],
  imports: [MatIconModule]
})
export class AccountButtonComponent  implements OnInit {

  private _bottomSheet = inject(MatBottomSheet);
  private auth = inject(AuthService);

  constructor() { }

  ngOnInit() {}

  openBottomSheet(): void {
      this._bottomSheet.open(BottomSheetComponent, {
        data: [{
          name: 'Disconnect',
          description: 'Disconnect the user',
          url: 'disconnect'
        }]
      }).afterDismissed().subscribe((data: any ) => {
        if (data !== undefined) {
          switch (data) {
            case "disconnect":
              this.auth.logout();
              break;
            default:
              console.log("nothing");
          }
        }
      });
    }

}
