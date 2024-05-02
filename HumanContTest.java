import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class HumansContTest {

    @Test
    void testHuman() {
        UsersRepository usersRepo = Mockito.mock(UsersRepository.class);
        Model model = new ExtendedModelMap();

        HumansCont controller = new HumansCont();
        controller.usersRepo = usersRepo;

        Users user = new Users();
        user.setId(1L);
        user.setUsername("john.doe");
        user.setPassword("password");
        user.setRole(Role.CLIENT);

        Long userId = 1L;

        when(usersRepo.getReferenceById(userId)).thenReturn(user);

        String result = controller.Human(model, userId);

        assertEquals("human", result);
        assertEquals(user, model.getAttribute("human"));

        verify(usersRepo).getReferenceById(userId);
    }
}