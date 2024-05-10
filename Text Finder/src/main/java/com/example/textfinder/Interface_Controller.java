package com.example.textfinder;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class Interface_Controller implements Initializable {

    Indizador indizarFile =  new Indizador();

    LinkedListLibrary<File> lista_archivos = new LinkedListLibrary<>();

    FileChooser.ExtensionFilter txt = new FileChooser.ExtensionFilter("Text Files (*.txt)", "*.txt");
    FileChooser.ExtensionFilter pdf = new FileChooser.ExtensionFilter("PDF Files (*.pdf)", "*.pdf");
    FileChooser.ExtensionFilter docx = new FileChooser.ExtensionFilter("Word Documents (*.docx)", "*.docx");

    @FXML
    private ListView<String> listview_biblioteca;

    @FXML
    private ListView<String> listview_results;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listview_biblioteca.setItems(FXCollections.observableArrayList());
        listview_results.setItems(FXCollections.observableArrayList());
    }

    public void btn_anadir_doc(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(txt, pdf, docx); //añade los filtros para escoger archivos
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null){
            lista_archivos.add(selectedFile);
            listview_biblioteca.getItems().add(selectedFile.getName());
        }
    }

    public void btn_actualizar_doc(ActionEvent actionEvent) {
    }

    public void btn_eliminar_doc(ActionEvent actionEvent) {
        // Obtener el índice del elemento seleccionado
        int selectedIndex = listview_biblioteca.getSelectionModel().getSelectedIndex();
        String nombreFile = listview_biblioteca.getSelectionModel().getSelectedItem();
        System.out.println("Documento eliminado: " + nombreFile);
        // Si se ha seleccionado un elemento, eliminarlo
        if (selectedIndex >= 0) {
            // Eliminar el archivo de la lista
            File archivoSeleccionado = lista_archivos.get(nombreFile);
            lista_archivos.remove(archivoSeleccionado);
            // Eliminar el nombre del archivo del ListView
            listview_biblioteca.getItems().remove(selectedIndex);
        }
    }

    public void btn_anadir_carpeta(ActionEvent actionEvent) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Seleccionar carpeta");
        File selectedDirectory = directoryChooser.showDialog(null); //muestra el explorador
        if (selectedDirectory != null) {
            // Obtener la lista de archivos dentro de la carpeta seleccionada
            File[] files = selectedDirectory.listFiles();
            if (files != null) {
                // Agregar los nombres de los archivos al ListView
                for (File file : files) {
                    if (file.isFile() && (file.getName().toLowerCase().endsWith(".docx") ||
                            file.getName().toLowerCase().endsWith(".pdf") ||
                            file.getName().toLowerCase().endsWith(".txt"))) {
                        lista_archivos.add(file);
                        listview_biblioteca.getItems().add(file.getName());
                    }
                }
                System.out.println("carpeta añadida");
            }
        }
    }

    public void btn_IndizarArchivos(ActionEvent actionEvent) {
        for (File archivo : lista_archivos) {
            indizarFile.indizacion(archivo);
        }
    }
}
