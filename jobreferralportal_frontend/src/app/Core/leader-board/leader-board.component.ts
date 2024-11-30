import { Component, OnInit } from '@angular/core';
import { HrService } from '../../Services/services/hr.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-leaderboard',
  templateUrl: './leader-board.component.html',
  styleUrls: ['./leader-board.component.css'],
  standalone: true,
  imports: [CommonModule]
})
export class LeaderBoardComponent implements OnInit {
  leaderboard: Array<{ empId: string; name: string; score: string }> = [];
  isLoading = true; 
  errorMessage = '';

  constructor(private hrService: HrService) {}

  ngOnInit(): void {
    this.fetchLeaderboard();
  }

  fetchLeaderboard(): void {
    this.hrService.getLeaderBoard().subscribe(
      (data: any[]) => {
        console.log('Received leaderboard data:', data);
        this.leaderboard = data.map(item => ({
          empId: item.empId, 
          name: item.employeeName,
          score: item.score
        }));
        this.isLoading = false;
      },
      (error) => {
        console.error('Error fetching leaderboard data:', error);
        this.errorMessage = 'Failed to load leaderboard. Please try again later.';
        this.isLoading = false;
      }
    );
  }
  
}
