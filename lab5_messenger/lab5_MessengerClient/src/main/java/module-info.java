module ru.nsu.ccfit.malinovskii {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires org.kordamp.ikonli.fontawesome;
    //requires com.almasb.fxgl.all;

    opens ru.nsu.ccfit.malinovskii to javafx.fxml;
    exports ru.nsu. ccfit.malinovskii to javafx.graphics;
    exports ru.nsu.ccfit.malinovskii.Client;
    opens ru.nsu.ccfit.malinovskii.Client to javafx.fxml;
    exports ru.nsu.ccfit.malinovskii.MessageTypes to javafx.graphics;
    opens ru.nsu.ccfit.malinovskii.MessageTypes to javafx.fxml;
}