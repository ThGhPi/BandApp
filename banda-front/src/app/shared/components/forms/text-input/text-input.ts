import { Component, input } from '@angular/core';
import { ReactiveFormsModule, FormControl } from '@angular/forms';

@Component({
  selector: 'app-text-input',
  imports: [ReactiveFormsModule],
  templateUrl: './text-input.html',
  styleUrl: './text-input.css',
})
export class TextInput {

  name = input.required<string>();
  label = input.required<string>();
  placeholder = input<string>();

  type = input<'text'|'email'|'password'>('text');

  control = input.required<FormControl<string>>();

  get errorMessage(): string | null {
    const control = this.control;

    if (!control() || !control().touched || !control().errors) {
      return null;
    }

    if (control().hasError('required')) {
      return `${this.label} est requis.`;
    }

    if (control().hasError('pattern') || control().hasError('email')) {
      return 'Veuillez entrer' + this.placeholder + ' valide.';
    }

    return 'Entrée non valide.';
  }
}
