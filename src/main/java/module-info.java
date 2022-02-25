module ivanov.leonid.botdbdatareader {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens ivanov.leonid.botdbdatareader to javafx.fxml;
    exports ivanov.leonid.botdbdatareader;
}