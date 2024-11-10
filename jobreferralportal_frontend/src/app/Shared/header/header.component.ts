import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../Services/services/auth.service';
import { HrService } from '../../Services/services/hr.service';
import { EmployeeService } from '../../Services/services/employee.service';
import { Router } from '@angular/router'; 
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-header',
  standalone: true,
  templateUrl: './header.component.html',
  imports: [CommonModule],
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  userRole: string | null = null;

  constructor(
    private authService: AuthService, 
    private hrService: HrService, 
    private employeeService: EmployeeService,
    private router: Router
  ) {}

  ngOnInit() {
    this.userRole = this.authService.getUserRole(); 
  }

  isLoggedIn(): boolean {
    return this.authService.isLoggedIn(); 
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/login']); 
  }

  viewJobs() {
    const destination = this.userRole === 'HR' ? '/hr-dashboard' : '/employee-dashboard';
    this.router.navigate([destination]);
  }

  viewReferredCandidates() {
    if (this.userRole === 'HR') {
      this.hrService.getAllReferredCandidates().subscribe((data) => {
        this.router.navigate(['/referred-candidates'], { state: { data } });
      });
    } else if (this.userRole === 'EMPLOYEE') {
      this.employeeService.getAllReferredCandidates().subscribe((data) => {
        this.router.navigate(['/referred-candidates'], { state: { data } });
      });
    }
  }

  navigateTo(route: string) {
    this.router.navigate([`/${route}`]);
  }

 
  get currentUrl(): string {
    return this.router.url;
  }
}
