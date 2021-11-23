import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService, User } from './services/authentication-service.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'frontend';
}
