package com.example.textfinder;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class Interface_Controller implements Initializable {

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
        FileChooser.ExtensionFilter txt = new FileChooser.ExtensionFilter("Text Files (*.txt)", "*.txt");
        FileChooser.ExtensionFilter pdf = new FileChooser.ExtensionFilter("PDF Files (*.pdf)", "*.pdf");
        FileChooser.ExtensionFilter docx = new FileChooser.ExtensionFilter("Word Documents (*.docx)", "*.docx");
        FileChooser fileChooser = new FileChooser();

        // Agregar los filtros al fileChooser
        fileChooser.getExtensionFilters().addAll(txt, pdf, docx);

        // Mostrar el diálogo para seleccionar un archivo
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null){
            listview_biblioteca.getItems().add(selectedFile.getName());
        }

    }

    public void btn_actualizar_doc(ActionEvent actionEvent) {
    }

    public void btn_eliminar_doc(ActionEvent actionEvent) {
        // Obtener el índice del elemento seleccionado
        int selectedIndex = listview_biblioteca.getSelectionModel().getSelectedIndex();

        // Si se ha seleccionado un elemento, eliminarlo
        if (selectedIndex >= 0) {
            listview_biblioteca.getItems().remove(selectedIndex);
        }
    }

    public void btn_anadir_carpeta(ActionEvent actionEvent) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Seleccionar carpeta");

        // Mostrar el diálogo para seleccionar una carpeta
        File selectedDirectory = directoryChooser.showDialog(null);

        if (selectedDirectory != null) {
            // Agregar la ruta de la carpeta al ListView
            listview_biblioteca.getItems().add("Carpeta: " + selectedDirectory.getName());
        }
    }
}