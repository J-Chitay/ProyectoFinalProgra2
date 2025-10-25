package gt.edu.umg.programacion2.proyectofinal.inventoryFinal;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordGenerator {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        String plainPassword = "josue"; // <- la contraseña que quieras
        String hashedPassword = encoder.encode(plainPassword);

        System.out.println("Contraseña encriptada: " + hashedPassword);
    }
}
