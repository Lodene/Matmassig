import { Component, inject, OnInit } from '@angular/core';
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { OkModalComponent } from '../generic-component/ok-modal/ok-modal.component';
import { Router } from '@angular/router';

@Component({
  selector: 'app-new-password',
  templateUrl: './new-password.page.html',
  styleUrls: ['./new-password.page.scss'],
  standalone: false
})
export class NewPasswordPage {

  constructor(private router: Router) { }

  private formBuilder = inject(FormBuilder);
  private readonly dialogOk = inject(MatDialog);
    
  userForm = this.formBuilder.group({    
    email: ['', [Validators.required, Validators.email]],    
  });

  public async sendEmail() {
    if (this.userForm.valid) {
      let data = {
        email: this.email.value,
      };
      this.openDialogOk();
    } else {
      console.log("erreur");
    }
  }

  get email() {
    return this.userForm.get("email") as FormControl;
  }

  openDialogOk(): void {
      const dialogRef = this.dialogOk.open(OkModalComponent, {
        data: {
          message: "An email has been sent to the requested address."
        }
      });
  
      dialogRef.afterClosed().subscribe(result => {
        this.router.navigate(["home"]);
      });
    }
}
