package com.innovators.jobreferralportal.Service;

import com.innovators.jobreferralportal.entity.Employee;
import com.innovators.jobreferralportal.entity.Job;
import com.innovators.jobreferralportal.entity.ReferredCandidate;
import com.innovators.jobreferralportal.repository.EmployeeRepo;
import com.innovators.jobreferralportal.repository.JobRepo;
import com.innovators.jobreferralportal.repository.ReferredCandidateRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private ReferredCandidateRepo referredCandidateRepo;

	@Autowired
	private JobRepo jobRepo;

	@Autowired
	private EmployeeRepo employeeRepo;

	@Override
	public void referCandidate(ReferredCandidate referredCandidate) {

//		if (referredCandidate.getFName() == null || referredCandidate.getLName() == null
//				|| referredCandidate.getYearsOfExp() < 0 || referredCandidate.getReferredBy() == null
//				|| referredCandidate.getStatus() == null) {
//			throw new IllegalArgumentException(
//					"All fields except referralId must be non-null and yearsOfExp must be non-negative.");
//		}

		referredCandidateRepo.save(referredCandidate);
	}

	@Override
	public List<ReferredCandidate> getAllReferredCandidatesByEmployeeId(Long employeeId) {
		return referredCandidateRepo.findByReferredBy(employeeId);

	}

	@Override
	public List<ReferredCandidate> getAllReferredCandidatesSearch(Long employeeId, String name) {
		return referredCandidateRepo.findByReferredByAndFirstNameContainingIgnoreCase(employeeId, name.toLowerCase());
	}

	@Override
	public void deleteReferral(Long id) {
		referredCandidateRepo.deleteById(id);
	}


	@Override
	public List<List<String>> getLeaderBoardList() {
		List<List<String>> res = new ArrayList<>();


		List<Employee> employeeList = employeeRepo.findAll();


		System.out.println("Before sorting:");
		employeeList.forEach(e -> {
			System.out.println("ID: " + e.getEmployeeID() + ", Score: " + e.getScore());
		});


		employeeList.sort((e1, e2) -> {
			Integer score1 = e1.getScore() == null ? 0 : e1.getScore();
			Integer score2 = e2.getScore() == null ? 0 : e2.getScore();
			return score2.compareTo(score1); // Descending order
		});


		System.out.println("After sorting:");
		employeeList.forEach(e -> {
			System.out.println("ID: " + e.getEmployeeID() + ", Score: " + e.getScore());
		});


		for (Employee e : employeeList) {

			String employeeName = (e.getLName() != null ? e.getLName() : "null") + "," +
					(e.getFName() != null ? e.getFName() : "null");

			String score = e.getScore() == null ? "0" : String.valueOf(e.getScore());

			String empId = String.valueOf(e.getEmployeeID());

			List<String> employeeDetailList = new ArrayList<>();
			employeeDetailList.add(empId);
			employeeDetailList.add(employeeName);
			employeeDetailList.add(score);
			res.add(employeeDetailList);
		}
		return res;
	}
}
