package ru.nsu.ccfit.malinovskii.factory.Emulator;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.event.ActionEvent;
import ru.nsu.ccfit.malinovskii.FactoryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static java.lang.Thread.sleep;
import static ru.nsu.ccfit.malinovskii.Main.emulator;

public class EmulatorController {
    @FXML
    public Label engineStorage;
    @FXML
    public Label bodyStorage;
    @FXML
    public Label accessoriesStorage;
    @FXML
    public Label carStorage;
    @FXML
    public Button exitButton;
    @FXML
    private Label enginePerSecondLabel;
    @FXML
    private Label bodyPerSecondLabel;
    @FXML
    public Label accessoriesPerSecondLabel;
    @FXML
    private Label workersPerSecondLabel;
    @FXML
    private Label dealersPerSecondLabel;
    @FXML
    private Slider enginePerSecondSlider;
    @FXML
    private Slider bodyPerSecondSlider;
    @FXML
    public Slider accessoriesPerSecondSlider;
    @FXML
    private Slider workersPerSecondSlider;
    @FXML
    private Slider dealersPerSecondSlider;

    int message = 0;

    @FXML
    public void getEngines(ScheduledExecutorService enginesExecutor, EmulatorController emulatorController) {
        final int[] presentValue = {0};
        final ScheduledFuture<?>[] futureTask = {null};
        int providerID = (int) Thread.currentThread().threadId();
        final int[] id = {providerID * 1000000};
        String name = "engine";

        if (enginePerSecondSlider != null) {
            enginePerSecondSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
                presentValue[0] = newVal.intValue();

                Platform.runLater(() -> {
                    enginePerSecondLabel.setText(String.valueOf(presentValue[0]));
                });

                if (futureTask[0] != null) {
                    futureTask[0].cancel(true);
                }

                futureTask[0] = enginesExecutor.scheduleAtFixedRate(() -> {
                    FactoryObject engine = new FactoryObject(name, id[0], providerID);
                    synchronized (emulator.engineStorage){
                        if(!emulator.engineStorage.push(engine)){
                            try {
                                emulator.engineStorage.wait();
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }

                        emulatorController.engineStorageUpdate(emulator.engineStorage.getSize(), emulator.engineStorage.getMaxSize());
                        emulator.engineStorage.notifyAll();
                    }
                    ++id[0];
                }, presentValue[0], presentValue[0], TimeUnit.MILLISECONDS);
            });
        } else {
            System.out.println("ALARM! ENGINE SLIDER NOT FOUND!");
        }
    }

    @FXML
    public void getBodys(ScheduledExecutorService bodysExecutor, EmulatorController emulatorController) {
        final int[] presentValue = {0};
        final ScheduledFuture<?>[] futureTask = {null};
        int providerID = (int) Thread.currentThread().threadId();
        final int[] id = {providerID * 1000000};
        String name = "body";

        if (bodyPerSecondSlider != null) {
            bodyPerSecondSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
                presentValue[0] = newVal.intValue();

                Platform.runLater(() -> {
                    bodyPerSecondLabel.setText(String.valueOf(presentValue[0]));
                });

                if (futureTask[0] != null) {
                    futureTask[0].cancel(true);
                }

                futureTask[0] = bodysExecutor.scheduleAtFixedRate(() -> {
                    FactoryObject body = new FactoryObject(name, id[0], providerID);
                    synchronized (emulator.bodyStorage){
                        if (!emulator.bodyStorage.push(body)) {
                            try {
                                emulator.bodyStorage.wait();
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }

                        emulatorController.bodyStorageUpdate(emulator.bodyStorage.getSize(), emulator.bodyStorage.getMaxSize());
                        emulator.bodyStorage.notifyAll();
                    }

                    ++id[0];
                }, presentValue[0], presentValue[0], TimeUnit.MILLISECONDS);
            });
        } else {
            System.out.println("ALARM! BODYS SLIDER NOT FOUND!");
        }
    }

    @FXML
    public void getAccessories(ScheduledExecutorService accessoriesExecutor, EmulatorController emulatorController) {
        final int[] presentValue = {0};
        final List<ScheduledFuture<?>> futureTasks = new ArrayList<>();
        int providerID = (int) Thread.currentThread().threadId();
        final int[] id = new int[1000];
        for (int i = 0; i < 1000; ++i){
            id[i] = i*1000000;
        }
        String name = "accessor";

        if (accessoriesPerSecondSlider != null) {
            accessoriesPerSecondSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
                presentValue[0] = newVal.intValue();

                Platform.runLater(() -> {
                    accessoriesPerSecondLabel.setText(String.valueOf(presentValue[0]));
                });

                for (ScheduledFuture<?> futureTask : futureTasks) {
                    futureTask.cancel(true);
                }
                futureTasks.clear();

                int threadPoolSize = ((ThreadPoolExecutor) accessoriesExecutor).getCorePoolSize();
                for (int i = 0; i < threadPoolSize; i++) {
                    int finalI = i;
                    ScheduledFuture<?> futureTask = accessoriesExecutor.scheduleAtFixedRate(() -> {
                        FactoryObject accessor = new FactoryObject(name, id[finalI], providerID);
                        synchronized (emulator.accessoriesStorage){
                            if (!emulator.accessoriesStorage.push(accessor)){
                                try {
                                    emulator.accessoriesStorage.wait();
                                    emulator.accessoriesStorage.push(accessor);
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                }
                            }

                            emulatorController.accessoriesStorageUpdate(emulator.accessoriesStorage.getSize(), emulator.accessoriesStorage.getMaxSize());
                            emulator.accessoriesStorage.notifyAll();
                        }
                        ++id[finalI];
                    }, presentValue[0], presentValue[0], TimeUnit.MILLISECONDS);
                    futureTasks.add(futureTask);
                }
            });
        } else {
            System.out.println("ALARM! ACCESSORIES SLIDER NOT FOUND!");
        }
    }

    @FXML
    public void getMachine(ScheduledExecutorService workersExecutor, EmulatorController emulatorController) {
        final int[] presentValue = {0};
        final List<ScheduledFuture<?>> futureTasks = new ArrayList<>();
        int providerID = (int) Thread.currentThread().getId();
        final int[] id = {providerID * 1000000};
        String name = "car";

        if (workersPerSecondSlider != null) {
            workersPerSecondSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
                presentValue[0] = newVal.intValue();

                Platform.runLater(() -> {
                    workersPerSecondLabel.setText(String.valueOf(presentValue[0]));
                });

                for (ScheduledFuture<?> futureTask : futureTasks) {
                    futureTask.cancel(true);
                }
                futureTasks.clear();

                int threadPoolSize = ((ThreadPoolExecutor) workersExecutor).getCorePoolSize();
                for (int i = 0; i < threadPoolSize; i++) {
                    final FactoryObject[] details = new FactoryObject[3];
                    ScheduledFuture<?> futureTask = workersExecutor.scheduleAtFixedRate(() -> {
                        synchronized (emulator.accessoriesStorage){
                            while(emulator.accessoriesStorage.isEmpty())
                            {
                                try {
                                    emulator.accessoriesStorage.wait();
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                }
                            }

                            details[0] = emulator.accessoriesStorage.getFactoryObject();

                            emulatorController.accessoriesStorageUpdate(emulator.accessoriesStorage.getSize(), emulator.accessoriesStorage.getMaxSize());
                            emulator.accessoriesStorage.notifyAll();
                        }
                        synchronized (emulator.bodyStorage){
                            while (emulator.bodyStorage.isEmpty()){
                                try {
                                    emulator.bodyStorage.wait();
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                }
                            }

                            details[1] = emulator.bodyStorage.getFactoryObject();
                            emulatorController.bodyStorageUpdate(emulator.bodyStorage.getSize(), emulator.bodyStorage.getMaxSize());
                            emulator.bodyStorage.notifyAll();
                        }
                        synchronized (emulator.engineStorage){
                            while (emulator.engineStorage.isEmpty()){
                                try {
                                    emulator.engineStorage.wait();
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                }
                            }

                            details[2] = emulator.engineStorage.getFactoryObject();
                            emulatorController.engineStorageUpdate(emulator.engineStorage.getSize(), emulator.engineStorage.getMaxSize());
                            emulator.engineStorage.notifyAll();
                        }

                        FactoryObject car = new FactoryObject(name, id[0], providerID);
                        car.pushDetail(details[2]);
                        car.pushDetail(details[1]);
                        car.pushDetail(details[0]);
                        synchronized (emulator.carStorage){
                            while(!emulator.carStorage.push(car)){
                                try {
                                    emulator.carStorage.wait();
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                }
                            }

                            emulatorController.carStorageUpdate(emulator.carStorage.getSize(), emulator.carStorage.getMaxSize());
                            emulator.carStorage.notifyAll();
                        }
                        ++id[0];
                    }, presentValue[0], presentValue[0], TimeUnit.MILLISECONDS);
                    futureTasks.add(futureTask);
                }
            });
        } else {
            System.out.println("ALARM! ACCESSORIES SLIDER NOT FOUND!");
        }
    }


    @FXML
    public void getDealers(ScheduledExecutorService dealersExecutor, EmulatorController emulatorController) {
        final int[] presentValue = {0};
        final List<ScheduledFuture<?>> futureTasks = new ArrayList<>();
        int providerID = (int) Thread.currentThread().threadId();
        final int[] id = {providerID * 1000000};

        if (dealersPerSecondSlider != null) {
            dealersPerSecondSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
                presentValue[0] = newVal.intValue();

                Platform.runLater(() -> {
                    dealersPerSecondLabel.setText(String.valueOf(presentValue[0]));
                });

                for (ScheduledFuture<?> futureTask : futureTasks) {
                    futureTask.cancel(true);
                }
                futureTasks.clear();

                int threadPoolSize = ((ThreadPoolExecutor) dealersExecutor).getCorePoolSize();
                for (int i = 0; i < threadPoolSize; i++) {
                    final FactoryObject[] car = new FactoryObject[1];
                    ScheduledFuture<?> futureTask = dealersExecutor.scheduleAtFixedRate(() -> {
                        synchronized (emulator.carStorage){
                            while (emulator.carStorage.isEmpty()){
                                try {
                                    emulator.carStorage.wait();
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                }
                            }

                            car[0] = emulator.carStorage.getFactoryObject();
                            car[0].printFactoryObjectInfo(providerID);
                            emulatorController.carStorageUpdate(emulator.carStorage.getSize(), emulator.carStorage.getMaxSize());
                            emulator.carStorage.notifyAll();
                        }
                        ++id[0];
                    }, presentValue[0], presentValue[0], TimeUnit.MILLISECONDS);
                    futureTasks.add(futureTask);
                }
            });
        } else {
            System.out.println("ALARM! ACCESSORIES SLIDER NOT FOUND!");
        }
    }



    @FXML
    public int getWorkersPerSecondValue() {
        final int[] presentValue = {0};
        if (workersPerSecondSlider != null) {
            workersPerSecondSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
                presentValue[0] = newVal.intValue();
                workersPerSecondLabel.setText(String.valueOf(presentValue[0]));
            });
        } else {
            // Обработка ситуации, когда слайдер не инициализирован
            return 0;
        }
        return presentValue[0];
    }

    public int getDealersPerSecondValue() {
        final int[] presentValue = {0};
        if (dealersPerSecondSlider != null) {
            dealersPerSecondSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
                presentValue[0] = newVal.intValue();
                dealersPerSecondLabel.setText(String.valueOf(presentValue[0]));
            });
        } else {
            // Обработка ситуации, когда слайдер не инициализирован
            return 0;
        }
        return presentValue[0];
    }

    public void engineStorageUpdate(int storageSize, int storageMaxSize) {
        Platform.runLater(() -> {
            engineStorage.setText(String.valueOf(storageSize) + "/" + String.valueOf(storageMaxSize));
        });
    }

    public void bodyStorageUpdate(int storageSize, int storageMaxSize) {
        Platform.runLater(() -> {
            bodyStorage.setText(String.valueOf(storageSize) + "/" + String.valueOf(storageMaxSize));
        });
    }

    public void accessoriesStorageUpdate(int storageSize, int storageMaxSize) {
        Platform.runLater(() -> {
            accessoriesStorage.setText(String.valueOf(storageSize) + "/" + String.valueOf(storageMaxSize));
        });
    }

    public void carStorageUpdate(int storageSize, int storageMaxSize) {
        Platform.runLater(() -> {
            carStorage.setText(String.valueOf(storageSize) + "/" + String.valueOf(storageMaxSize));
        });
    }

    @FXML
    public void initialize() {
        exitButton.setOnAction(e -> Platform.exit());
    }





}