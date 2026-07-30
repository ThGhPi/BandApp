import { Component, input, output } from '@angular/core';
import { Choice } from '../../models/choice.model';
import { Survey } from '../../models/survey.model';

@Component({
  selector: 'app-option',
  imports: [],
  templateUrl: './option.html',
  styleUrl: './option.css',
})
export class Option {
  
  survey = input.required<Survey>();
  choice = input.required<Choice>();
  last = input<boolean>(false);

  voteChange = output<{
    surveyId: number;
    optionId: number;
    selected: boolean;
  }>();

  toggle(): void {

    const newValue = !this.choice().chosen;

    this.voteChange.emit({
      surveyId: this.survey().id,
      optionId: this.choice().id,
      selected: newValue
    });
  }

}
