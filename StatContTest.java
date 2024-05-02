import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class StatsContTest {

    @Test
    void testStats() {
        UsersRepository usersRepo = Mockito.mock(UsersRepository.class);
        ScoreRepository scoreRepo = Mockito.mock(ScoreRepository.class);
        Model model = new ExtendedModelMap();

        StatsCont controller = new StatsCont();
        controller.usersRepo = usersRepo;
        controller.scoreRepo = scoreRepo;

        // Mock data
        List<Users> users = Arrays.asList(
                createUser("John Doe", Education.MID, 3),
                createUser("Jane Smith", Education.HIGH, 5),
                createUser("Mike Johnson", Education.MID, 2),
                createUser("Sarah Williams", Education.HIGH, 4),
                createUser("Robert Brown", Education.HIGH, 6)
        );

        List<Score> scores = Arrays.asList(
                createScore(users.get(0), 80),
                createScore(users.get(1), 90),
                createScore(users.get(2), 75),
                createScore(users.get(3), 85),
                createScore(users.get(4), 95)
        );

        when(usersRepo.findAllByRoleAndTertiary_Marital(Role.CLIENT, any(Marital.class))).thenReturn(Collections.emptyList());
        when(usersRepo.findAllByRoleAndTertiary_Origin(Role.CLIENT, any(Origin.class))).thenReturn(Collections.emptyList());
        when(usersRepo.findAllByRoleAndTertiary_Citizenship(Role.CLIENT, any(Citizenship.class))).thenReturn(Collections.emptyList());
        when(usersRepo.findAllByRoleAndTertiary_Education(Role.CLIENT, any(Education.class))).thenReturn(users);
        when(usersRepo.findAllByRoleOrderBySecondary_Experience(Role.CLIENT)).thenReturn(users);
        when(scoreRepo.findAllByOwner_Role(Role.CLIENT)).thenReturn(scores);

        String result = controller.Stats(model);

        assertEquals("stats", result);

        // Verify model attributes
        verify(model).addAttribute(eq("maritals"), any(HashMap.class));
        verify(model).addAttribute(eq("origins"), any(HashMap.class));
        verify(model).addAttribute(eq("citizenships"), any(HashMap.class));
        verify(model).addAttribute(eq("topQuantityName"), any(String[].class));
        verify(model).addAttribute(eq("topQuantityNumber"), any(int[].class));
        verify(model).addAttribute(eq("mid"), anyInt());
        verify(model).addAttribute(eq("high"), anyInt());
        verify(model).addAttribute(eq("userSize"), anyInt());
        verify(model).addAttribute(eq("expString"), any(String[].class));
        verify(model).addAttribute(eq("expInt"), any(int[].class));
        verify(model).addAttribute(eq("medExp"), anyInt());
        verify(model).addAttribute(eq("medExpMin"), anyInt());
        verify(model).addAttribute(eq("medExpMax"), anyInt());
    }

    private Users createUser(String fio, Education education, int experience) {
        Users user = new Users();
        user.setPrimarys(new Primarys(fio));
        user.setSecondary(new Secondary(experience));
        user.setTertiary(new Tertiary(null, null, null, education));
        return user;
    }

    private Score createScore(Users owner, int summary) {
        Score score = new Score();
        score.setOwner(owner);
        score.setSummary(summary);
        return score;
    }
}