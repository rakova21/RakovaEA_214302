package com.crewrisk.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class QuestionContTest {

    @Test
    void testQuestion() {
        int answer1 = 1;
        int answer2 = 2;
        String answer3 = "Answer 3";
        String answer4 = "Answer 4";
        int answer5 = 5;
        int answer6 = 6;
        String answer7 = "Answer 7";
        int answer8 = 8;
        String answer9 = "Answer 9";
        String answer10 = "Answer 10";
        String answer11 = "Answer 11";
        String answer12 = "Answer 12";

        UsersRepository usersRepo = Mockito.mock(UsersRepository.class);
        Users user = Mockito.mock(Users.class);
        Mockito.when(usersRepo.getReferenceById(Mockito.anyLong())).thenReturn(user);

        QuestionCont controller = new QuestionCont();
        controller.usersRepo = usersRepo;

        Model model = new ExtendedModelMap();
        MockHttpServletRequest request = new MockHttpServletRequest();

        request.setParameter("answer1", String.valueOf(answer1));
        request.setParameter("answer2", String.valueOf(answer2));
        request.setParameter("answer3", answer3);
        request.setParameter("answer4", answer4);
        request.setParameter("answer5", String.valueOf(answer5));
        request.setParameter("answer6", String.valueOf(answer6));
        request.setParameter("answer7", answer7);
        request.setParameter("answer8", String.valueOf(answer8));
        request.setParameter("answer9", answer9);
        request.setParameter("answer10", answer10);
        request.setParameter("answer11", answer11);
        request.setParameter("answer12", answer12);

        String result = controller.question(model, request);

        assertEquals("redirect:/profile", result);

        verify(usersRepo).getReferenceById(Mockito.anyLong());
        verify(user).setQuestion(Mockito.any(Question.class));
        verify(usersRepo).save(user);
    }
}
