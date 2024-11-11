import { Component, OnInit } from '@angular/core';
import { EmployeeService } from '../../Services/services/employee.service';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { JobService } from '../../Services/services/job.service';
import { FormsModule } from '@angular/forms';
@Component({
  selector: 'app-employee-dashboard',
  standalone: true,
  imports: [CommonModule,FormsModule],
  templateUrl: './employee-dashboard.component.html',
  styleUrls: ['./employee-dashboard.component.css']
})
export class EmployeeDashboardComponent implements OnInit {
  searchQuery: string = '';
  jobs: any[] = [];

  constructor(private employeeService: EmployeeService, private router: Router,private jobService: JobService) {}

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

  searchJobs(): void {
    if (this.searchQuery) {
      this.jobService.searchJobs(this.searchQuery).subscribe((data: any[]) => {
        this.jobs = data;
      });
    } else {
      this.loadJobs();
    }
  }

}
