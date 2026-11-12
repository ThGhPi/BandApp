import { Injectable } from "@angular/core";
import { API_ENDPOINTS } from "../../../config/api-endpoints";
import { HttpClient } from "@angular/common/http";
import { LoginRequestModel } from "../../models/login-request.model";
import { LoginResponse } from "../../models/login-response.model";
import { Observable, tap } from "rxjs";
import { TokenStorageService } from "../token-storage.service";
import { RegisterRequest } from "../../../../features/auth/models/register-request.model";
import { Person } from "../../../../features/auth/models/person.model";

@Injectable({providedIn : "root"})
export class AuthService {
    private readonly baseURL = API_ENDPOINTS.auth;

    constructor(
        private readonly http: HttpClient,
        private readonly tokenStorage: TokenStorageService
    ) { }
    
    login(credentials: LoginRequestModel): Observable<LoginResponse> {
        return this.http.post<LoginResponse>(
            `${this.baseURL}/login`,
            credentials
        ).pipe(
        tap(response => {
          this.tokenStorage.setToken(response.token);
        })
      );
    }

    register(credentials: RegisterRequest): Observable<Person> {
        return this.http.post<Person>(
            `${this.baseURL}/register`,
            credentials
        );
    }

    isAuthenticated() : boolean {
        return this.tokenStorage.hasToken();
    }

    logout() : void {
        this.tokenStorage.removeToken();
    }

    getToken() : string | null {
        return this.tokenStorage.getToken();
    }
}