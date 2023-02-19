package lk.ijse.dep10.app.controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

import lk.ijse.dep10.app.model.Students;
import lk.ijse.dep10.app.util.Gender;
import lk.ijse.dep10.app.util.StudyDuration;

import java.util.ArrayList;
import java.util.Optional;

public class MainFormController {

    public Label lblGender;
    public TableView<Students> tblStudents;
    @FXML
    private Button btnAdd;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnNewStudent;

    @FXML
    private Button btnRemove;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnSelect;

    @FXML
    private Button btnUnSelect;

    @FXML
    private CheckBox ckbPartTime;

    @FXML
    private ComboBox<String> cmbDegree;

    @FXML
    private Label lblID;

    @FXML
    private ListView<String> lstAvailableModules;

    @FXML
    private ListView<String> lstContacts;

    @FXML
    private ListView<String> lstSelectedModules;

    @FXML
    private RadioButton rdoFemale;

    @FXML
    private RadioButton rdoMale;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtName;

    public void initialize(){
        cmbDegree.getItems().addAll("Computer Science", "Information Technology", "Information Management");
        lstSelectedModules.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        lstAvailableModules.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        txtContact.textProperty().addListener((value, previous, current) -> {
            txtContact.getStyleClass().remove("invalid");
            btnAdd.setDisable(true);
            if (!contactNumberValidation(current)) {
//                txtContact.getStyleClass().add("invalid");
            }else {
                btnAdd.setDisable(false);
            }
            if (current.isBlank()) txtContact.getStyleClass().remove("invalid");
        });

        cmbDegree.getSelectionModel().selectedItemProperty().addListener((value, previous, current) -> {
            lstAvailableModules.setDisable(current==null);
        });

        lstContacts.getSelectionModel().selectedItemProperty().addListener((value, previous, current) -> {
            btnRemove.setDisable(current==null);
        });
        lstAvailableModules.getSelectionModel().selectedItemProperty().addListener((value, previous, current) -> {
            btnSelect.setDisable(current==null);
        });
        lstSelectedModules.getSelectionModel().selectedItemProperty().addListener((value, previous, current) -> {
            btnUnSelect.setDisable(current==null);
        });

        tblStudents.getSelectionModel().selectedItemProperty().addListener((value, previous, current) -> {
        if (!tblStudents.getSelectionModel().isEmpty()) {
            lblID.setText(current.getId());
            lstSelectedModules.getItems().clear();
            txtName.setText(current.getName());
            rdoMale.getToggleGroup().selectToggle((current.getGender() == Gender.MALE) ? rdoMale : rdoFemale);
            lstContacts.getItems().clear();
            lstContacts.getItems().addAll(current.getContacts());
            cmbDegree.setValue(current.getDegree());
            ckbPartTime.setSelected(current.isPartTime() == StudyDuration.PartTime);
            lstSelectedModules.getItems().addAll(current.getModules());
            lstAvailableModules.getItems().clear();
            moduleSelection(current.getDegree());
            lstAvailableModules.getItems().removeAll(current.getModules());
            }
        });

    }

    @FXML
    void btnNewStudentOnAction(ActionEvent event) {

        tblStudents.getSelectionModel().clearSelection();

        txtName.setDisable(false);
        rdoMale.setDisable(false);
        rdoFemale.setDisable(false);
        txtContact.setDisable(false);
        ckbPartTime.setDisable(false);
        btnSave.setDisable(false);
        btnDelete.setDisable(false);


        ObservableList<String> items = cmbDegree.getItems();

        lblID.setText("Generated ID");
        txtName.clear();
        rdoMale.getToggleGroup().selectToggle(null);
        txtContact.clear();
        lstContacts.getItems().clear();
        cmbDegree.getSelectionModel().clearSelection();
        lstAvailableModules.getItems().clear();
        lstSelectedModules.getItems().clear();
        ckbPartTime.setSelected(false);
        txtName.requestFocus();
        cmbDegree.setPromptText("-- Select a Degree --");



    }

