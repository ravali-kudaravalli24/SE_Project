import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../Services/services/auth.service';
import { HrService } from '../../Services/services/hr.service'; // Assuming HrService handles the API calls
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-referred-candidates',
  standalone:true,
  templateUrl: './referred-candidates.component.html',
  styleUrls: ['./referred-candidates.component.css'],
  imports:[CommonModule]
})

export class ReferredCandidatesComponent implements OnInit {
  candidates: any[] = [];
  userRole: string | null = null;

  constructor(
    private router: Router,
    private authService: AuthService,
    private hrService: HrService 
  ) {}

  ngOnInit(): void {
    this.userRole = this.authService.getUserRole();
    if (history.state && history.state.data) {
      this.candidates = history.state.data;
      console.log('Candidates data from state:', this.candidates);
    } else {
      console.error('No data found in the router state.');
    }
  }

  downloadResume(candidateId: number): void {
    this.hrService.downloadResume(candidateId).subscribe(
      (response:any) => {
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
      (error:any) => {
        console.error('Error downloading resume', error);
        alert('Failed to download resume.');
      }
    );
  }
}
