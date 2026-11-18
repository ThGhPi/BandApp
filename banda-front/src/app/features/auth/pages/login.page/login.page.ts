import { Component, inject } from '@angular/core';
import { LoginForm } from "../../components/login-form/login-form";
import { Router } from '@angular/router';

@Component({
  selector: 'app-login.page',
  imports: [LoginForm],
  templateUrl: './login.page.html',
  styleUrl: './login.page.css',
})
export class LoginPage {
  private readonly router = inject(Router);
  navigateToSignup(): void {
    this.router.navigate(['/inscription']);
  }
}
