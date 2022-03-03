package ivanov.leonid.botdbdatareader;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    private Dbconnector mydb;
    boolean stateConnection = false;
    String currentDB;

    @FXML
    private Label welcomeText, logview;
    @FXML
    Button dbconnect, selecttables, selectdatabtn;

    @FXML
    TableView tableView;

    @FXML
    ListView resultview;

    @FXML
    ChoiceBox dbselector, choiseselect;


    @FXML
    protected void onHelloButtonClick() throws SQLException {
        String dbaddress = (String) dbselector.getValue();
        if(!stateConnection) {
            welcomeText.setText("Устанавливаем соединение с БД!");
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
            resultview.getItems().clear();
            }
        }
        //String sq = mydb.doselect("SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES;");
        //String sq = mydb.doselect("SHOW TABLES;");
        //logview.setText(sq);

    @FXML
    public void getAllTablesFromDB(ActionEvent actionEvent) throws SQLException {
        if(mydb != null){
            //System.out.println(mydb.doselect("SHOW TABLES;"));
            ArrayList<String> result = mydb.doselect("SHOW TABLES;");
            String tmp = result.get(0);
            System.out.println(tmp);
            Integer columncount = Integer.valueOf(tmp); //Получаем количество столбцов в ответе
            ArrayList<String> res = new ArrayList<>(); //массив для вывода в интерфейс
            for(int i = 1+columncount; i< result.size(); ){
                res.add(result.get(i));
                i = i + columncount;
            }

            choiseselect.setItems(FXCollections.observableArrayList(res));
            choiseselect.setDisable(false);
            selectdatabtn.setDisable(false);
        }else{
            logview.setText("Нет подключения к базе данных!");
            choiseselect.setDisable(true);
            selectdatabtn.setDisable(true);
        }
    }

    public void selectAllDataFromtable(ActionEvent actionEvent) throws SQLException {
        if(choiseselect.getValue()!=null) {
            logview.setText("Представлены данные из таблицы: " + (String) choiseselect.getValue());
            List<String> res = new ArrayList<String>();
            res = mydb.doselect("SELECT * FROM " + (String) choiseselect.getValue() + ";");
            //System.out.println(res);
            ObservableList<String> obslist = FXCollections.observableArrayList(res);
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

    }

    public void delSelectRecord(ActionEvent actionEvent) {
        logview.setText("Удалить выбранную запись из БД. Пока не реализовано");
    }
}

//Добавить фильтрацию по дате выбираемых данных из базы, возможность удаления записей из базы
//обрабатывать данные из запроса удобнее по полям