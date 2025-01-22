import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { RouteReuseStrategy } from '@angular/router';

import { IonicModule, IonicRouteStrategy } from '@ionic/angular';
import { SocketIoConfig, SocketIoModule } from 'ngx-socket-io';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import { provideHttpClient, withFetch } from '@angular/common/http';
import { AuthService } from './services/auth.service';
import { ReviewPageModule } from './review/review.module';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { MatFormFieldModule } from '@angular/material/form-field';


const config: SocketIoConfig = { url: 'http://localhost:3000', options: {} };

@NgModule({
  declarations: [AppComponent],
  imports: [BrowserModule, IonicModule.forRoot(), AppRoutingModule,
    ReviewPageModule,
    MatIconModule,
    MatListModule,
    SocketIoModule.forRoot(config),
    MatFormFieldModule],
  providers: [
    provideHttpClient(
      withFetch(),
    ),
    { provide: RouteReuseStrategy, useClass: IonicRouteStrategy },
    provideAnimationsAsync(),
    AuthService],
  bootstrap: [AppComponent],
})
export class AppModule {}
