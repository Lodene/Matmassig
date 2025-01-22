import { Injectable } from '@angular/core';
import { Socket } from 'ngx-socket-io';
import { Observable } from 'rxjs';
import { AuthService } from '../services/auth.service';

@Injectable({
  providedIn: 'root'
})
export class WebSocketService {
  constructor(private webSocket: Socket,
    private authService: AuthService
  ) {
    this.webSocket = new Socket({
      url: 'http://localhost:8089/',
      options: {}
    });


  }
   // this method is used to start connection/handhshake of socket with server
  connectSocket(message: string) {
      this.webSocket.emit('connect', message);
  }
  // this method is used to get response from server
  receiveStatus() {
    return this.webSocket.fromEvent('/get-response');
  }

  // this method is used to end web socket connection
  disconnectSocket() {
    this.webSocket.disconnect();
  }

  getReview(): Observable<any> {
    return this.webSocket.fromEvent('review-created')
  }

  getToken(): Observable<any> {
    return this.webSocket.fromEvent('login-successfully');
  }


}
