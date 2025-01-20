import { Component } from '@angular/core';
import { GoogleAuth } from '@codetrix-studio/capacitor-google-auth';

@Component({
  selector: 'app-home',
  templateUrl: './home.page.html',
  styleUrls: ['./home.page.scss'],
  standalone: false,
})
export class HomePage {

  constructor() {}

  public async signIn(){
    let googleUser = await GoogleAuth.signIn();
    console.log(googleUser.email);
  }

  public signOut(){
    GoogleAuth.signOut();
  }

}
