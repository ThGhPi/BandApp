import { Component, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { AuthService } from '../../../../../core/auth/services/auth.service/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login-form',
  imports: [ReactiveFormsModule],
  templateUrl: './login-form.html',
  styleUrl: './login-form.css',
})
export class LoginForm {
  
  private readonly formBuilder = inject(FormBuilder);
  private readonly authService = inject(AuthService);
  private readonly router = inject(Router);

  loginFormGroup = this.formBuilder.nonNullable.group({
    username: ['', Validators.required],
    trialPassword: ['', Validators.required]
  });

  onSubmit(): void {

    if (this.loginFormGroup.invalid) {
      return;
    }

    this.authService.login(
      this.loginFormGroup.getRawValue()
    ).subscribe({
      next: () => {
        this.router.navigate(['/sondages']); // à changer lors de l'ajout de la page d'accueil : this.router.navigate(['/accueil']);
      },
      error: error => {
        console.error('Authentication failed', error);
      }
    });
  }
}
