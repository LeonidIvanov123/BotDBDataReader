package ivanov.leonid.botdbdatareader;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class HelloController implements Initializable {

    private Dbconnector mydb;
    boolean stateConnection = false;
    String currentDB;
    String curTable; //в переменную надо бы писать текущую выведенную на экран таблицу
    String version;
    static SettingsConnection myConnection;
   // private ModuleLayer.Controller children;

    @FXML
    private Label welcomeText, logview;
    @FXML
    Button dbconnect, selecttables, selectdatabtn;
    @FXML
    CheckBox checkDateForSelect;
    @FXML
    ListView resultview;
    @FXML
    DatePicker dateStartSelect, dateStopSelect;
    @FXML
    ChoiceBox dbselector, choiseselect;
    @FXML
    TextField fieldNewDB;

    List<String> querryresult = new ArrayList<String>();

    @FXML
    protected void onHelloButtonClick() throws SQLException {
        String dbaddress = (String) dbselector.getValue();
        if(!stateConnection) {
            //welcomeText.setText("Устанавливаем соединение с БД!");
            if (dbselector.getValue() == null) {
                dbaddress = "127.0.0.1:3306";
            } else {
                dbaddress = (String) dbselector.getValue();
            }
            mydb = new Dbconnector(dbaddress);
            boolean stateCon = mydb.initConnection();
            if (stateCon) {
                currentDB = dbaddress;
                logview.setText("Подключение к БД " + dbaddress + " успешно!");
                dbconnect.setText("Disconnect");
                stateConnection = true;
                selecttables.setDisable(false);
                welcomeText.setText(version + "             [Текущее поключение к БД: " + dbaddress + "]");
               // myConnection.setDBAddress(dbaddress);
            } else {
                logview.setText("Не удалось подключиться к БД");
                mydb = null;
            }
        }
        else {
            logview.setText( "Соединение с БД " + currentDB + " разорвано!");
            mydb.closeConnection();
            dbconnect.setText("Connect to db");
            stateConnection = false;
            currentDB = "";
            selecttables.setDisable(true);
            choiseselect.setDisable(true);
            selectdatabtn.setDisable(true);
            checkDateForSelect.setSelected(false);
            checkDateForSelect.setDisable(true);
            dateStartSelect.setDisable(true);
            dateStopSelect.setDisable(true);
            resultview.getItems().clear();
            welcomeText.setText(version);
            }
        }
        //String sq = mydb.doselect("SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES;");
        //String sq = mydb.doselect("SHOW TABLES;");
        //logview.setText(sq);

    @FXML
    public void getAllTablesFromDB(ActionEvent actionEvent) throws SQLException {
        if(mydb != null){
            //System.out.println(mydb.doselect("SHOW TABLES;"));
            //ArrayList<String> result = mydb.doselect("SHOW TABLES;");
            querryresult = mydb.doselect("SHOW TABLES;");
            Integer columncount = Integer.valueOf(querryresult.get(0)); //Получаем количество столбцов в ответе
            ArrayList<String> res = new ArrayList<>(); //массив для вывода в интерфейс
            for(int i = 1+columncount; i< querryresult.size(); ){
                res.add(querryresult.get(i));
                i = i + columncount;
            }

            choiseselect.setItems(FXCollections.observableArrayList(res));
            choiseselect.setDisable(false);
            selectdatabtn.setDisable(false);
            checkDateForSelect.setDisable(false);
            dateStartSelect.setDisable(false);
            dateStopSelect.setDisable(false);
        }else{
            logview.setText("Нет подключения к базе данных!");
            choiseselect.setDisable(true);
            selectdatabtn.setDisable(true);
            checkDateForSelect.setSelected(false);
            checkDateForSelect.setDisable(true);
            dateStartSelect.setDisable(true);
            dateStopSelect.setDisable(true);
        }
    }

    public void selectAllDataFromtable() throws SQLException {
        String dateStart, dateEnd;
        if(choiseselect.getValue()!=null) {
            logview.setText("Представлены данные из таблицы: " + (String) choiseselect.getValue());
            if(!checkDateForSelect.isSelected()) {
                querryresult = mydb.doselectNew("SELECT * FROM " + (String) choiseselect.getValue() + ";");
                System.out.println(dateStartSelect.getValue());
            }else {
                querryresult = mydb.doselectNew("SELECT * FROM " + (String) choiseselect.getValue() + "WHERE (dateMsg > ?);", dateStartSelect.getValue().toString());
            }
            Integer columncount = Integer.valueOf(querryresult.get(0)); //Получаем количество столбцов в ответе
            ArrayList<String> dataforlistview = new ArrayList<>(); //массив для вывода в интерфейс
            for(int i = 1+columncount; i< querryresult.size(); ){
                String temp = "";
                for(int j = i; j < (i+ columncount); j++) {
                    temp = temp + " " + querryresult.get(j);
                }
                //System.out.println(temp); //все данные из запроса
                i = i + columncount;
                dataforlistview.add(temp);
            }

            ObservableList<String> obslist = FXCollections.observableArrayList(dataforlistview);
            resultview.setItems(obslist);
        }else{
            logview.setText("Выберите таблицу из БД");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dbselector.setItems(FXCollections.observableArrayList("localhost:3306", "127.0.0.1:3306", "127.0.0.1:50770"));
        choiseselect.setDisable(true);
        selecttables.setDisable(true);
        selectdatabtn.setDisable(true);
        checkDateForSelect.setDisable(true);
        dateStartSelect.setDisable(true);
        dateStopSelect.setDisable(true);
        version = welcomeText.getText();
    }

    public void delSelectRecord(ActionEvent actionEvent) throws SQLException {
        int numofstring = resultview.getSelectionModel().getSelectedIndex();
        String sqlrequest = "";
        if(numofstring != -1) {
            int colcount = Integer.parseInt(querryresult.get(0));
            sqlrequest = "DELETE FROM " + (String) choiseselect.getValue() + " WHERE "
                    + querryresult.get(1) + "=?;";
                    //Если удалять только по первому столбцу

            if(mydb.dodelite(sqlrequest, Integer.valueOf(querryresult.get(1+ colcount + numofstring* colcount)))){
                logview.setText("Запрос на удаление выполнен.");
                selectAllDataFromtable();//рефреш окна с записями
            }else {
                logview.setText("Возникла ошибка. Удалить запись не удалось.");
            }
        }
    }

    public void addNewDBinCBox(ActionEvent actionEvent) {
        logview.setText("Добавить БД можно через интерфейс 'Opn setting'");
        /*
        if(fieldNewDB.getText() != ""){
            ObservableList olist = dbselector.getItems();
            olist.add(fieldNewDB.getText());
            dbselector.setItems(FXCollections.observableArrayList(olist));
            logview.setText("В список добавлена база: " + fieldNewDB.getText());
            fieldNewDB.clear();
        }else {
            logview.setText("Введите адрес вашей БД в формате 'ip:port'");
        }
         */
    }

    public void openSettingWindow(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlSetting = new FXMLLoader(HelloApplication.class.getResource("setting-view.fxml"));
        Scene scene = new Scene(fxmlSetting.load(), 350, 200);
        Stage s = new Stage();
        s.setTitle("Settings: BotDatareader:1.1");
        s.setScene(scene);

        //ModuleLayer.Controller controller = fxmlSetting.getController();
        //controller.initData(myConnection);


        s.show();
        //обновлять список когда s закроется
        //Node n = fxmlSetting.getController();
        //children = fxmlSetting.getController();


    }

    public void updateChoiceBDBox() throws IOException {
        List<SettingsConnection> connectionList = new ArrayList<>();
        File f = new File("./filesetting");
        if(f.exists()) {
            BufferedReader br = new BufferedReader(new FileReader(f));

            String tmp = "";
            while ((tmp = br.readLine()) != null){
                SettingsConnection sc = new SettingsConnection(tmp);
                connectionList.add(sc);
            }

            logview.setText("info about DB from file: " + f.getName());
        }
        dbselector.getItems().clear();
      /*  Iterator settinglistiterator = connectionList.iterator();
        for(SettingsConnection sc : connectionList){
            dbselector.getItems().add(sc.getDBAddress());
        }
        */
        dbselector.setItems(FXCollections.observableArrayList(connectionList.stream().map(SettingsConnection::getDBAddress).collect(toList())));

        //dbselector.getItems().containsAll(connectionList);
        //dbselector.setItems(FXCollections.observableArrayList("localhost6", "127.0.0.1:3306", "127.0.0.1:50770"));
    }
}