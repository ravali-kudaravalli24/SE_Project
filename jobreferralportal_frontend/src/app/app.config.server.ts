import { Injectable, PLATFORM_ID } from '@angular/core';
import { isPlatformServer } from '@angular/common';

@Injectable({
  providedIn: 'root'
})
export class ServerConfigService {
  private isServer: boolean;

  constructor() {
    this.isServer = isPlatformServer(PLATFORM_ID);
  }

  getConfig(): { [key: string]: any } {
    return this.isServer ? { 
      apiEndpoint: 'http://localhost:8090',
      logging: false
    } : {};
  }
}
