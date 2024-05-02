import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class JobsContTest {

    @Test
    void testJobs() {
        JobsRepository jobsRepo = Mockito.mock(JobsRepository.class);
        Model model = new ExtendedModelMap();

        JobsCont controller = new JobsCont();
        controller.jobsRepo = jobsRepo;

        Jobs job1 = new Jobs();
        job1.setId(1L);
        job1.setName("Job 1");
        job1.setExp(2);
        job1.setSalary(5000);
        job1.setStatus(JobStatus.OPEN);

        Jobs job2 = new Jobs();
        job2.setId(2L);
        job2.setName("Job 2");
        job2.setExp(5);
        job2.setSalary(8000);
        job2.setStatus(JobStatus.CLOSED);

        List<Jobs> expectedJobs = Arrays.asList(job1, job2);
        JobStatus[] expectedStatuses = JobStatus.values();

        when(jobsRepo.findAll()).thenReturn(expectedJobs);

        String result = controller.jobs(model);

        assertEquals("jobs", result);
        assertEquals(expectedJobs, model.getAttribute("jobs"));
        assertEquals(expectedStatuses, model.getAttribute("jobStatuses"));

        verify(jobsRepo).findAll();
    }

    @Test
    void testJobRes_NewVacancy() {
        VacancyRepository vacancyRepo = Mockito.mock(VacancyRepository.class);
        JobsRepository jobsRepo = Mockito.mock(JobsRepository.class);
        JobsCont controller = new JobsCont();
        controller.vacancyRepo = vacancyRepo;
        controller.jobsRepo = jobsRepo;

        Long jobId = 1L;
        Long userId = 2L;

        when(controller.getUser().getId()).thenReturn(userId);
        when(vacancyRepo.findByJob_IdAndUser_Id(jobId, userId)).thenReturn(null);

        String result = controller.jobRes(jobId);

        assertEquals("redirect:/jobs", result);
        verify(vacancyRepo).save(new Vacancy(jobsRepo.getReferenceById(jobId), controller.getUser()));
    }

    @Test
    void testJobRes_ExistingVacancy() {
        VacancyRepository vacancyRepo = Mockito.mock(VacancyRepository.class);
        JobsCont controller = new JobsCont();
        controller.vacancyRepo = vacancyRepo;

        Long jobId = 1L;
        Long userId = 2L;

        when(controller.getUser().getId()).thenReturn(userId);
        when(vacancyRepo.findByJob_IdAndUser_Id(jobId, userId)).thenReturn(new Vacancy());

        String result = controller.jobRes(jobId);

        assertEquals("redirect:/jobs", result);
        verify(vacancyRepo, never()).save(any(Vacancy.class));
    }

    @Test
    void testJobAdd() {
        JobsRepository jobsRepo = Mockito.mock(JobsRepository.class);
        JobsCont controller = new JobsCont();
        controller.jobsRepo = jobsRepo;

        String jobName = "New Job";
        int jobExp = 3;
        int jobSalary = 6000;

        String result = controller.jobAdd(jobName, jobExp, jobSalary);

        assertEquals("redirect:/jobs", result);
        verify(jobsRepo).save(new Jobs(jobName, jobExp, jobSalary));
    }

    @Test
    void testJobEdit() {
        JobsRepository jobsRepo = Mockito.mock(JobsRepository.class);
        JobsCont controller = new JobsCont();
        controller.jobsRepo = jobsRepo;

        Long jobId = 1L;
        String jobName = "Updated Job";
        int jobExp = 4;
        int jobSalary = 7000;
        JobStatus jobStatus = JobStatus.IN_PROGRESS;

        Jobs job = new Jobs();
        job.setId(jobId);

        when(jobsRepo.getReferenceById(jobId)).thenReturn(job);

        String result = controller.jobEdit(jobName, jobExp, jobSalary, jobStatus, jobId);

        assertEquals("redirect:/jobs", result);
        verify(job).setName(jobName);
        verify(job).setExp(jobExp);
        verify(job).setSalary(jobSalary);
        verify(job).setStatus(jobStatus);
        verify(jobsRepo).save(job);
    }
}