package co.edu.uniquindio.proyecto.rest;

import co.edu.uniquindio.proyecto.dto.Mensaje;
import co.edu.uniquindio.proyecto.entidades.Profesor;
import co.edu.uniquindio.proyecto.entidades.Usuario;
import co.edu.uniquindio.proyecto.servicios.ProfesorServicio;
import co.edu.uniquindio.proyecto.servicios.UsuarioServicio;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.time.LocalDate;

@RestController
@RequestMapping("api/")
public class InicioRestController {

    @Getter
    @Setter
    private Usuario usuario;

    @Getter
    @Setter
    private LocalDate localDate;

    @Getter @Setter
    private Profesor profesor;

    @Getter @Setter
    private String rol;

    @Autowired
    private UsuarioServicio usuarioServicio;

    @Autowired
    private ProfesorServicio profesorServicio;

    @PostMapping
    public ResponseEntity<?> crearUsuario(@RequestBody Usuario usuario){ 
        if (rol.equals("Estudiante")) {
            try {
                usuario.setFechaNacimiento(localDate.toString());
                usuarioServicio.registrarUsuario(usuario);
                usuario = new Usuario();
                rol = "";
                FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, "Alerta", "Registro exitoso");
                FacesContext.getCurrentInstance().addMessage("msj-bean", fm);
                return ResponseEntity.status(201).body(new Mensaje("El usuario se creó exitosamente"));
            } catch (Exception e) {
                FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Alerta", e.getMessage());
                FacesContext.getCurrentInstance().addMessage("msj-bean", fm);
                return ResponseEntity.status(500).body(new Mensaje(e.getMessage()));
            }
        } else {
            try {
                profesor.setPassword(usuario.getPassword());
                profesor.setApellido(usuario.getApellido());
                profesor.setUsername(usuario.getUsername());
                profesor.setEmail(usuario.getEmail());
                profesor.setFechaNacimiento(localDate.toString());
                profesor.setId(usuario.getId());
                profesor.setNombre(usuario.getNombre());

                profesorServicio.registrarProfesor(profesor);
                usuario = new Usuario();
                profesor = new Profesor();
                rol = "";
                FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, "Alerta", "Registro exitoso");
                FacesContext.getCurrentInstance().addMessage("msj-bean", fm);
                return ResponseEntity.status(201).body(new Mensaje("El usuario se creó exitosamente"));
            } catch (Exception e) {
                FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Alerta", e.getMessage());
                FacesContext.getCurrentInstance().addMessage("msj-bean", fm);
                return ResponseEntity.status(500).body(new Mensaje(e.getMessage()));
            }
        }
    }
}
