package gt.edu.umg.programacion2.proyectofinal.inventoryFinal.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import gt.edu.umg.programacion2.proyectofinal.inventoryFinal.model.Cliente;

import java.time.LocalDate;
import java.util.regex.Pattern;

@Component
public class ClienteValidator implements Validator {

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    private static final Pattern TELEFONO_PATTERN =
            Pattern.compile("^\\d{8,15}$"); // 8 a 15 dígitos

    @Override
    public boolean supports(Class<?> clazz) {
        return Cliente.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Cliente cliente = (Cliente) target;

        // Nombre completo obligatorio
        if(cliente.getNombreCompleto() == null || cliente.getNombreCompleto().trim().isEmpty()) {
            errors.rejectValue("nombreCompleto", "Nombre obligatorio", "Debe completar el nombre completo");
        }

        // DPI obligatorio y mínimo 8 dígitos
        if(cliente.getDpi() == null || cliente.getDpi().trim().isEmpty()) {
            errors.rejectValue("dpi", "DPI obligatorio", "Debe completar el DPI");
        } else if(cliente.getDpi().length() < 8) {
            errors.rejectValue("dpi", "DPI inválido", "El DPI debe tener al menos 8 dígitos");
        }

        // Correo obligatorio y formato válido
        if(cliente.getCorreo() == null || cliente.getCorreo().trim().isEmpty()) {
            errors.rejectValue("correo", "Correo obligatorio", "Debe completar el correo");
        } else if(!EMAIL_PATTERN.matcher(cliente.getCorreo()).matches()) {
            errors.rejectValue("correo", "Correo inválido", "Formato de correo inválido");
        }

        // Teléfono opcional, pero si se ingresa debe ser numérico y 8 a 15 dígitos
        if(cliente.getTelefono() != null && !cliente.getTelefono().trim().isEmpty()) {
            if(!TELEFONO_PATTERN.matcher(cliente.getTelefono()).matches()) {
                errors.rejectValue("telefono", "Teléfono inválido", "Teléfono debe ser numérico y tener entre 8 y 15 dígitos");
            }
        }

        // Fecha de nacimiento opcional, validar que no sea futura
        LocalDate fecha = cliente.getFechaNacimiento();
        if(fecha != null) {
            if(fecha.isAfter(LocalDate.now())) {
                errors.rejectValue("fechaNacimiento", "Fecha inválida", "La fecha de nacimiento no puede ser futura");
            }
        }

        // Dirección opcional, sin validación específica
    }
}
