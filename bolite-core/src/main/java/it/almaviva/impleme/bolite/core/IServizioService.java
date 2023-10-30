package it.almaviva.impleme.bolite.core;


import it.almaviva.impleme.bolite.domain.dto.NewServizioDTO;
import it.almaviva.impleme.bolite.domain.dto.ServizioDTO;

import java.util.List;

public interface IServizioService {

    List<ServizioDTO> getServizi();

    ServizioDTO createServizio(NewServizioDTO body);

    ServizioDTO updateServizio(Integer id, ServizioDTO body);

    void deleteServizio(Integer id);
}
