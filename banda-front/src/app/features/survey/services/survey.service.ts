import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Survey } from '../models/survey.model';
import { API_ENDPOINTS } from '../../../core/config/api-endpoints';

@Injectable({
  providedIn: 'root',
})
export class SurveyService {
  private baseUrl = API_ENDPOINTS.surveys;

  constructor(private http: HttpClient) { }

  /**
   * Get all surveys
   */
  getAll(): Observable<Survey[]> {
    return this.http.get<Survey[]>(this.baseUrl);
  }

  /**
   * Get survey by id
   */
  getById(id: number): Observable<Survey> {
    return this.http.get<Survey>(`${this.baseUrl}/${id}`);
  }

  /**
   * Vote an option (choice)
   */
  addVote(surveyId: number, choiceId: number): Observable<Survey> {
    return this.http.post<Survey>(
      `${this.baseUrl}/${surveyId}/vote`,
      { choiceId }
    );
  }

  /**
   * Unvote an option 
   */
  removeVote(surveyId: number, choiceId: number): Observable<Survey> {
    return this.http.delete<Survey>(
      `${this.baseUrl}/${surveyId}/vote/${choiceId}`
    );
  }


  /**
   * Create survey (admin)
   */
  create(survey: Partial<Survey>): Observable<Survey> {
    return this.http.post<Survey>(this.baseUrl, survey);
  }
}
