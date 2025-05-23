package Sena.ProyectoNostel.domain.repository;

import Sena.ProyectoNostel.persistence.crud.PlanMejoramientoCrudRepository;
import Sena.ProyectoNostel.persistence.entity.ActividadComplementaria;
import Sena.ProyectoNostel.persistence.entity.PlanMejoramiento;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PlanMejoramientoRepository extends PlanMejoramientoCrudRepository {
    List<PlanMejoramiento> findByIdAprendiz(Integer idAprendiz);

    @Procedure(procedureName = "AsignarPlanMejoramiento")
    void asignarPlanMejoramiento(
            @Param("p_id_aprendiz") Integer idAprendiz,
            @Param("p_id_instructor") Integer idInstructor,
            @Param("p_estado") String estado,
            @Param("p_acta_number") String actaNumber,
            @Param("p_nombre_comite") String nombreComite,
            @Param("p_ciudad") String ciudad,
            @Param("p_fecha") LocalDate fecha,
            @Param("p_hora_inicio") String horaInicio,
            @Param("p_hora_fin") String horaFin,
            @Param("p_lugar_enlace") String lugarEnlace,
            @Param("p_direccion_regional_centro") String direccionRegionalCentro,
            @Param("p_agenda") String agenda,
            @Param("p_objetivos") String objetivos,
            @Param("p_desarrollo") String desarrollo,
            @Param("p_conclusiones") String conclusiones
    );

    // Método para obtener el último ID insertado (puedes usar una consulta nativa si el procedimiento no lo devuelve)
    @Query(value = "SELECT LAST_INSERT_ID()", nativeQuery = true)
    Integer getLastInsertedId();
}
