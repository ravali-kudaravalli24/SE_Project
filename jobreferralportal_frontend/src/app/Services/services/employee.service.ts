import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class EmployeeService {
  private baseUrl = '/api/employee';

  constructor(private http: HttpClient) {}

  
  getAllJobs(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/getAllJobs`);
  }

  
  referCandidate(jobId: number, fName: string, lName: string, yearsOfExp: number, referredBy: number, resume: File): Observable<HttpResponse<string>>  {
    const formData: FormData = new FormData();
    formData.append('resume', resume);
    formData.append('fName', fName);
    formData.append('lName', lName);
    formData.append('yearsOfExp', yearsOfExp.toString());
    formData.append('referredBy', referredBy.toString());

    return this.http.post<string>(`${this.baseUrl}/referCandidate`, formData, { 
      responseType: 'text' as 'json', 
      observe: 'response' 
    });
    
  }
  getLeaderBoard(): Observable<any> {
    return this.http.get<any[]>(`${this.baseUrl}/getLeaderBoardEmp`);
  }

  getAllReferredCandidates(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/getAllReferredCandidates`);
  }
  deleteReferral(referralId: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/deleteReferral/${referralId}`);
  }
}

