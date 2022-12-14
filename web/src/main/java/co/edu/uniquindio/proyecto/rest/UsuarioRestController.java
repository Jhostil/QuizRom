package co.edu.uniquindio.proyecto.rest;

import co.edu.uniquindio.proyecto.dto.Mensaje;
import co.edu.uniquindio.proyecto.entidades.Profesor;
import co.edu.uniquindio.proyecto.entidades.Usuario;
import co.edu.uniquindio.proyecto.servicios.ProfesorServicio;
import co.edu.uniquindio.proyecto.servicios.UsuarioServicio;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("api/usuarios")
public class UsuarioRestController {

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

    @GetMapping
    public List<Usuario> listar() throws ExecutionException, InterruptedException {
        return usuarioServicio.listarUsuarios();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtener(@PathVariable("id") String idUsuario) {
        try {
            Usuario usuario = usuarioServicio.obtenerUsuario(idUsuario);
            return ResponseEntity.status(200).body(usuario);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new Mensaje(e.getMessage()));
        }
    }

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
                return ResponseEntity.status(201).body(new Mensaje("El usuario se cre?? exitosamente"));
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
                return ResponseEntity.status(201).body(new Mensaje("El usuario se cre?? exitosamente"));
            } catch (Exception e) {
                FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Alerta", e.getMessage());
                FacesContext.getCurrentInstance().addMessage("msj-bean", fm);
                return ResponseEntity.status(500).body(new Mensaje(e.getMessage()));
            }
        }
    }

    @PutMapping
    public ResponseEntity<?> actualizar(@RequestBody Usuario usuario) {
        try {
            usuarioServicio.actualizarUsuario(usuario);
            return ResponseEntity.status(200).body(new Mensaje("El usuario se actualiz?? correctamente"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new Mensaje(e.getMessage()));
        }
    }

    @DeleteMapping("/{codigo}")
    public ResponseEntity<?> eliminar (@PathVariable("codigo") String codigo) {
        try {
            usuarioServicio.eliminarUsuario(codigo);
            return ResponseEntity.status(200).body(new Mensaje("El usuario se elimin?? exitosamente"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new Mensaje(e.getMessage()));
        }
    }

   /* @GetMapping("/favoritos/{codigo}")
    public ResponseEntity<?> obtenerFavoritos(@PathVariable("codigo") String codigo){
        try {
            List<Producto> lista = usuarioServicio.listarFavoritos(codigo);
            return ResponseEntity.status(200).body(lista);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new Mensaje("Error al obtener la lista"));
        }
    }*/
}
