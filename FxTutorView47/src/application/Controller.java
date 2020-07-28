package application;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class Controller {
	private boolean editing;	
	@FXML
	private TableView<Person> table;
	@FXML
	private TableColumn<Person, String> name;
	@FXML
	private TableColumn<Person, String> surname;
	@FXML
	private TextField name_tf;
	@FXML
	private TextField surname_tf;
	@FXML
	private TabPane tabPane;

	
	ObservableList<Person> items = FXCollections.observableArrayList(); 
	public Controller() {
		items.addAll(new Person("Степан", "Петров"),new Person("Asya","Ivanova"),new Person("Jhon", "Rambo"));
	}
	
	@FXML
	private void submit() {
		if(editing) {table.getSelectionModel().getSelectedItem().set(name_tf.getText(), surname.getText());
		editing = false;}
		else {items.add(new Person(name_tf.getText(), surname_tf.getText()));}
		name_tf.clear(); surname_tf.clear();
		table.refresh();
	}
	
	@FXML
	private void close() {
		System.exit(0);
	}
	
	@FXML
	public void initialize() {
		table.itemsProperty().setValue(items);		
		table.setRowFactory(param -> {
				TableRow<Person> row = new TableRow<>();
				MenuItem remove = new MenuItem("Remove");
				MenuItem edit = new MenuItem("Edit");
				edit.setOnAction( event -> {
						name_tf.setText(table.getSelectionModel().getSelectedItem().getName());	
						surname_tf.setText(table.getSelectionModel().getSelectedItem().getSurName());
						editing = true;
						});
				remove.setOnAction(event -> items.remove(table.getSelectionModel().getSelectedItem()));				
				ContextMenu menu = new ContextMenu(remove, edit);		
				row.contextMenuProperty().setValue(menu);			
				return row;			
		});
		name.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getName()));
		surname.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getSurName()));
	}
}
