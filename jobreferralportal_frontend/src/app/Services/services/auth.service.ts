import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Router } from '@angular/router';
import { catchError, Observable, tap, throwError } from 'rxjs';
import { SessionService } from './session.service'; 

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private baseUrl = '/api/users';
  private lastUrl: string | null = null;


  constructor(
    private http: HttpClient,
    private router: Router,
    private sessionService: SessionService 
  ) {}

  setLastUrl(url: string): void {
    sessionStorage.setItem('lastUrl', url);
  }

  getLastUrl(): string | null {
    const url = sessionStorage.getItem('lastUrl');
    console.log(url);
    return url; 
  }
  
  login(username: string, password: string): Observable<any> {
    const params = new HttpParams()
      .set('username', username)
      .set('password', password);

    return this.http.post<any>(`${this.baseUrl}/login`, null, { params, responseType: 'json' }).pipe(
      tap(response => {
        const employeeID = response.employeeID;
        const employeeType=response.employeeType;
        this.sessionService.setSessionData('employeeID', employeeID); 
        this.sessionService.setSessionData('employeeType', employeeType); 
      }),
      catchError(error => {
        console.error('Login failed', error);
        return throwError(() => error);
      })
    );
  }

  logout() {
    return this.http.post(`${this.baseUrl}/logout`, {}, { responseType: 'text' }).subscribe(() => {
      this.sessionService.clearSession();
      this.router.navigate(['/login']);
    });
  }

  isAuthenticated(): boolean {
    return !!this.sessionService.getSessionData('employeeID'); 
  }
  

  isLoggedIn(): boolean {
    return this.isAuthenticated();
  }

  getUserRole(): string|null {
    return this.sessionService.getSessionData('employeeType');
  }
  addUser(employee: any): Observable<any> {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.post<any>(`${this.baseUrl}/addUsers`, employee, { headers });
  }
 
  
}
