import { Component, OnInit } from '@angular/core';
import { EmployeeService } from '../../Services/services/employee.service';
import { HrService } from '../../Services/services/hr.service';
import { AuthService } from '../../Services/services/auth.service'; // Hypothetical service to get roles
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

  constructor(
    private employeeService: EmployeeService,
    private hrService: HrService,
    private authService: AuthService // Hypothetical service to check roles
  ) {}

  ngOnInit(): void {
    this.fetchLeaderboard();
  }

  fetchLeaderboard(): void {
    const userRole = this.authService.getUserRole(); // Method to get the user's role
    if (userRole === 'HR') {
      // Call HR service
      this.hrService.getLeaderBoard().subscribe(
        (data: any[]) => {
          console.log('HR leaderboard data:', data);
          this.mapLeaderboardData(data);
        },
        (error) => {
          this.handleError(error);
        }
      );
    } else if (userRole === 'EMPLOYEE') {
      // Call Employee service
      this.employeeService.getLeaderBoard().subscribe(
        (data: any[]) => {
          console.log('Employee leaderboard data:', data);
          this.mapLeaderboardData(data);
        },
        (error) => {
          this.handleError(error);
        }
      );
    } else {
      this.errorMessage = 'Unauthorized access.';
      this.isLoading = false;
    }
  }

  mapLeaderboardData(data: any[]): void {
    this.leaderboard = data.map(item => ({
      empId: item.empId,
      name: item.employeeName,
      score: item.score
    }));
    this.isLoading = false;
  }

  handleError(error: any): void {
    console.error('Error fetching leaderboard data:', error);
    this.errorMessage = 'Failed to load leaderboard. Please try again later.';
    this.isLoading = false;
  }
}
