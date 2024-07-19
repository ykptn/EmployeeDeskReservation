package com.eyl.backend.service.serviceInterfaces;

import com.eyl.backend.dto.DeskDTO;

import java.util.List;

public interface IDeskService {
    DeskDTO createDesk(DeskDTO deskDTO);
    DeskDTO getDeskById(Long deskId);
    List<DeskDTO> getAllDesks();
    DeskDTO updateDesk(Long deskId, DeskDTO updatedDeskDTO);
    void deleteDesk(Long deskId);
}
