package com.innovators.jobreferralportal.Service;

import com.innovators.jobreferralportal.entity.Job;
import com.innovators.jobreferralportal.entity.ReferredCandidate;
import com.innovators.jobreferralportal.repository.JobRepo;
import com.innovators.jobreferralportal.repository.ReferredCandidateRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private ReferredCandidateRepo referredCandidateRepo;

	@Autowired
	private JobRepo jobRepo;

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
	public void deleteReferral(Long id) {
		referredCandidateRepo.deleteById(id);
	}

}

