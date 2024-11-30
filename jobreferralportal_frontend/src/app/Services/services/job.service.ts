import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class JobService {
  private baseUrl = '/api/hr';

  
  constructor(private http: HttpClient) {}

  addJob(job: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/addJob`, job);
  }

  updateJob(jobId: number, job: any): Observable<String> {
    return this.http.put(`${this.baseUrl}/updateJob/${jobId}`, job,{ responseType: 'text' });
  }

  

  deleteJob(jobId: number): Observable<String> {
    return this.http.delete(`${this.baseUrl}/deleteJob/${jobId}`,{ responseType: 'text' });
  }
  
  uploadBulkJobs(file: File): Observable<any> {
    const formData: FormData = new FormData();
    formData.append('file', file, file.name);

    return this.http.post(`${this.baseUrl}/uploadBulkJobs`, formData,{ responseType: 'text' });
  }


  searchJobs(positionName: string): Observable<any> {
    return this.http.get<any>('/api/employee/search', {
        params: { positionName: positionName }
    });
  }


    hrSearchJobs(positionName: string): Observable<any> {
      return this.http.get<any>('/api/hr/search', {
          params: { keyword: positionName }
      });

}
}