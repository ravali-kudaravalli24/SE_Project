import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
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
      responseType: 'blob' 
    });
  }
   
  updateCandidateStatus(statusUpdate: string, id: number): Observable<any> {
    const params = new HttpParams()
      .set('statusUpdate', statusUpdate)
      .set('id', id.toString());

    return this.http.put(`${this.baseUrl}/updateStatus`, null, { params });
  }
  searchReferredCandidates(candidateName: string): Observable<any> {
    return this.http.get<any>('/api/hr/searchCandidates', {
        params: { positionName: candidateName }
    });
  }
 
}
