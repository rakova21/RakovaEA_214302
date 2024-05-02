package com.crewrisk.controller;

public class CertificationContTest {


        @Test
        void testCertification() {
            // Prepare test data
            String answer1 = "Answer 1";
            String answer2 = "Answer 2";
            String answer3 = "Answer 3";
            String answer4 = "Answer 4";
            String answer5 = "Answer 5";
            String answer6 = "Answer 6";
            String answer7 = "Answer 7";
            String answer8 = "Answer 8";
            String answer9 = "Answer 9";
            String answer10 = "Answer 10";
            boolean answer11 = true;
            boolean answer12 = false;

            UsersRepository usersRepo = Mockito.mock(UsersRepository.class);
            Users user = Mockito.mock(Users.class);
            Mockito.when(usersRepo.getReferenceById(Mockito.anyLong())).thenReturn(user);

            CertificationCont controller = new CertificationCont();
            controller.usersRepo = usersRepo;

            Model model = new ExtendedModelMap();
            MockHttpServletRequest request = new MockHttpServletRequest();

            request.setParameter("answer1", answer1);
            request.setParameter("answer2", answer2);
            request.setParameter("answer3", answer3);
            request.setParameter("answer4", answer4);
            request.setParameter("answer5", answer5);
            request.setParameter("answer6", answer6);
            request.setParameter("answer7", answer7);
            request.setParameter("answer8", answer8);
            request.setParameter("answer9", answer9);
            request.setParameter("answer10", answer10);
            request.setParameter("answer11", String.valueOf(answer11));
            request.setParameter("answer12", String.valueOf(answer12));

            String result = controller.certification(model, request);

            assertEquals("redirect:/profile", result);

            verify(usersRepo).getReferenceById(Mockito.anyLong());
            verify(user).setCertification(Mockito.any(Certification.class));
            verify(user).setCertificationed(false);
            verify(usersRepo).save(user);
        }


}
