import { Component, inject, signal } from '@angular/core';
import { Survey } from '../../models/survey.model';
import { SurveyService } from '../../services/survey.service';
import { Card } from "../../../../shared/components/card/card";

@Component({
  selector: 'app-survey-list.page',
  imports: [Card],
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
    this.surveyService.getAll().subscribe(data => {
      this.surveys.set(data);
    });
  }
}
