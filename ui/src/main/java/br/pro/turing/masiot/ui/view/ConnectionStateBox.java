package br.pro.turing.masiot.ui.view;

import br.pro.turing.masiot.core.model.ConnectionState;
import br.pro.turing.masiot.core.model.Device;
import br.pro.turing.masiot.core.service.ServiceManager;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class ConnectionStateBox extends Circle {

    private Device device;

    private SimpleBooleanProperty connectionState = new SimpleBooleanProperty(false);

    public ConnectionStateBox(Device device) {
        this.device = device;

        this.setRadius(5);
        this.setState(ConnectionState.OFFLINE);
        this.startTask();
    }

    private void startTask() {
        new Thread(() -> {
            while (true) {
                long t1 = System.currentTimeMillis();

                this.device = ServiceManager.getInstance().deviceService.findById(this.device.getDeviceName());
                final ConnectionState state = ConnectionState.get(this.device.getConnectionState());
                if (state != null) {
                    this.connectionState.set(state.equals(ConnectionState.ONLINE));
                    Platform.runLater(() -> {
                        ConnectionStateBox.this.setState(state);
                    });
                }

                long duration = System.currentTimeMillis() - t1;
                try {
                    Thread.sleep(duration < 5000 ? 5000 - duration : 0);
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

    public SimpleBooleanProperty connectionStateProperty() {
        return connectionState;
    }
}
