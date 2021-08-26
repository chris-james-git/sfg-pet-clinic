package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.repositories.OwnerRepository;
import guru.springframework.sfgpetclinic.repositories.PetRepository;
import guru.springframework.sfgpetclinic.repositories.PetTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OwnerSDJpaServiceTest {

    private static final Long TEST_ID = 1L;
    private static final String TEST_LAST_NAME = "Smith";
    @Mock
    private OwnerRepository ownerRepository;

    @Mock
    private PetRepository petRepository;

    @Mock
    private PetTypeRepository petTypeRepository;

    @InjectMocks
    private OwnerSDJpaService service;

    private Owner returnOwner;

    @BeforeEach
    void setUp() {
        returnOwner = Owner.builder().id(TEST_ID).lastName(TEST_LAST_NAME).build();
    }

    @Test
    void findAll() {
        Set<Owner> returnOwnerSet = Set.of(
                returnOwner,
                Owner.builder().id(2L).build());

        when(ownerRepository.findAll()).thenReturn(returnOwnerSet);

        Set<Owner> owners = service.findAll();

        assertNotNull(owners);
        assertEquals(2, owners.size());
    }

    @Test
    void findById() {
        when(ownerRepository.findById(TEST_ID)).thenReturn(Optional.of(returnOwner));

        Owner owner = service.findById(TEST_ID);

        assertNotNull(owner);
    }

    @Test
    void findByIdNotFound() {
        when(ownerRepository.findById(TEST_ID)).thenReturn(Optional.empty());

        Owner owner = service.findById(TEST_ID);

        assertNull(owner);
    }

    @Test
    void save() {
        Owner ownerToSave = Owner.builder().id(TEST_ID).build();

        when(ownerRepository.save(any())).thenReturn(returnOwner);

        Owner savedOwner = service.save(ownerToSave);

        assertEquals(returnOwner, savedOwner);
    }

    @Test
    void delete() {
        service.delete(returnOwner);

        verify(ownerRepository).delete(returnOwner);
    }

    @Test
    void deleteById() {
        service.deleteById(TEST_ID);

        verify(ownerRepository).deleteById(TEST_ID);
    }

    @Test
    void findByLastName() {
        when(ownerRepository.findByLastName(TEST_LAST_NAME)).thenReturn(Optional.of(returnOwner));

        Owner smith = service.findByLastName(TEST_LAST_NAME);

        assertEquals(TEST_LAST_NAME, smith.getLastName());
    }
}