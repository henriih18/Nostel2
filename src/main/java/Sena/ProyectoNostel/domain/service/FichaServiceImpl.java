package Sena.ProyectoNostel.domain.service;

import Sena.ProyectoNostel.domain.dto.FichaDTO;
import Sena.ProyectoNostel.domain.repository.FichaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FichaServiceImpl implements FichaService {
    @Autowired
    private FichaRepository fichaRepository;

    @Autowired
    private FichaService fichaService;






}
