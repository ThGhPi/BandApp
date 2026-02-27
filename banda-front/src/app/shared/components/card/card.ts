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

  headerClasses = computed(() => 
  `bg-${this.headerBg()} px-4 py-3 flex justify-between items-center text-white font-semibold`
);
}
