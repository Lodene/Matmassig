import { Component, inject, OnInit } from '@angular/core';
import { MatBottomSheet } from '@angular/material/bottom-sheet';
import { MatIconModule } from '@angular/material/icon';
import { BottomSheetComponent } from '../bottom-sheet/bottom-sheet.component';
import { AuthService } from 'src/app/services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-account-button',
  templateUrl: './account-button.component.html',
  styleUrls: ['./account-button.component.scss'],
  imports: [MatIconModule]
})
export class AccountButtonComponent  implements OnInit {

  private _bottomSheet = inject(MatBottomSheet);
  private auth = inject(AuthService);

  constructor(private router: Router) { }

  ngOnInit() {}

  openBottomSheet(): void {
      this._bottomSheet.open(BottomSheetComponent, {
        data: [
          {
            name: 'Edit profiles',
            url: 'user-preferences'
          },
          {
          name: 'Disconnect',
          url: 'disconnect'
        }]
      }).afterDismissed().subscribe((data: any ) => {
        if (data !== undefined) {
          switch (data) {
            case "user-preferences":

              break;
            case "disconnect":
              this.auth.logout();
              this.router.navigate(['/home']);
              break;
            default:
              break;
          }
        }
      });
    }

}
