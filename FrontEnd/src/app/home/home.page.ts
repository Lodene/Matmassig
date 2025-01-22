import { Component, inject, signal } from '@angular/core';
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { GoogleAuth } from '@codetrix-studio/capacitor-google-auth';
import { AuthService } from '../services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home',
  templateUrl: './home.page.html',
  styleUrls: ['./home.page.scss'],
  standalone: false,
})
export class HomePage {

  constructor(private router: Router) {}
  
    private formBuilder = inject(FormBuilder);
    private auth = inject(AuthService);

    errorMessage = signal('');
  
    userForm = this.formBuilder.group({    
      email: ['', [Validators.required, Validators.email]],    
      password: ['', Validators.required], 
    });

  public async signInGoogle(){
    let googleUser = await GoogleAuth.signIn();
    console.log(googleUser.email);
  }

  public signOutGoogle(){
    GoogleAuth.signOut();
  }

  public async signInEmail(){
    if (this.userForm.valid) {
      
        let data: UserData = {
          email: this.email.value,
          password: this.password.value
        };
        
        this.auth.login();
        if (this.auth.isAuthenticated()) {
          this.router.navigate(["/tabs/review"]);
        }
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

  updateErrorMessage() {
    if (this.email.hasError('required')) {
      this.errorMessage.set('You must enter a value');
    } else if (this.email.hasError('email')) {
      this.errorMessage.set('Not a valid email');
    } else {
      this.errorMessage.set('');
    }
  }

}

export interface UserData {
  email: string;
  password: string;
}