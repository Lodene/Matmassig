import { Component } from '@angular/core';
import { GoogleAuth } from '@codetrix-studio/capacitor-google-auth';
import { Platform } from '@ionic/angular';
import { environment } from 'src/environments/environment';
import { WebSocketService } from './websocket/web-socket.service';

@Component({
  selector: 'app-root',
  templateUrl: 'app.component.html',
  styleUrls: ['app.component.scss'],
  standalone: false,
})
export class AppComponent {
  constructor(
    private platform: Platform,
    private webSocketService: WebSocketService
  ) {
    this.initializeApp();
  }

  initializeApp() {
    this.platform.ready().then(() => {
      console.log('READY!')
      GoogleAuth.initialize(
        {
          clientId:environment.clientID,
          scopes: ['profile', 'email'],
          grantOfflineAccess: true,
        }
      )
      // connect to websocket =>
       this.webSocketService.connectSocket("hello world");

    })
  }
}
