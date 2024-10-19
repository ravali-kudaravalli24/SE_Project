import { Component } from '@angular/core';
import { AuthService } from '../../Services/services/auth.service';
import { HrService } from '../../Services/services/hr.service';
import { Router } from '@angular/router'; 
import { CommonModule } from '@angular/common';
import { EmployeeService } from '../../Services/services/employee.service';

@Component({
  selector: 'app-header',
  standalone: true,
  templateUrl: './header.component.html',
  imports: [CommonModule],
  styleUrls: ['./header.component.css']
})
export class HeaderComponent {
  constructor(
    private authService: AuthService, 
    private hrService: HrService, 
    private employeeService:EmployeeService,
    private router: Router
  ) {}

  logout() {
    this.authService.logout();
    this.router.navigate(['/login']); 
  }

  isLoggedIn(): boolean {
    return this.authService.isLoggedIn(); 
  }

  
  viewReferredCandidates(): void {
    const userRole = this.authService.getUserRole(); 
    if (userRole === 'HR') {
      this.hrService.getAllReferredCandidates().subscribe(
        (data) => {
          console.log('Referred candidates for HR:', data);
          this.router.navigate(['/referred-candidates'], { state: { data } });
        },
        (error) => {
          console.error('Error fetching referred candidates for HR:', error);
        }
      );
    } else if (userRole === 'EMPLOYEE') {
      this.employeeService.getAllReferredCandidates().subscribe(
        (data) => {
          console.log('Referred candidates for Employee:', data);
          this.router.navigate(['/referred-candidates'], { state: { data } });
        },
        (error) => {
          console.error('Error fetching referred candidates for Employee:', error);
        }
      );
    }
  }

  viewJobs() {
    const userRole = this.authService.getUserRole(); 
    if (userRole === 'HR') {
    this.router.navigate(['/hr-dashboard']);
    }
    if (userRole === 'EMPLOYEE') 
    {
      this.router.navigate(['/employee-dashboard']);
    }
  }
  getButtonDetails() {
    const isReferredCandidatesPage = this.router.url.includes('referred-candidates');
    return {
      text: isReferredCandidatesPage ? 'View Jobs' : 'View Referred Candidates',
      action: isReferredCandidatesPage ? this.viewJobs.bind(this) : this.viewReferredCandidates.bind(this)
    };
  }
}
