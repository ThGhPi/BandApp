import { Component, input, output, signal } from '@angular/core';
import { Choice } from '../../models/choice.model';
import { Survey } from '../../models/survey.model';

@Component({
  selector: 'app-choice-line',
  imports: [],
  templateUrl: './choice-line.html',
  styleUrl: './choice-line.css',
})
export class ChoiceLine {
  
  survey = input.required<Survey>();
  choice = input.required<Choice>();
  last = input<boolean>(false);
  chosen = signal<boolean>(false);

  voteChange = output<{
    surveyId: number;
    optionId: number;
    selected: boolean;
  }>();

  ngOnInit(): void {
    this.chosen.set(this.choice().chosen);
  }

  toggle(): void {

    if (this.survey().multiplicity) {
      const newValue = !this.choice().chosen;
  
      this.voteChange.emit({
        surveyId: this.survey().id,
        optionId: this.choice().id,
        selected: newValue
      });
    }
  }

}
