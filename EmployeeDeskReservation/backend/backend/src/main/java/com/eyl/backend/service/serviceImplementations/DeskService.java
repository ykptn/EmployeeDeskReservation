package com.eyl.backend.service.serviceImplementations;


import com.eyl.backend.dto.DeskDTO;
import com.eyl.backend.entity.Desk;
import com.eyl.backend.exception.ResourceNotFoundException;
import com.eyl.backend.mapper.DeskMapper;
import com.eyl.backend.repository.DeskRepository;
import com.eyl.backend.service.serviceInterfaces.IDeskService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DeskService implements IDeskService {

    private final DeskRepository repo;

    @Override
    public DeskDTO createDesk(DeskDTO deskDTO) {
        Desk desk = DeskMapper.INSTANCE.mapToDesk(deskDTO);
        Desk savedDesk = repo.save(desk);
        return DeskMapper.INSTANCE.mapToDeskDTO(savedDesk);
    }

    @Override
    public DeskDTO getDeskById(Long deskId) {
        Desk desk = repo.findById(deskId)
                .orElseThrow(() -> new ResourceNotFoundException("Desk not found with id: " + deskId));
        return DeskMapper.INSTANCE.mapToDeskDTO(desk);
    }

    @Override
    public List<DeskDTO> getAllDesks() {
        return repo.findAll().stream()
                .map(DeskMapper.INSTANCE::mapToDeskDTO)
                .collect(Collectors.toList());
    }

    @Override
    public DeskDTO updateDesk(Long deskId, DeskDTO updatedDeskDTO) {
        Desk desk = repo.findById(deskId)
                .orElseThrow(() -> new ResourceNotFoundException("Desk not found with id: " + deskId));
        desk.setDeskNo(updatedDeskDTO.getDeskNo());
        desk.setRoom(updatedDeskDTO.getRoom());
        desk.getUnavailableDates().addAll(updatedDeskDTO.getUnavailableDates());
        Desk updatedDesk = repo.save(desk);
        return DeskMapper.INSTANCE.mapToDeskDTO(updatedDesk);
    }

    @Override
    public void deleteDesk(Long deskId) {
        Desk desk = repo.findById(deskId)
                .orElseThrow(() -> new ResourceNotFoundException("Desk not found with id: " + deskId));
        repo.delete(desk);
    }
}
