
import { Component } from '@angular/core';
import { ChangeDetectorRef, Component, OnInit, OnDestroy } from '@angular/core';
import { AuthService } from '../../Services/services/auth.service';
import { HrService } from '../../Services/services/hr.service';
import { EmployeeService } from '../../Services/services/employee.service';
import { Router } from '@angular/router'; 
import { CommonModule } from '@angular/common';
import { Subscription } from 'rxjs';


@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css'],
  standalone: true,
  imports:[CommonModule]
})

export class HeaderComponent {
  
  constructor(private authService: AuthService, private router: Router,private hrService: HrService, private employeeService:EmployeeService) {
    
  }

  
    
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



  viewReferredCandidates(): void {
  
    const userRole = this.authService.getUserRole(); 
   
    if (userRole === 'HR') {
      console.log('Fetching referred candidates for HR...');
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
      console.log('Fetching referred candidates for Employee...');
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


  viewLeaderboard(): void {
    this.router.navigate(['/leader-board']);
  }

  viewJobs(): void {
    const userRole = this.authService.getUserRole(); 
    if (userRole === 'HR')
    this.router.navigate(['/hr-dashboard']);
  else
  this.router.navigate(['/employee-dashboard']);

  }



  getFirstButtonDetails() {
    const currentUrl = this.router.url;

    if (currentUrl.includes('referred-candidates')) {
      return { text: 'View Leaderboard', action: this.viewLeaderboard.bind(this) };
    } else if (currentUrl.includes('leader-board')) {
      return { text: 'View Referred Candidates', action: this.viewReferredCandidates.bind(this) };
    } else {
      return { text: 'View Referred Candidates', action: this.viewReferredCandidates.bind(this)  };
    }
  }

  getSecondButtonDetails() {
    const currentUrl = this.router.url;

    if (currentUrl.includes('referred-candidates') || currentUrl.includes('leader-board')) {
      return { text: 'View Jobs', action: this.viewJobs.bind(this) };
    } else {
      return { text: 'View Leaderboard', action: this.viewLeaderboard.bind(this) };
    }

    
  logout() {
    this.authService.logout();
    this.router.navigate(['/login']); 
  }

  
  navigateTo(route: string) {
    this.router.navigate([`/${route}`]);
  }

  get currentUrl(): string {
    return this.router.url;

  }
}
