import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class VacancyContTest {

    @Test
    void testVacancy() {
        VacancyRepository vacancyRepo = Mockito.mock(VacancyRepository.class);
        Model model = new ExtendedModelMap();

        VacancyCont controller = new VacancyCont();
        controller.vacancyRepo = vacancyRepo;

        Vacancy vacancy1 = new Vacancy();
        vacancy1.setId(1L);
        vacancy1.setStatus(VacancyStatus.WAITING);

        Vacancy vacancy2 = new Vacancy();
        vacancy2.setId(2L);
        vacancy2.setStatus(VacancyStatus.APPROVED);

        Vacancy vacancy3 = new Vacancy();
        vacancy3.setId(3L);
        vacancy3.setStatus(VacancyStatus.REJECTED);

        List<Vacancy> expectedVacancies = Arrays.asList(vacancy1, vacancy2, vacancy3);

        when(vacancyRepo.findAllByStatus(VacancyStatus.WAITING)).thenReturn(Arrays.asList(vacancy1));
        when(vacancyRepo.findAllByStatus(VacancyStatus.APPROVED)).thenReturn(Arrays.asList(vacancy2));
        when(vacancyRepo.findAllByStatus(VacancyStatus.REJECTED)).thenReturn(Arrays.asList(vacancy3));

        String result = controller.Vacancy(model);

        assertEquals("vacancy", result);
        assertEquals(expectedVacancies, model.getAttribute("vacancies"));

        verify(vacancyRepo).findAllByStatus(VacancyStatus.WAITING);
        verify(vacancyRepo).findAllByStatus(VacancyStatus.APPROVED);
        verify(vacancyRepo).findAllByStatus(VacancyStatus.REJECTED);
    }
}