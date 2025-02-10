package Sena.ProyectoNostel.persistence.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public enum EstadoPlanMejoramiento {
    Pendiente,
    /*@JsonFormat(shape = JsonFormat.Shape.STRING)
            @JsonProperty("En Progreso")*/
    En_Progreso,
    Completado

}
