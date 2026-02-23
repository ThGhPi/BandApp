import { Component, Input } from '@angular/core';
import { Choice } from '../../models/choice.model';
import { Survey } from '../../models/survey.model';

@Component({
  selector: 'app-option',
  imports: [],
  templateUrl: './option.html',
  styleUrl: './option.css',
})
export class Option {
  @Input() survey!: Survey;
  @Input() option!: Choice;
}
