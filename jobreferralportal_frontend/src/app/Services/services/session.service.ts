import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class SessionService {

  
  setSessionData(key: string, value: string): void {
    sessionStorage.setItem(key, value);
  }

 
  getSessionData(key: string): string | null {
    return sessionStorage.getItem(key);
  }

  clearSession(): void {
    sessionStorage.clear();
  }
}
