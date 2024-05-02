package com.crewrisk.controller;

import com.crewrisk.model.Primarys;
import com.crewrisk.model.Secondary;
import com.crewrisk.model.Tertiary;
import com.crewrisk.model.Users;
import com.crewrisk.model.enums.Citizenship;
import com.crewrisk.model.enums.Education;
import com.crewrisk.model.enums.Marital;
import com.crewrisk.model.enums.Origin;
import com.crewrisk.repo.UsersRepo;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
class HumansContTest {




        @Test
        void testHumanEditOld() throws IOException {
            // Prepare test data
            Long id = 1L;
            String surname = "Smith";
            String name = "John";
            String patronymic = "Doe";
            String passport = "123456789";
            String passport_number = "ABC123";
            String date = "2022-01-01";
            String issued = "Authority";
            String issued_date = "2022-01-02";
            String identity = "Identity";
            String address = "123 Main St";
            String tel_mob = "1234567890";
            String tel_home = "0987654321";
            String email = "test@example.com";
            String job = "Engineer";
            String post = "Manager";
            int income = 5000;
            int experience = 5;
            Marital marital = Marital.MARRIED;
            Origin origin = Origin.MINSK;
            Citizenship citizenship = Citizenship.USA;
            Education education = Education.HIGH;


            UsersRepo usersRepo = Mockito.mock(UsersRepo.class);
            Users user = Mockito.mock(Users.class);
            Primarys primarys = Mockito.mock(Primarys.class);
            Secondary secondary = Mockito.mock(Secondary.class);
            Tertiary tertiary = Mockito.mock(Tertiary.class);
            Mockito.when(usersRepo.getReferenceById(id)).thenReturn(user);
            Mockito.when(user.getPrimarys()).thenReturn(primarys);
            Mockito.when(user.getSecondary()).thenReturn(secondary);
            Mockito.when(user.getTertiary()).thenReturn(tertiary);


            HumansCont controller = new HumansCont();
            controller.usersRepo = usersRepo;

            File tempFile = File.createTempFile("test", ".jpg");
            MockMultipartFile photo = new MockMultipartFile("photo", "test.jpg", "image/jpeg", tempFile.getAbsolutePath().getBytes());

            String result = controller.HumanEditOld(null, id, photo, surname, name, patronymic, passport, passport_number,
                    date, issued, issued_date, identity, address, tel_mob, tel_home, email, job, post, income, experience,
                    marital, origin, citizenship, education);

            assertEquals("redirect:/humans/{id}", result);

            Mockito.verify(usersRepo).getReferenceById(id);
            Mockito.verify(primarys).set(surname, name, patronymic, passport, passport_number);
            Mockito.verify(secondary).set(date, issued, issued_date, identity, address, tel_mob, tel_home, email, job, post, income, experience);
            Mockito.verify(tertiary).set(marital, origin, citizenship, education);
            Mockito.verify(usersRepo).save(user);

            Mockito.verify(primarys).setPhoto(any(String.class));
            Mockito.verify(photo).transferTo(any(File.class));
            tempFile.deleteOnExit();
        }
    }