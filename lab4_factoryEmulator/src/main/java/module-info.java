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
    //requires com.almasb.fxgl.all;

    opens ru.nsu.ccfit.malinovskii to javafx.fxml;
    exports ru.nsu. ccfit.malinovskii to javafx.graphics;
    exports ru.nsu.ccfit.malinovskii.factory.Emulator;
    opens ru.nsu.ccfit.malinovskii.factory.Emulator to javafx.fxml;
}