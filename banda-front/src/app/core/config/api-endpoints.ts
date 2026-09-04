import { environment } from '../../../environment/environment';

export const API_ENDPOINTS = {
  auth: `${environment.apiUrl}/auth`,
  surveys: `${environment.apiUrl}/surveys`,
  persons: `${environment.apiUrl}/persons`,
  events: `${environment.apiUrl}/events`,
  partitions: `${environment.apiUrl}/partitions`,
};
