import { Component, inject, signal } from '@angular/core';
import { Survey } from '../../models/survey.model';
import { SurveyService } from '../../services/survey.service';
import { Card } from "../../../../shared/components/card/card";
import { SmartDatePipe } from '../../../../shared/pipes/smart-date.pipe';
import { Option } from '../../components/option/option';
import { Vote } from '../../models/vote.model';

@Component({
  selector: 'app-survey-list.page',
  imports: [Card, SmartDatePipe, Option],
  templateUrl: './survey-list.page.html',
  styleUrl: './survey-list.page.css',
})
export class SurveyListPage {
  private surveyService = inject(SurveyService);

  surveys = signal<Survey[]>([]);

  ngOnInit() {
    this.loadSurveys();
  }

  loadSurveys() {
    this.surveyService.getRecent().subscribe(data => {
      this.surveys.set(data);
    });
  }

  handleVote(event: {
    surveyId: number;
    optionId: number;
    selected: boolean;
  }) {
    const vote: Vote = {
      surveyId: event.surveyId,
      choiceId: event.optionId
    };
    const request$ = event.selected
      ? this.surveyService.addVote(vote)
      : this.surveyService.removeVote(vote);

    request$.subscribe(updatedSurvey => {
      this.updateSurvey(updatedSurvey);
    });
  }

  private updateSurvey(updatedSurvey: Survey) {
    this.surveys.update(surveys =>
      surveys.map(s =>
        s.id === updatedSurvey.id ? updatedSurvey : s
      )
    );
  }
}
