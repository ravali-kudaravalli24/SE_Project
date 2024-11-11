import { ChangeDetectorRef, Component, OnInit, OnDestroy } from '@angular/core';
import { AuthService } from '../../Services/services/auth.service';
import { HrService } from '../../Services/services/hr.service';
import { EmployeeService } from '../../Services/services/employee.service';
import { Router } from '@angular/router'; 
import { CommonModule } from '@angular/common';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-header',
  standalone: true,
  templateUrl: './header.component.html',
  imports: [CommonModule],
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit, OnDestroy {
  userRole: string | null = null;
  private roleSubscription: Subscription | null = null;

  constructor(
    private authService: AuthService, 
    private hrService: HrService, 
    private employeeService: EmployeeService,
    private router: Router,
    private cdRef: ChangeDetectorRef
  ) {
    this.userRole = this.authService.getUserRole(); 
  }

  ngOnInit() {
    this.roleSubscription = this.authService.getUserRoleObservable().subscribe(role => {
      this.userRole = role; 
    });
    const sessionRole = this.authService.getUserRole();
    if (sessionRole) {
      this.userRole = sessionRole;
    }
  }

  ngOnDestroy() {
    if (this.roleSubscription) {
      this.roleSubscription.unsubscribe(); 
    }
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
