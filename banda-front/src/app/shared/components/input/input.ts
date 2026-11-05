import { Component, input, output } from '@angular/core';

@Component({
  selector: 'app-input',
  imports: [],
  templateUrl: './input.html',
  styleUrl: './input.css',
})
export class Input {
  name = input<string>();
  label = input<string>();
  formName = input<string>();
  type = input<string>('text');
  pattern = input<string>();
  placeholder = input<string>();

}
