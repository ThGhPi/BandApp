import { Component, inject, signal } from '@angular/core';
import { AuthService } from '../../../../core/auth/services/auth.service/auth.service';
import { Profil } from '../../models/profil.model';

@Component({
  selector: 'app-profil.page',
  imports: [],
  templateUrl: './profil.page.html',
  styleUrl: './profil.page.css',
})
export class ProfilPage {
  private profilService = inject(AuthService);

  profil = signal<Profil | null>(null);

  ngOnInit() {
    this.loadProfil();
  }

  loadProfil() {
    this.profilService.getProfil().subscribe(data => {
      this.profil.set(data);
    });
  }
}