    public void txtNameOnKeyReleased(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) rdoMale.requestFocus();
    }

    @FXML
    void btnAddOnAction(ActionEvent event) {
        String contactNumber = txtContact.getText();
        for (String number : new ArrayList<String>(lstContacts.getItems())) {
            if(contactNumber.equals(number)){
                txtContact.getStyleClass().add("invalid");
                return;
            }
        }

        lstContacts.getItems().add(contactNumber);
        txtContact.selectAll();
        txtContact.requestFocus();
        lstSelectedModules.setDisable(false);
    }

    @FXML
    void btnRemoveOnAction(ActionEvent event) {
        lstContacts.getItems().remove(lstContacts.getSelectionModel().getSelectedIndex());
        lstContacts.getSelectionModel().clearSelection();
    }

    public void cmbDegreeOnAction(ActionEvent actionEvent) {
        String selectedItem = cmbDegree.getSelectionModel().getSelectedItem();
        if (selectedItem == null) return;
        lstSelectedModules.getItems().clear();
        lstAvailableModules.getItems().clear();
        if (selectedItem.equals("Computer Science")){
            lstAvailableModules.getItems().addAll("OOP","Python","PHP","Angular","React","Node JS");
        }
        if (selectedItem.equals("Information Technology")){
            lstAvailableModules.getItems().addAll("Java","Python","PHP","Angular","React","Node JS");
        }
        if (selectedItem.equals("Information Management")){
            lstAvailableModules.getItems().addAll("management","Python","PHP","Angular","React","Node JS");
        }
    }

    @FXML
    void btnSelectOnAction(ActionEvent event) {

        lstSelectedModules.getItems().addAll(lstAvailableModules.getSelectionModel().getSelectedItems());
        lstAvailableModules.getItems().remove(lstAvailableModules.getSelectionModel().getSelectedIndex());
        lstAvailableModules.getSelectionModel().clearSelection();
        lstSelectedModules.setDisable(false);
    }

    @FXML
    void btnUnSelectOnAction(ActionEvent event) {
        lstAvailableModules.getItems().addAll(lstSelectedModules.getSelectionModel().getSelectedItems());
        lstSelectedModules.getItems().remove(lstSelectedModules.getSelectionModel().getSelectedIndex());
        lstSelectedModules.getSelectionModel().clearSelection();
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        boolean validate = true;
        txtName.getStyleClass().remove("invalid");
        lblGender.setTextFill(Color.BLACK);
        lstContacts.getStyleClass().remove("invalid");
        cmbDegree.getStyleClass().remove("invalid");
        lstSelectedModules.getStyleClass().remove("invalid");

        if (lstSelectedModules.getItems().size()<3){
            lstSelectedModules.requestFocus();
            lstSelectedModules.getStyleClass().add("invalid");
            validate = false;
        }

        if (cmbDegree.getSelectionModel().getSelectedItem() == null){
            cmbDegree.requestFocus();
            cmbDegree.getStyleClass().add("invalid");
            validate = false;
        }

        if (lstContacts.getItems().isEmpty() || duplicateNumber(lstContacts.getItems())) {
            lstContacts.requestFocus();
            lstContacts.getStyleClass().add("invalid");
            validate = false;
        }

        if (rdoFemale.getToggleGroup().getSelectedToggle()==null) {
            rdoMale.requestFocus();
            lblGender.setTextFill(Color.RED);
            validate = false;
        }

        if (txtName.getText().isBlank()) {
            txtName.getStyleClass().add("invalid");
            txtName.selectAll();
            txtName.requestFocus();
            validate = false;
        }

        int count =0;

        for (Students student : tblStudents.getItems()) {
            if (tblStudents.getSelectionModel().getSelectedIndex()== count++) continue;
            for (String contact : student.getContacts()) {
                for (String item : lstContacts.getItems()) {
                    if (item.equals(contact)){
                        lstContacts.getStyleClass().add("invalid");
                        validate = false;
                    }
                }
            }
        }


        if (validate==false) return;




        Students student = new Students();
        student.setName(txtName.getText().trim());
        student.setGender(rdoMale.isSelected() ? Gender.MALE: Gender.FEMALE);
        student.setContacts(new ArrayList<>(lstContacts.getItems()));
        student.setDegree(cmbDegree.getSelectionModel().getSelectedItem());
        student.setModules( new ArrayList<>(lstSelectedModules.getItems()));
        student.setPartTime(ckbPartTime.isSelected() ? StudyDuration.PartTime : StudyDuration.FullTime);
        student.setId(lblID.getText());


        ObservableList<Students> studentsList = tblStudents.getItems();

        if (!tblStudents.getSelectionModel().isEmpty()){
            studentsList.add(tblStudents.getSelectionModel().getSelectedIndex(), student);
            studentsList.remove(tblStudents.getSelectionModel().getSelectedIndex());
        } else {
            lblID.setText(generateId(cmbDegree.getSelectionModel().getSelectedItem()));
            studentsList.add(student);
            student.setId(lblID.getText());
        }

        tblStudents.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        tblStudents.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblStudents.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("gender"));
        tblStudents.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("contacts"));
        tblStudents.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("degree"));
        tblStudents.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("modules"));
        tblStudents.getColumns().get(6).setCellValueFactory(new PropertyValueFactory<>("partTime"));

