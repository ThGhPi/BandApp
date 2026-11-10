import { Component, input } from '@angular/core';
import { ReactiveFormsModule, FormControl } from '@angular/forms';

@Component({
  selector: 'app-date-input',
  imports: [ReactiveFormsModule],
  templateUrl: './date-input.html',
  styleUrl: './date-input.css',
})
export class DateInput {
  
  name = input.required<string>();
  label = input.required<string>();
  placeholder = input<string>();

  type = input<'date'>('date');

  control = input.required<FormControl<string>>();

  get errorMessage(): string | null {
    const control = this.control;

    if (!control || !control.touched || !control.errors) {
      return null;
    }

    if (control.hasError('required')) {
      return `${this.label} est requis.`;
    }

    if (control.hasError('pattern')) {
      return 'Veuillez entrer' + this.placeholder + ' valide.';
    }

    return 'Entrée non valide.';
  }
}
