package com.innovators.jobreferralportal.repository;

import com.innovators.jobreferralportal.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepo extends JpaRepository<Job, Long> {

    public List<Job> findByPositionNameContainingIgnoreCaseAndLocationContainingIgnoreCase(String positionName, String location);

    public List<Job> findByPositionNameContainingIgnoreCase(String positionName);

}
