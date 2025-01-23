import { Component, inject, signal } from '@angular/core';
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { GoogleAuth } from '@codetrix-studio/capacitor-google-auth';
import { AuthService, UserData } from '../services/auth.service';
import { Router } from '@angular/router';
import { WebSocketService } from '../websocket/web-socket.service';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-home',
  templateUrl: './home.page.html',
  styleUrls: ['./home.page.scss'],
  standalone: false,
})
export class HomePage {

  constructor(private router: Router,
    private webSocket: WebSocketService,
  ) {}
  
    private formBuilder = inject(FormBuilder);
    private auth = inject(AuthService);
    login$: Observable<any> = new Observable<any>();
    errorMessage = signal('');
  
    userForm = this.formBuilder.group({    
      email: ['', [Validators.required, Validators.email]],    
      password: ['', Validators.required], 
    });

  public async signInGoogle(){
    let googleUser = await GoogleAuth.signIn();
    if (googleUser.email !== null) {
      this.auth.login({
        email: googleUser.email,
        password: googleUser.name + googleUser.email
      }).subscribe((result: any) => {
        if (!!result) {
          this.router.navigate(["/tabs/review"]);
          // this.login$ = this.webSocket.getToken().pipe();
        } else {
          this.auth.signUp({
            name: googleUser.name,
            email: googleUser.email,
            password: googleUser.name + googleUser.email
          }).subscribe(result => {
            if (!!result) {
              this.auth.login({
                email: googleUser.email,
                password: googleUser.name + googleUser.email
              }).subscribe(result => {
                if (!!result) {
                  this.router.navigate(["/tabs/review"]);
                  // this.login$ = this.webSocket.getToken().pipe();
                }
              });
            }
          });
        }
      });
    }
    console.log(googleUser.email);
  }

  public signOutGoogle(){
    GoogleAuth.signOut();
    this.auth.logout();
  }

  public async signInEmail(){
    if (this.userForm.valid) {
      
        let data: UserData = {
          email: this.email.value,
          password: this.password.value
        };
        
        this.auth.login(data).subscribe(result => {
          if (!!result) {
            this.router.navigate(["/tabs/review"]);
            // this.login$ = this.webSocket.getToken().pipe();
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

  onTokenResponse(): void {
    
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