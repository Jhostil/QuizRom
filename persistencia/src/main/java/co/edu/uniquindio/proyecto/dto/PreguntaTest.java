package co.edu.uniquindio.proyecto.dto;

import lombok.*;

//Clase usada para contener los datos de una pregunta del test sin  incluir las respuestas.
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class PreguntaTest {

    @EqualsAndHashCode.Include
    private Integer id;
    private String pregunta;
    private String descripcion;

}
