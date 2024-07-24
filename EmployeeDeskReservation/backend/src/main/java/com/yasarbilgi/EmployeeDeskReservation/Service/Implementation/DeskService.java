package com.yasarbilgi.EmployeeDeskReservation.Service.Implementation;

import com.yasarbilgi.EmployeeDeskReservation.DTO.DeskDTO;
import com.yasarbilgi.EmployeeDeskReservation.Entity.Desk;
import com.yasarbilgi.EmployeeDeskReservation.Exception.ResourceNotFoundException;
import com.yasarbilgi.EmployeeDeskReservation.Mapper.DeskMapper;
import com.yasarbilgi.EmployeeDeskReservation.Repository.DeskRepository;
import com.yasarbilgi.EmployeeDeskReservation.Service.Interface.IDeskService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DeskService implements IDeskService {

    private final DeskRepository deskRepository;

    @Override
    public DeskDTO createDesk(DeskDTO deskDTO) {
        Desk desk = DeskMapper.INSTANCE.mapToDesk(deskDTO);
        Desk savedDesk = deskRepository.save(desk);
        return DeskMapper.INSTANCE.mapToDeskDTO(savedDesk);
    }

    @Override
    public DeskDTO getDeskById(Long deskId) {
        Desk desk = deskRepository.findById(deskId)
                .orElseThrow(() -> new ResourceNotFoundException("Desk not found with id: " + deskId));
        return DeskMapper.INSTANCE.mapToDeskDTO(desk);
    }

    @Override
    public List<DeskDTO> getAllDesks() {
        return deskRepository.findAll().stream()
                .map(DeskMapper.INSTANCE::mapToDeskDTO)
                .collect(Collectors.toList());
    }

    @Override
    public DeskDTO updateDesk(Long deskId, DeskDTO updatedDeskDTO) {
        Desk desk = deskRepository.findById(deskId)
                .orElseThrow(() -> new ResourceNotFoundException("Desk not found with id: " + deskId));
        desk.setDeskNo(updatedDeskDTO.getDeskNo());
        desk.setRoom(updatedDeskDTO.getRoom());
        desk.getUnavailableDates().addAll(updatedDeskDTO.getUnavailableDates());
        Desk updatedDesk = deskRepository.save(desk);
        return DeskMapper.INSTANCE.mapToDeskDTO(updatedDesk);
    }

    @Override
    public void deleteDesk(Long deskId) {
        Desk desk = deskRepository.findById(deskId)
                .orElseThrow(() -> new ResourceNotFoundException("Desk not found with id: " + deskId));
        deskRepository.delete(desk);
    }
}