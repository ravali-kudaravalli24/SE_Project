package com.innovators.jobreferralportal.Service;


import com.innovators.jobreferralportal.entity.Job;
import com.innovators.jobreferralportal.repository.JobRepo;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class JobServiceImpl implements JobService {

    @Autowired
    private JobRepo jobRepo;

    @Override
    public Job addJob(Job jobListing) {
        return jobRepo.save(jobListing);

    }

    @Override
    public void saveJobsFromExcel(MultipartFile file) throws IOException {
        List<Job> jobs = parseExcelFile(file);
        jobRepo.saveAll(jobs);
    }

    private List<Job> parseExcelFile(MultipartFile file) throws IOException {
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
            job.setNumberOfOpenPositions(currentRow.getCell(3).getStringCellValue());

            jobList.add(job);
        }

        workbook.close();
        return jobList;
    }
}