//        tblStudents.getSelectionModel().clearSelection();
        btnNewStudent.fire();

    }

    @FXML
    void ckbPartTimeOnAction(ActionEvent event) {

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        Optional<ButtonType> buttonType = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure to delete this student from the list?", ButtonType.YES, ButtonType.NO).showAndWait();
        if (buttonType.isEmpty() || buttonType.get() == ButtonType.NO) return;
        tblStudents.getItems().remove(tblStudents.getSelectionModel().getSelectedIndex());

    }

    @FXML
    void lstAvailableModulesOnKeyReleased(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) btnSelect.fire();
    }

    @FXML
    void lstSelectedModulesOnKeyReleased(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) btnUnSelect.fire();
    }

    @FXML
    void rdoFemaleOnAction(ActionEvent event) {

    }

    @FXML
    void rdoMaleOnAction(ActionEvent event) {

    }

    @FXML
    void txtContactOnAction(ActionEvent event) {

    }

    @FXML
    void txtNameOnAction(ActionEvent event) {

    }

    public void lstContactsOnKeyReleased(KeyEvent keyEvent) {

    }

    @FXML
    void txtContactOnKeyReleased(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) btnAdd.fire();
    }

    private boolean duplicateNumber(ObservableList<String> items) {
        return false;
    }

    private boolean contactNumberValidation(String contactNumber) {

        char[] chars = contactNumber.toCharArray();
        for (char aChar : chars) {
            System.out.println("=====");
            System.out.println(aChar);
        }
        if (contactNumber.length() >3 && chars[3] != '-') {
            txtContact.getStyleClass().add("invalid");
            return false;
        }
        for (char c : chars) {
            System.out.println(c);
            if (contactNumber.length() >3 && chars[3] == c  ) continue;
//            System.out.println(c);
            if (!Character.isDigit(c)) {
                txtContact.getStyleClass().add("invalid");
                return false;
            }
        }
        if (contactNumber.length() >11) txtContact.getStyleClass().add("invalid");;
        if (contactNumber.length() !=11) return false;
        txtContact.getStyleClass().remove("invalid");
        return true;
    }

    private void moduleSelection( String selectedItem ) {
        if (selectedItem.equals("Computer Science")){
            lstAvailableModules.getItems().addAll("OOP","Python","PHP","Angular","React","Node JS");
        }
        if (selectedItem.equals("Information Technology")){
            lstAvailableModules.getItems().addAll("Java","Python","PHP","Angular","React","Node JS");
        }
        if (selectedItem.equals("Information Management")){
            lstAvailableModules.getItems().addAll("management","Python","PHP","Angular","React","Node JS");
        }
    }
    private int csCount=1;
    private int iTCount=1;
    private int iMCount=1;

    public String generateId (String degree){
        if (degree.equals("Computer Science")) {
            return String.format("SC-%03d",csCount++);
        }
        if (degree.equals("Information Technology")) {
            return String.format("IT-%03d",iTCount++);
        }
        if (degree.equals("Information Management")) {
            return String.format("IM-%03d",iMCount++);
        }
        else return "none";
    }

}
