import { Component, inject } from '@angular/core';
import { TextInput } from '../../../../../shared/components/forms/input/text-input';
import { DateInput } from '../../../../../shared/components/forms/date-input/date-input';
import { FormBuilder, Validators, ReactiveFormsModule, AbstractControl, ValidationErrors, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';


@Component({
  selector: 'app-signup.page',
  imports: [ReactiveFormsModule,TextInput, DateInput],
  templateUrl: './signup.page.html',
})
export class SignupPage {
  usernameLabel: string = "Nom d'utilisateur (ou pseudonyme)";

  private readonly router = inject(Router);
  private formBuilder = inject(FormBuilder);

  signupForm : FormGroup = this.formBuilder.nonNullable.group({
    firstname: ['', [Validators.required, Validators.pattern('^[a-zA-Z]+$')]],
    lastname: ['', [Validators.required, Validators.pattern('^[a-zA-Z]+$')]],
    username: ['', [Validators.required, Validators.pattern('^[a-zA-Z0-9]+$')]],
    email: ['', [Validators.required, Validators.email]],
    trialPassword: ['', [
      Validators.required,
      Validators.pattern('^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$')
    ]],
    confirmPassword: ['', [
      Validators.required,
      Validators.pattern('^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$')
    ]],
    phonenumber: ['', [Validators.required, Validators.pattern('^\\+?[1-9]\\d{7,14}$')]],
    birthdate: ['', [Validators.required]]
  }, {
    validators: this.passwordMatchValidator
  });

  passwordMatchValidator(
    control: AbstractControl
  ): ValidationErrors | null {

    const password = control.get('trialPassword')?.value;
    const confirmation = control.get('confirmPassword')?.value;

    if (!password || !confirmation) {
      return null;
    }

    return password === confirmation
      ? null
      : { passwordMismatch: true };
  }

  onSubmit(): void {

    if (this.signupForm.invalid) {
      this.signupForm.markAllAsTouched();
      return;
    }

    const {
      firstname,
      lastname,
      username,
      email,
      trialPassword,
      phonenumber,
      birthdate
    } = this.signupForm.getRawValue();

    const request = {
      firstname,
      lastname,
      username,
      email,
      trialPassword,
      phonenumber,
      birthdate
    };

    console.log(request); //connect sevice to send request to backend
  }

  goToLogin(): void {
    this.router.navigate(['/auth/login']);
  }
}
