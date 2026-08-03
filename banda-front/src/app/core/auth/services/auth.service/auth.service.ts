import { Injectable } from "@angular/core";
import { API_ENDPOINTS } from "../../../config/api-endpoints";
import { HttpClient } from "@angular/common/http";

@Injectable({providedIn : "root"})
export class AuthService {
    private baseURL = API_ENDPOINTS.auth;

    constructor(private http: HttpClient) { }
    
    login(): void {
        return this.http.post(
            `${this.baseURL}/login`
        );
    }


}