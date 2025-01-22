import { Component, inject, OnInit } from '@angular/core';
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService, UserData } from '../services/auth.service';

@Component({
  selector: 'app-new-account',
  templateUrl: './new-account.page.html',
  styleUrls: ['./new-account.page.scss'],
  standalone: false,
})
export class NewAccountPage {

  constructor(private router: Router) { }

  private formBuilder = inject(FormBuilder);
  private auth = inject(AuthService);
    
  userForm = this.formBuilder.group({
    name: ['', Validators.required],
    email: ['', [Validators.required, Validators.email]],    
    password: ['', Validators.required],
    password2: ['', Validators.required],
  });

  public async signUp() {
    if (this.userForm.valid && this.password.value === this.password2.value) {
      let data: UserData = {
        name: this.email.value,
        email: this.email.value,
        password: this.password.value,
      };
      
      this.auth.signUp(data).subscribe(result => {
        if (!!result) {
          this.router.navigate(['tabs/recipe']);
        }
      });
    } else {
      console.log("erreur");
    }
  }

  get email() {
    return this.userForm.get("email") as FormControl;
  }

  get password() {
    return this.userForm.get("password") as FormControl;
  }

  get password2() {
    return this.userForm.get("password2") as FormControl;
  }

}