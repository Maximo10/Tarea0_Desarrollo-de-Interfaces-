package com.example.demo;

import com.mongodb.client.MongoCollection;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class HelloController {

    // COMBOBOX
    @FXML
    private ComboBox<String> comboArchivo;

    // PAGINACIÓN
    @FXML
    private Button btnAnterior;
    @FXML
    private Button btnSiguiente;
    @FXML
    private Label lblPagina;
    @FXML
    private Button btnBorrar;

    // DATOS DEL EMPLEADO
    @FXML
    private Label lblNombre;
    @FXML
    private Label lblEdad;
    @FXML
    private Label lblPuesto;
    @FXML
    private Label lblDepartamento;



    // Lista de empleados
    private List<Empleado> empleados = new ArrayList<>();
    private int indiceActual = 0;

    @FXML
    public void initialize() {
        comboArchivo.getItems().addAll("Json", "Txt", "MySql", "MongoDB");

        cargarEmpleadosDesdeMongo();

        if (!empleados.isEmpty()) {
            mostrarEmpleado(indiceActual);
        } else {
            lblNombre.setText("No hay empleados");
            lblEdad.setText("-");
            lblPuesto.setText("-");
            lblDepartamento.setText("-");
            lblPagina.setText("Página 0 de 0");
        }

        btnAnterior.setOnAction(event -> {
            if (indiceActual > 0) {
                indiceActual--;
                mostrarEmpleado(indiceActual);
            }
        });

        btnSiguiente.setOnAction(event -> {
            if (indiceActual < empleados.size() - 1) {
                indiceActual++;
                mostrarEmpleado(indiceActual);
            }
        });

        btnBorrar.setOnAction(event -> eliminarEmpleado());
    }

    private void cargarEmpleadosDesdeMongo() {
        try {
            empleados.clear();
            indiceActual = 0;
            MongoCollection<Document> collection = Controller_Mongo.getDatabase().getCollection("Empleado");

            for (Document doc : collection.find()) {
                String nombre = doc.getString("nombre");
                String apellidos = doc.getString("apellidos");
                int edad = doc.getInteger("edad", 0);
                String departamento = doc.getString("departamento");
                String posicion = doc.getString("posicion");

                empleados.add(new Empleado(nombre, apellidos, edad, departamento, posicion));
            }

            System.out.println("Se cargaron " + empleados.size() + " empleados desde MongoDB.");
        } catch (Exception e) {
            System.out.println("Error al conectar con MongoDB: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void mostrarEmpleado(int indice) {
        Empleado e = empleados.get(indice);
        lblNombre.setText(e.getNombre() + " " + e.getApellidos());
        lblEdad.setText(e.getEdad() + " años");
        lblPuesto.setText(e.getPuesto()); // aquí mostrará la posición
        lblDepartamento.setText(e.getDepartamento());
        lblPagina.setText("Página " + (indice + 1) + " de " + empleados.size());
    }

    private void eliminarEmpleado() {
        if (empleados.isEmpty()) return;

        Empleado e = empleados.get(indiceActual);

        try {
            // Eliminar de MongoDB
            MongoCollection<Document> collection = Controller_Mongo.getDatabase().getCollection("Empleado");
            Document filtro = new Document("nombre", e.getNombre())
                    .append("apellidos", e.getApellidos());
            collection.deleteOne(filtro);

            // Eliminar de la lista local
            empleados.remove(indiceActual);

            // Ajustar índiceActual
            if (indiceActual >= empleados.size()) {
                indiceActual = empleados.size() - 1;
            }

            // Mostrar siguiente empleado o limpiar la pantalla
            if (!empleados.isEmpty()) {
                mostrarEmpleado(indiceActual);
            } else {
                lblNombre.setText("No hay empleados");
                lblEdad.setText("-");
                lblPuesto.setText("-");
                lblDepartamento.setText("-");
                lblPagina.setText("Página 0 de 0");
            }

            System.out.println("Empleado eliminado correctamente.");

        } catch (Exception ex) {
            System.out.println("Error al eliminar empleado: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

}
