package com.example.textfinder;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.io.File;
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.ResourceBundle;
import java.io.BufferedReader;
import java.io.FileReader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;


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

    private Indizador indizador = new Indizador();
    private String textoParseado = "";

    @FXML
    private TextField TextFieldBuscar;

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
        // Obtener el índice del elemento seleccionado
        int indice = listview_biblioteca.getSelectionModel().getSelectedIndex();
        String nombreFile = listview_biblioteca.getSelectionModel().getSelectedItem();
        System.out.println("Documento eliminado: " + nombreFile);
        // Si se ha seleccionado un elemento, actualizarlo
        if (indice >= 0) {
            // Eliminar el archivo de la lista
            File archivoSeleccionado = lista_archivos.get(nombreFile);
            lista_archivos.remove(archivoSeleccionado);
            // Conseguir el path del archivo
            String path = archivoSeleccionado.getAbsolutePath();
            File file_actualizado = new File(path);
            lista_archivos.add(file_actualizado);

        }
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
    public void btn_Buscar_Archivos(ActionEvent actionEvent) {
        String palabraBuscar = TextFieldBuscar.getText().trim();
        // Limpiar la lista de resultados
        listview_results.getItems().clear();
        // Iterar sobre cada archivo y buscar la palabra
        for (File archivo : lista_archivos) {
            if (buscarPalabraEnArchivo(palabraBuscar, archivo)) {
                listview_results.getItems().add(archivo.getName());
            }
        }
    }
    private boolean buscarPalabraEnArchivo(String palabra, File archivo) {
        String nombreArchivo = archivo.getName();
        String extension = nombreArchivo.substring(nombreArchivo.lastIndexOf(".") + 1);

        if (extension.equalsIgnoreCase("docx")) {
            try (FileInputStream fis = new FileInputStream(archivo)) {
                XWPFDocument document = new XWPFDocument(fis);
                List<XWPFParagraph> paragraphs = document.getParagraphs();

                for (XWPFParagraph paragraph : paragraphs) {
                    String texto = paragraph.getText();
                    if (texto != null && texto.contains(palabra)) {
                        return true;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (extension.equalsIgnoreCase("txt")) {
            try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
                String linea;
                while ((linea = br.readLine()) != null) {
                    if (linea.contains(palabra)) {
                        return true;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (extension.equalsIgnoreCase("pdf")) {
            try (PDDocument document = PDDocument.load(archivo)) {
                PDFTextStripper pdfStripper = new PDFTextStripper();
                String texto = pdfStripper.getText(document);
                if (texto != null && texto.contains(palabra)) {
                    return true;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

}