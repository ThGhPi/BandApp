import { NgClass } from '@angular/common';
import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-card',
  standalone: true,
  imports: [NgClass],
  templateUrl: './card.html',
})
export class Card {
  @Input() title!: string;
  @Input() subtitle?: string;
  @Input() headerBg: string = "festive";
}
