package com.innovators.jobreferralportal.repository;

import com.innovators.jobreferralportal.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public interface JobRepo extends JpaRepository<Job, Long> {

    public List<Job> findByKeywordsContainingIgnoreCase(String keyword);

}
