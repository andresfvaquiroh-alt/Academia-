package org.example.academiaa.app;

import org.example.academiaa.modelo.Academia;
import org.example.academiaa.service.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Punto de entrada de la aplicación JavaFX.
 * <p>
 * Arquitectura <b>MVC</b>:
 * <ul>
 *   <li><b>Modelo</b>: paquetes {@code model} (entidades), {@code repository}
 *       (persistencia) y {@code service} (reglas de negocio).</li>
 *   <li><b>Vista</b>: archivos FXML en {@code resources/.../view}, cargados
 *       aquí y por cada controlador mediante {@code fx:include}.</li>
 *   <li><b>Controlador</b>: clases en el paquete {@code controller}, que
 *       traducen eventos de la interfaz en llamadas a los servicios y
 *       actualizan la vista con el resultado, sin contener lógica de negocio.</li>
 * </ul>
 * Esta clase actúa además como <i>composition root</i>: obtiene la instancia
 * única de  Academia (Singleton) y desde sus repositorios construye
 * los servicios que luego inyecta a los controladores (DIP).
 */
public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        Academia academia = Academia.obtenerInstancia();
        DatosDemo.cargarSiVacio(academia);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/academiaa/view/main-view.fxml"));
        Parent root = loader.load();

        stage.setTitle("LenguajeCafetero - Sistema de Gestión Académica");
        stage.setScene(new Scene(root, 1024, 680));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
