import { Routes } from '@angular/router';
import { SurveyListPage } from './features/survey/pages/survey-list.page/survey-list.page';
import { LoginPage } from './features/auth/login/pages/login.page/login.page';
import { authGuard } from './core/auth/guards/auth.guard';
import { SignupPage } from './features/auth/signup/pages/signup.page/signup.page';

export const routes: Routes = [
    
  { path: '', redirectTo: 'connexion', pathMatch: 'full' },

  { path: 'connexion', component: LoginPage },
  
  { path: 'inscription', component: SignupPage },
  
  { path: 'sondages', canActivate: [authGuard], component: SurveyListPage },
  
  { path: '**', redirectTo: 'connexion' }
];
