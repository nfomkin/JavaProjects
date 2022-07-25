package ru.itmo.nfomkin.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.itmo.nfomkin.repository.OwnerRepository;

@ExtendWith(MockitoExtension.class)
public class OwnerServiceImplTest {

    @Mock
    private OwnerRepository ownerRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    private OwnerService ownerService;

    @BeforeEach
    void setUp() {
        ownerService = new OwnerServiceImpl(ownerRepository, passwordEncoder);
    }

    @Test
    void checkFindById() {
        Long ownerId = 1L;

        ownerService.findById(ownerId);

        Mockito.verify(ownerRepository).findById(Mockito.eq(ownerId));
    }

    @Test
    void checkFindByName() {
        String name = "Nikita";

        ownerService.findByName(name);

        Mockito.verify(ownerRepository).findByName(Mockito.eq(name));
    }
    
    @Test 
    void checkFindByUsername() {
        String username = "nfomkin";

        ownerService.findByName(username);

        Mockito.verify(ownerRepository).findByName(Mockito.eq(username));
    }

    @Test
    void checkFindAll() {
        ownerService.findAll();

        Mockito.verify(ownerRepository).findAll();
    }
}
