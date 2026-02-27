import { Routes } from '@angular/router';
import { SurveyListPage } from './features/survey/pages/survey-list.page/survey-list.page';
import { LoginPage } from './core/auth/login.page/login.page';

export const routes: Routes = [
    
  { path: '', redirectTo: 'login', pathMatch: 'full' },

  { path: 'login', component: LoginPage },

  { path: 'sondages', component: SurveyListPage },

  { path: '**', redirectTo: 'login' }
];
