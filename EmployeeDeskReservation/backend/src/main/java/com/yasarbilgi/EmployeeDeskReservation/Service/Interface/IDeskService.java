package com.yasarbilgi.EmployeeDeskReservation.Service.Interface;

import com.yasarbilgi.EmployeeDeskReservation.DTO.DeskDTO;

import java.util.List;

public interface IDeskService {
    DeskDTO createDesk(DeskDTO deskDTO);
    DeskDTO getDeskById(Long deskId);
    List<DeskDTO> getAllDesks();
    DeskDTO updateDesk(Long deskId, DeskDTO updatedDeskDTO);
    void deleteDesk(Long deskId);
}