package com.dsa.model;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class UserTest {

  @InjectMocks
  User user;

  @Mock
  Court court;

//  @BeforeEach
//  void init(@Mock Court court) {
//    userService = new DefaultUserService(userRepository, settingRepository, mailClient);
//    Mockito.lenient().when(settingRepository.getUserMinAge()).thenReturn(10);
//    when(settingRepository.getUserNameMinLength()).thenReturn(4);
//    Mockito.lenient().when(userRepository.isUsernameAlreadyExists(any(String.class))).thenReturn(false);
//  }

  @Test
  void getCourtId() {
    when(court.getId()).thenReturn((long)5);
    assertEquals(user.getCourtId(),5);
  }

  @Test
  void getCourt() {
  }

  @Test
  void setCourt() {
  }

//  @Test
//  void givenValidUser_whenSaveUser_thenSucceed(@Mock MailClient mailClient) {
//    // Given
//    user = new User("Jerry", 12);
//    when(userRepository.insert(any(User.class))).then(new Answer<User>() {
//      int sequence = 1;
//      @Override
//      public User answer(InvocationOnMock invocation) throws Throwable {
//        User user = (User) invocation.getArgument(0);
//        user.setId(sequence++);
//        return user;
//      }
//    });
//    userService = new DefaultUserService(userRepository, settingRepository, mailClient);
//    // When
//    User insertedUser = userService.register(user);
//    // Then
//    verify(userRepository).insert(user);
//    Assertions.assertNotNull(user.getId());
//    verify(mailClient).sendUserRegistrationMail(insertedUser);
//  }
}