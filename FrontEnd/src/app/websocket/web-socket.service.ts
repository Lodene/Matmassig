import { Injectable } from '@angular/core';
import { Socket } from 'ngx-socket-io';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class WebSocketService {
  constructor(private webSocket: Socket) {
    this.webSocket = new Socket({
      url: 'http://localhost:3000/',
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


}
