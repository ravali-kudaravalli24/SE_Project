import { Component, OnInit } from '@angular/core';
import { EmployeeService } from '../../Services/services/employee.service';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
@Component({
  selector: 'app-employee-dashboard',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './employee-dashboard.component.html',
  styleUrls: ['./employee-dashboard.component.css']
})
export class EmployeeDashboardComponent implements OnInit {
  jobs: any[] = [];

  constructor(private employeeService: EmployeeService, private router: Router) {}

  ngOnInit(): void {
    this.loadJobs();
  }

  loadJobs(): void {
    this.employeeService.getAllJobs().subscribe({
      next: (data) => {
        this.jobs = data;
        
      },
      error: (error) => {
        console.error('Error fetching jobs:', error);
      },
      complete: () => {
        console.info('Job fetching complete');
      }
    });
  }

  referCandidate(jobId: number): void {
    this.router.navigate(['/refer', jobId]);
  }

}
