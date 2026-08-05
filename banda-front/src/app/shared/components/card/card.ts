import { Component, computed, input } from '@angular/core';

@Component({
  selector: 'app-card',
  standalone: true,
  imports: [],
  templateUrl: './card.html',
})
export class Card {
  title = input.required<string>();
  subtitle = input<string>();
  headerBg = input<string>('festive');
  headerText = input<string>('white');

  headerClasses = computed(() => 
  `bg-${this.headerBg()} p-2 flex
  justify-${this.subtitle() ? 'between' : 'center'}
  items-center text-xl
  text-${this.headerText()}`
);
}
