package com.innovators.jobreferralportal.Service;


import com.innovators.jobreferralportal.entity.Job;
import com.innovators.jobreferralportal.repository.JobRepo;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class JobServiceImpl implements JobService {

    @Autowired
    private JobRepo jobRepo;

    @Getter
    @Setter
    private Set<String> locations;


    @PostConstruct
    public void JobServiceImpl() {
        List<Job> jobs  = jobRepo.findAll();
        this.locations = jobs.stream().map(Job::getLocationData).filter(Objects::nonNull).collect(Collectors.toSet());
        System.out.println(locations);
    }

    @Override
    public List<Job> getAllJobs() {

        return jobRepo.findAll();
    }
    @Override
    public Job addJob(Job jobListing) {
        if (jobListing == null) {
            throw new IllegalArgumentException("Job listing cannot be null");
        }

        if(jobListing.getLocationData() != null){
            this.locations.add(jobListing.getLocationData());
        }
        return jobRepo.save(jobListing);
    }

    @Override
    public boolean updateJob(Long id, Job updatedJob) {
        //get Job based on id
        Job jobForUpdate = jobRepo.getReferenceById(id);

        jobForUpdate.setPositionName(updatedJob.getPositionName());
        jobForUpdate.setJobDescription(updatedJob.getJobDescription());
        jobForUpdate.setDepartmentName(updatedJob.getDepartmentName());
        jobForUpdate.setNumberOfOpenPositions(updatedJob.getNumberOfOpenPositions());

        try{
            jobRepo.save(jobForUpdate);
            return  true;
        }catch(DataAccessException e){
            return  false;
        }

    }

    @Override
    public void saveJobsFromExcel(MultipartFile file) throws IOException {
        List<Job> jobs = parseExcelFile(file);
        jobRepo.saveAll(jobs);
    }

    @Override
    public void deleteJob(Long id) {
        jobRepo.deleteById(id);
        if (jobRepo.existsById(id)) {
            throw new IllegalStateException("Job not deleted");
        }
    }

    @Override
    public List<Job> searchJob(String positionName, String location) {
        if (location == null) {
            return jobRepo.findByPositionNameContainingIgnoreCase(positionName);
        } else {
            return jobRepo.findByPositionNameContainingIgnoreCaseAndLocationContainingIgnoreCase(positionName, location);
        }

    }

    protected List<Job> parseExcelFile(MultipartFile file) throws IOException {
        List<Job> jobList = new ArrayList<>();
        Workbook workbook = WorkbookFactory.create(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);  // Make sure that the data is in the first sheet
        Iterator<Row> rows = sheet.iterator();

        // Skip the header row
        if (rows.hasNext()) rows.next();

        while (rows.hasNext()) {
            Row currentRow = rows.next();
            Job job = new Job();

            job.setPositionName(currentRow.getCell(0).getStringCellValue());
            job.setJobDescription(currentRow.getCell(1).getStringCellValue());
            job.setDepartmentName(currentRow.getCell(2).getStringCellValue());
            job.setNumberOfOpenPositions(String.valueOf((int) currentRow.getCell(3).getNumericCellValue()));

            jobList.add(job);
        }

        workbook.close();
        return jobList;
    }
}
