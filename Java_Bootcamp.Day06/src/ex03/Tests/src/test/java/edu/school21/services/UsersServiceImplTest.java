package edu.school21.services;

import edu.school21.models.User;
import edu.school21.repositories.UsersRepository;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import edu.school21.exceptions.*;

public class UsersServiceImplTest {
    private UsersRepository usersRepository = mock(UsersRepository.class);
    private UsersServiceImpl usersService = new UsersServiceImpl(usersRepository);
    private User userMock = new User(1L, "login1", "1234", false);

    @Test
    public void testSuccess() {
        when(usersRepository.findByLogin("login1")).thenReturn(userMock);
        boolean res = usersService.authenticate("login1", "1234");
        assertTrue(res);
        verify(usersRepository, times(1)).update(userMock);
    }


    @Test
    public void testInvalidLogin() {
        when(usersRepository.findByLogin("login2")).thenThrow(new EntityNotFoundException("User not found!"));
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> usersService.authenticate("login2", "1234"),
                "Expected EntityNotFoundException to be thrown");
        assertEquals("User not found!", exception.getMessage());
        verify(usersRepository, never()).update(userMock);
    }

    @Test
    public void testInvalidPassword() {
        when(usersRepository.findByLogin("login1")).thenReturn(userMock);
        boolean res = usersService.authenticate("login1", "123");
        assertFalse(res);
        verify(usersRepository, never()).update(userMock);
    }
}
