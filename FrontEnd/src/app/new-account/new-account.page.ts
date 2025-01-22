import { Component, inject, OnInit } from '@angular/core';
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-new-account',
  templateUrl: './new-account.page.html',
  styleUrls: ['./new-account.page.scss'],
  standalone: false,
})
export class NewAccountPage {

  constructor(private router: Router) { }

  private formBuilder = inject(FormBuilder);
    
  userForm = this.formBuilder.group({    
    email: ['', [Validators.required, Validators.email]],    
    password: ['', Validators.required],
    password2: ['', Validators.required],
  });

  public async signUp() {
    if (this.userForm.valid && this.password.value === this.password2.value) {
      let data: UserData = {
        email: this.email.value,
        password: this.password.value,
        password2: this.password2.value
      };

      this.router.navigate(['tabs/recipe'])
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


export interface UserData {
  email: string;
  password: string;
  password2: string;
}