package gt.edu.umg.programacion2.proyectofinal.inventoryFinal.dto;

import jakarta.validation.constraints.*;

public class ClienteDTO {

    private Long id;
    private String password; // nueva propiedad

    @NotBlank(message = "Nombre completo obligatorio")
    private String nombreCompleto;

    @NotBlank(message = "DPI obligatorio")
    @Size(min = 8, message = "DPI debe tener al menos 8 dígitos")
    private String dpi;

    @NotBlank(message = "Correo obligatorio")
    @Email(message = "Formato de correo inválido")
    private String correo;

    @Pattern(regexp = "\\d{8,15}", message = "Teléfono inválido")
    private String telefono;

    private String direccion;

    @NotBlank(message = "Fecha de nacimiento obligatoria")
    private String fechaNacimiento; // yyyy-MM-dd

    private Boolean activo = true;

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }

    public String getDpi() { return dpi; }
    public void setDpi(String dpi) { this.dpi = dpi; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(String fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
