import { inject } from '@angular/core';
import { CanActivateFn } from '@angular/router';
import { AuthService } from '../Services/services/auth.service';
import { Router } from '@angular/router';

export const employeeHrGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthService);
  const router = inject(Router);

 
  const userRole = authService.getUserRole();
  if (userRole === 'EMPLOYEE' || userRole === 'HR') {
    return true;
  } else {
    const lastUrl = authService.getLastUrl();
    router.navigate([lastUrl || 'logout']); 
    return false;
  }
};
