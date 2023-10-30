package it.almaviva.impleme.bolite.api.controllers;


import it.almaviva.impleme.bolite.core.IServizioService;
import it.almaviva.impleme.bolite.domain.dto.NewServizioDTO;
import it.almaviva.impleme.bolite.domain.dto.ServizioDTO;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class ServizioController implements IServizioController{

    private final IServizioService iServizioService;

    public ServizioController(IServizioService iServizioService) {
        this.iServizioService = iServizioService;
    }

    @Override
    public List<ServizioDTO> getServizi() {
        return iServizioService.getServizi();
    }

    @Override
    @RolesAllowed({"SUPER_AMMINISTRATORE","AMMINISTRATORE_SISTEMA"})
    public ServizioDTO createServizio(@Valid NewServizioDTO body) {
        return iServizioService.createServizio(body);
    }

    @Override
   @RolesAllowed({"SUPER_AMMINISTRATORE","AMMINISTRATORE_SISTEMA"})
    public void deleteServizio(Integer idServizio) {
            iServizioService.deleteServizio(idServizio);
    }

    @Override
    @RolesAllowed({"SUPER_AMMINISTRATORE","AMMINISTRATORE_SISTEMA"})
    public ServizioDTO updateServizio(Integer idServizio, @Valid ServizioDTO body) {
        return iServizioService.updateServizio(idServizio, body);
    }


}
