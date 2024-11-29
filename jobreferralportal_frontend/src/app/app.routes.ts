import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './Core/login/login.component';
import { ReferCandidateComponent } from './Core/refer-candidate/refer-candidate.component'; 
import { EmployeeDashboardComponent } from './Core/employee-dashboard/employee-dashboard.component';
import { HrDashboardComponent } from './Core/hr-dashboard/hr-dashboard.component';
import { ReferredCandidatesComponent } from './Shared/referred-candidates/referred-candidates.component';
import { AddUserComponent } from './Core/add-user/add-user.component';
import { employeeGuard } from './Gaurds/employee.guard';
import { hrGuard } from './Gaurds/hr.guard';
import { employeeHrGuard } from './Gaurds/employee-hr.gaurd';
import { LeaderBoardComponent } from './Core/leader-board/leader-board.component';

const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'hr-dashboard', component: HrDashboardComponent, canActivate: [hrGuard] }, 
  { path: 'employee-dashboard', component: EmployeeDashboardComponent, canActivate: [employeeGuard] },
  { path: 'refer/:jobId', component: ReferCandidateComponent, canActivate: [employeeGuard] }, 
  { path: 'referred-candidates', component: ReferredCandidatesComponent, canActivate: [employeeHrGuard] }, 
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: 'addUsers', component: AddUserComponent },
  {path:'leader-board', component: LeaderBoardComponent,canActivate: [employeeHrGuard]}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
