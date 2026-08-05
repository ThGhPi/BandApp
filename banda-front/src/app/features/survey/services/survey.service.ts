import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Survey } from '../models/survey.model';
import { API_ENDPOINTS } from '../../../core/config/api-endpoints';
import { Vote } from '../models/vote.model';

@Injectable({
  providedIn: 'root',
})
export class SurveyService {
  private baseUrl = API_ENDPOINTS.surveys;

  constructor(private http: HttpClient) { }

  /**
   * Get all recent surveys
   */
  getRecent(): Observable<Survey[]> {
    return this.http.get<Survey[]>(this.baseUrl);
  }

  /**
   * Get survey by id
   */
  getPrevious(date: Date): Observable<{ surveys: Survey[], hasNext: boolean }> {
    return this.http.get<{ surveys: Survey[], hasNext: boolean }>(`${this.baseUrl}/before/${date.toISOString()}`);
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
  addVote(vote: Vote): Observable<Survey> {
    return this.http.post<Survey>(
      `${this.baseUrl}/vote`,
      vote
    );
  }

  /**
   * Unvote an option 
   */
  removeVote(vote: Vote): Observable<Survey> {
    return this.http.delete<Survey>(
      `${this.baseUrl}/vote`,
      { body: vote }
    );
  }


  /**
   * Create survey (admin)
   */
  create(survey: Partial<Survey>): Observable<Survey> {
    return this.http.post<Survey>(this.baseUrl, survey);
  }
}
