package ru.nsu.ccfit.malinovskii.factory.Emulator;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.nsu.ccfit.malinovskii.FactoryObject;
import ru.nsu.ccfit.malinovskii.Main;
import ru.nsu.ccfit.malinovskii.factory.assemblers.MachineAssembler;
import ru.nsu.ccfit.malinovskii.factory.dealerships.CarDealership;
import ru.nsu.ccfit.malinovskii.factory.providers.AccesoriesProvider;
import ru.nsu.ccfit.malinovskii.factory.providers.BodyProvider;
import ru.nsu.ccfit.malinovskii.factory.providers.EngineProvider;
import ru.nsu.ccfit.malinovskii.factory.storages.AccessoriesStorage;
import ru.nsu.ccfit.malinovskii.factory.storages.BodyStorage;
import ru.nsu.ccfit.malinovskii.factory.storages.CarStorage;
import ru.nsu.ccfit.malinovskii.factory.storages.EngineStorage;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.*;

import static java.lang.Thread.sleep;

public class Emulator extends Application {
    AccessoriesStorage accessoriesStorage;
    BodyStorage bodyStorage;
    CarStorage carStorage;
    EngineStorage engineStorage;

    AccesoriesProvider accesoriesProvider;
    BodyProvider bodyProvider;
    EngineProvider engineProvider;

    CarDealership carDealership;

    MachineAssembler machineAssembler;

    ScheduledExecutorService enginesExecutor;
    ScheduledExecutorService bodysExecutor;
    ScheduledExecutorService accesoriesExecutor;
    ScheduledExecutorService workersExecutor;
    ScheduledExecutorService dealersExecutor;


    public Emulator(){
        this.accessoriesStorage = new AccessoriesStorage();
        this.bodyStorage = new BodyStorage();
        this.carStorage = new CarStorage();
        this.engineStorage = new EngineStorage();

        this.accesoriesProvider = new AccesoriesProvider();
        this.bodyProvider = new BodyProvider();
        this.engineProvider = new EngineProvider();

        this.carDealership = new CarDealership();

        this.machineAssembler = new MachineAssembler();
    }

    public void loadConfiguration(String configFile) throws IOException {
        System.out.println(configFile);
        Properties properties = new Properties();
        try (InputStream inputStream = Emulator.class.getResourceAsStream(configFile)) {
            properties.load(inputStream);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        try {
            int accessoryStorageSize = Integer.parseInt(Objects.requireNonNull(properties.getProperty("accessoryStorageSize")));
            int bodyStorageSize = Integer.parseInt(Objects.requireNonNull(properties.getProperty("bodyStorageSize")));
            int carStorageSize = Integer.parseInt(Objects.requireNonNull(properties.getProperty("carStorageSize")));
            int engineStorageSize = Integer.parseInt(Objects.requireNonNull(properties.getProperty("engineStorageSize")));
            int accessorySuppliersCount = Integer.parseInt(Objects.requireNonNull(properties.getProperty("accessorySuppliers")));
            int workersCount = Integer.parseInt(Objects.requireNonNull(properties.getProperty("workers")));
            int dealersCount = Integer.parseInt(Objects.requireNonNull(properties.getProperty("dealers")));
            Boolean logSaleCount = Boolean.getBoolean(Objects.requireNonNull(properties.getProperty("logSale")));

            System.out.println("engineStorageSize: " + engineStorageSize);
            this.accessoriesStorage.initialize(accessoryStorageSize);
            this.bodyStorage.initialize(bodyStorageSize);
            this.carStorage.initialize(carStorageSize);
            this.engineStorage.initialize(engineStorageSize);
            this.accesoriesProvider.initialize(accessorySuppliersCount);
            this.bodyProvider.initialize(1);
            this.engineProvider.initialize(1);
            this.machineAssembler.initialize(workersCount);
            this.carDealership.initialize(dealersCount);
            System.out.println(this.engineStorage.getMaxSize());
        } catch (NullPointerException e) {
            throw new RuntimeException("trash");
        }

    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        Emulator emulator = Main.emulator;

        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = getClass().getResource("/ru.nsu.ccfit.malinovskii/factoryEmulator-view.fxml");
        loader.setLocation(xmlUrl);
        Parent root = loader.load();

        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        EmulatorController emulatorController = loader.getController();

        enginesExecutor = Executors.newScheduledThreadPool(emulator.engineProvider.getSupplierCount());
        bodysExecutor = Executors.newScheduledThreadPool(emulator.bodyProvider.getSupplierCount());
        accesoriesExecutor = Executors.newScheduledThreadPool(emulator.accesoriesProvider.getSupplierCount());
        workersExecutor = Executors.newScheduledThreadPool(emulator.machineAssembler.getWorkersCount());
        dealersExecutor = Executors.newScheduledThreadPool(emulator.carDealership.getDealersCount());


        emulatorController.getEngines(enginesExecutor, emulatorController);
        emulatorController.getBodys(bodysExecutor, emulatorController);
        emulatorController.getAccessories(accesoriesExecutor, emulatorController);
        emulatorController.getMachine(workersExecutor, emulatorController);
        emulatorController.getDealers(workersExecutor, emulatorController);
    }

    @Override
    public void stop() {
        enginesExecutor.shutdownNow();
        bodysExecutor.shutdownNow();
        accesoriesExecutor.shutdownNow();
        workersExecutor.shutdownNow();
        dealersExecutor.shutdownNow();
    }
}
