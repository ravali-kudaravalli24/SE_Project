import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class HrService {
  private baseUrl = '/api/hr';

  constructor(private http: HttpClient) {}

  getAllJobs(): Observable<any> {
    return this.http.get(`${this.baseUrl}/getAllJobs`);
  }

  
  getAllReferredCandidates(): Observable<any> {
    return this.http.get(`${this.baseUrl}/getAllReferredCandidates`);
  }
  downloadResume(candidateId: number): Observable<Blob> {
    return this.http.get(`${this.baseUrl}/downloadResume/${candidateId}`, {
      responseType: 'blob' // Set response type to 'blob' to handle file download
    });
  }

  
}
