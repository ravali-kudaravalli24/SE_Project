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

		// Retrieve all employees from the repository
		List<Employee> employeeList = employeeRepo.findAll();

		System.out.println("Before sorting:");
		employeeList.forEach(e -> {
			System.out.println("ID: " + e.getEmployeeID() + ", Score: " + e.getScore());
		});

		// Sort employees by score in descending order
		employeeList.sort((e1, e2) -> {
			Integer score1 = e1.getScore() == null ? 0 : e1.getScore();
			Integer score2 = e2.getScore() == null ? 0 : e2.getScore();
			return score2.compareTo(score1); // Descending order
		});

		System.out.println("After sorting:");
		employeeList.forEach(e -> {
			System.out.println("ID: " + e.getEmployeeID() + ", Score: " + e.getScore());
		});

		// Add employees to the result list only if their score is 1 or above
		for (Employee e : employeeList) {
			Integer scoreValue = e.getScore() == null ? 0 : e.getScore();
			if (scoreValue >= 1) { // Check if score is 1 or above
				String employeeName = (e.getLastName() != null ? e.getLastName() : "null") + "," +
						(e.getFirstName() != null ? e.getFirstName() : "null");

				String score = String.valueOf(scoreValue); // Convert score to string
				String empId = String.valueOf(e.getEmployeeID());

				List<String> employeeDetailList = new ArrayList<>();
				employeeDetailList.add(empId);
				employeeDetailList.add(employeeName);
				employeeDetailList.add(score);

				res.add(employeeDetailList);
			}
		}

		return res;
	}

}
