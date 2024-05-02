package com.crewrisk.controller;

public class ProfileContTest {
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

    class ProfileContTest {

        @Test
        void testResume() throws IOException {
            
            String uploadImg = "test/upload";
            Long userId = 1L;
            String originalFilename = "resume.pdf";

            
            UsersRepository usersRepo = Mockito.mock(UsersRepository.class);
            Users user = Mockito.mock(Users.class);
            Mockito.when(usersRepo.getReferenceById(userId)).thenReturn(user);
            
            ProfileCont controller = new ProfileCont();
            controller.uploadImg = uploadImg;
            controller.usersRepo = usersRepo;

            
            File tempFile = File.createTempFile("test", ".pdf");
            MockMultipartFile resume = new MockMultipartFile("resume", originalFilename, "application/pdf", tempFile.getAbsolutePath().getBytes());

            String result = controller.resume(null, resume);


            assertEquals("redirect:/profile", result);

            verify(usersRepo).getReferenceById(userId);
            verify(user).setResume(Mockito.anyString());
            verify(usersRepo).save(user);

            verify(resume).transferTo(Mockito.any(File.class));


            tempFile.deleteOnExit();
        }
    }
}
