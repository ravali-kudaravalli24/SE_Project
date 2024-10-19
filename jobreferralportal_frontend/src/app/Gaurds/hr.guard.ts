import { Injectable } from '@angular/core';
import { CanActivateFn } from '@angular/router';
import { AuthService } from '../Services/services/auth.service';
import { Router } from '@angular/router';
import { inject } from '@angular/core';

export const hrGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthService);
  const router = inject(Router);

  if (authService.getUserRole() === 'HR') {
    return true;
  } else {
    const lastUrl = authService.getLastUrl();
    console.log(lastUrl);
    router.navigate([lastUrl || 'logout']); 
    return false;
  }
};
