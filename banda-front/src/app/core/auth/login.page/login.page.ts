import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login.page',
  imports: [],
  templateUrl: './login.page.html',
  styleUrl: './login.page.css',
})
export class LoginPage {
  
  username = '';
  password = '';

  constructor(private router: Router) {}

  login() {
    // TEMPORAIRE
    this.router.navigate(['/sondages']);
  }
}
