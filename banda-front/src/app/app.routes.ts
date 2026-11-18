import { Routes } from '@angular/router';
import { authGuard } from './core/auth/guards/auth.guard';
import { SurveyListPage } from './features/survey/pages/survey-list.page/survey-list.page';
import { LoginPage } from './features/auth/pages/login.page/login.page';
import { SignupPage } from './features/auth/pages/signup.page/signup.page';
import { ProfilPage } from './features/auth/pages/profil.page/profil.page';
import { PersonListPage } from './features/person/pages/person-list.page/person-list.page';

export const routes: Routes = [
    
  { path: 'connexion', component: LoginPage },
  
  { path: 'inscription', component: SignupPage },

  { path: 'profil', canActivate: [authGuard], component: ProfilPage },

  { path: 'membres', canActivate: [authGuard], component: PersonListPage },
  
  { path: 'sondages', canActivate: [authGuard], component: SurveyListPage },
  
  { path: '', redirectTo: 'connexion', pathMatch: 'full' },
  
  { path: '**', redirectTo: 'connexion' }
];
