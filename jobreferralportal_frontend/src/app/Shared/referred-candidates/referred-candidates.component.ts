import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../Services/services/auth.service';
import { HrService } from '../../Services/services/hr.service';
import { EmployeeService } from '../../Services/services/employee.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-referred-candidates',
  standalone: true,
  templateUrl: './referred-candidates.component.html',
  styleUrls: ['./referred-candidates.component.css'],
  imports: [CommonModule, FormsModule]
})
export class ReferredCandidatesComponent implements OnInit {
  candidates: any[] = [];
  userRole: string | null = null;
  searchQuery: string = '';
  statusOptions: string[] = [
    'UNDER CONSIDERATION',
    'ONLINE ASSESSMENT',
    'INTERVIEW ROUND 1',
    'INTERVIEW ROUND 2',
    'MANAGERIAL ROUND',
    'ACCEPTED',
    'REJECTED'
  ];
  constructor(
    private router: Router,
    private authService: AuthService,
    private hrService: HrService ,
    private employeeService: EmployeeService
  ) {}

  ngOnInit(): void {
    this.userRole = this.authService.getUserRole();
    
    if (history.state && history.state.data) {
      this.candidates = history.state.data;
      console.log('Candidates data from state:', this.candidates);
    } 
  }

  downloadResume(candidateId: number): void {
    this.hrService.downloadResume(candidateId).subscribe(
      (response: any) => {
        const blob = new Blob([response], { type: 'application/pdf' });
        const url = window.URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = `resume_${candidateId}.pdf`;
        document.body.appendChild(a);
        a.click();
        window.URL.revokeObjectURL(url);
        a.remove();
      },
      (error: any) => {
        console.error('Error downloading resume', error);
        alert('Failed to download resume.');
      }
    );
  }

 

  updateStatus(candidate: any): void {
    this.hrService.updateCandidateStatus(candidate.status, candidate.referralId)
      .subscribe({
        next: (response) => {
          if (history.state && history.state.data) {
            this.candidates = history.state.data;
            console.log('Candidates data from state:', this.candidates);
          } else {
            // Fetch data from backend if not available in state
            this.hrService.getAllReferredCandidates().subscribe({
              next: (data) => this.candidates = data,
              error: (error) => console.error('Error fetching candidates', error)
            });
          }
          console.log('Status updated successfully', response);
        },
        error: (error) => {
          console.error('Error updating status', error);
        }
      });
  }

  searchReferredCandidates(): void {
    if (this.searchQuery) {
      this.hrService.searchReferredCandidates(this.searchQuery).subscribe((data: any[]) => {
        this.candidates = data;
      });
    } else {
        this.hrService.getAllReferredCandidates().subscribe({
          next: (data) => this.candidates = data,
          error: (error) => console.error('Error fetching candidates', error)
        });
      
    }
  }

  deleteReferral(referralId: number): void {
    if (confirm('Are you sure you want to withdraw this referral?')) {
      this.employeeService.deleteReferral(referralId).subscribe({
        next: () => {
          this.candidates = this.candidates.filter(
            (candidate) => candidate.referralId !== referralId
          );
          alert('Referral successfully withdrawn.');
        },
        error: (error) => {
          console.error('Error deleting referral', error);
          alert('Failed to delete referral.');
        },
      });
    }
  }
 }
