import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class HumansContTest {

    @Test
    void testHumanCertification() {
        UsersRepository usersRepo = Mockito.mock(UsersRepository.class);
        CertificationRepository certificationRepo = Mockito.mock(CertificationRepository.class);
        Model model = new ExtendedModelMap();

        HumansCont controller = new HumansCont();
        controller.usersRepo = usersRepo;
        controller.certificationRepo = certificationRepo;

        Long userId = 1L;
        Users user = new Users();
        user.setId(userId);
        user.setCertificationed(false);

        when(usersRepo.getReferenceById(userId)).thenReturn(user);

        String result = controller.HumanCertificationed(userId);

        assertEquals("redirect:/humans/{id}", result);
        verify(user).setCertificationed(true);
        verify(usersRepo).save(user);
        verify(certificationRepo).deleteById(user.getCertification().getId());
    }
}