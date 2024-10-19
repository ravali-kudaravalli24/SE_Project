import { Component, OnInit } from '@angular/core';
import { HrService } from '../../Services/services/hr.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms'; 
import { JobService } from '../../Services/services/job.service';
@Component({
  selector: 'app-hr-dashboard',
  templateUrl: './hr-dashboard.component.html',
  styleUrls: ['./hr-dashboard.component.css'],
  standalone: true,
  imports: [CommonModule, FormsModule]
})
export class HrDashboardComponent implements OnInit {
  jobs: any[] = [];
  leaderBoard: any[] = [];
  showJobModal = false;
  selectedJob: any = null;
  newJob = { jobId: 0, positionName: '', jobDescription: '' ,departmentName:'',numberOfOpenPositions:0 };
  alertMessage: string = '';
  showConfirmationModal = false;
  jobToDeleteId: number | null = null;
  constructor(private hrService: HrService , private jobService: JobService) {}

  ngOnInit(): void {
    this.loadJobs();
  }

  loadJobs(): void {
    this.hrService.getAllJobs().subscribe((data) => {
      this.jobs = data;
    });
  }


  editJob(job: any): void {
    this.selectedJob = job;
    this.newJob = { ...job };
    this.showJobModal = true;
  }
  openAddJobModal() {
    this.showJobModal = true; 
    this.resetNewJob();
  }
  resetNewJob():void{
    this.newJob = {
      jobId: 0,
      positionName: '',
      jobDescription: '',
      departmentName: '',
      numberOfOpenPositions: 0
    }
  }

  saveJob(): void {
    if (this.selectedJob) {
      this.jobService.updateJob(this.selectedJob.jobId, this.newJob).subscribe(
        (response) => {
          console.log('Backend response:', response);
          const index = this.jobs.findIndex(job => job.jobId === this.selectedJob.jobId);
          if (index !== -1) {
            this.jobs[index] = { ...this.newJob };
          }
          this.closeJobModal();
        },
        (error) => {
          console.error('Error updating job in backend:', error);
        }
      );
    } else {
      this.jobService.addJob(this.newJob).subscribe(
        (response) => {
          console.log('Backend response:', response); 
          this.jobs.push(response);
          this.closeJobModal();
        },
        (error) => {
          console.error('Error adding job:', error);
        }
      );
    }
  }
  

  

  deleteJob(jobId: number): void {
    this.jobService.deleteJob(jobId).subscribe(() => {
      this.jobToDeleteId = jobId;
      this.showConfirmationModal = true;
    });
  }
  confirmDelete() {
    if (this.jobToDeleteId) {
      this.jobService.deleteJob(this.jobToDeleteId).subscribe(() => {
        this.loadJobs(); 
        this.closeConfirmationModal(); 
      });
    }
  }

  cancelDelete() {
    this.closeConfirmationModal(); 
  }

  closeConfirmationModal() {
    this.showConfirmationModal = false;
    this.jobToDeleteId = null; 
  }

  closeJobModal(): void {
    this.showJobModal = false;
    }

  
}
