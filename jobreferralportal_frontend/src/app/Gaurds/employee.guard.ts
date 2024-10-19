import { Injectable } from '@angular/core';
import { CanActivateFn } from '@angular/router';
import { AuthService } from '../Services/services/auth.service';
import { Router } from '@angular/router';
import { inject } from '@angular/core';

export const employeeGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthService);
  const router = inject(Router);

  if (authService.getUserRole() === 'EMPLOYEE') {
    return true;
  } else {
    const lastUrl = authService.getLastUrl();
    router.navigate([lastUrl || 'logout']); 
    return false;
  }
};
