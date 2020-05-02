package br.pro.turing.masiot.ui.view;

import br.pro.turing.masiot.core.model.ConnectionState;
import br.pro.turing.masiot.core.model.Device;
import br.pro.turing.masiot.core.service.ServiceManager;
import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class ConnectionStateBox extends Circle {

    private Device device;

    public ConnectionStateBox(Device device) {
        this.device = device;

        this.setRadius(5);
        this.setState(ConnectionState.ONLINE);
        this.startTask();
    }

    private void startTask() {
        new Thread(() -> {
            while (true) {
                long t1 = System.currentTimeMillis();

                ConnectionState currentConnectionState = ServiceManager.getInstance().deviceService
                        .findCurrentConnectionState(this.device);
                Platform.runLater(() -> ConnectionStateBox.this.setState(currentConnectionState));

                long duration = System.currentTimeMillis() - t1;
                try {
                    Thread.sleep(duration < 1000 ? 1000 - duration : 0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void setState(ConnectionState state) {
        if (state.equals(ConnectionState.ONLINE)) {
            this.setFill(Color.GREEN);
        } else if (state.equals(ConnectionState.OFFLINE)) {
            this.setFill(Color.RED);
        }
    }
}
