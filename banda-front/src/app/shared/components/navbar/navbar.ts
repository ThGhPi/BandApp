import { Component, inject } from '@angular/core';
import { Router, RouterLink } from '@angular/router';

@Component({
  selector: 'app-navbar',
  imports: [RouterLink],
  templateUrl: './navbar.html',
  styleUrl: './navbar.css',
})
export class Navbar {
  isOpen = false;
  private readonly router = inject(Router);

  toggleMenu() {
    this.isOpen = !this.isOpen;
    this.router.navigate(['/']);
  }
}
