package br.pro.turing.masiot.rmlclient;

import br.pro.turing.masiot.core.model.Data;
import br.pro.turing.masiot.core.model.Resource;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Logger;

public class ResourceBufferManager {

    private static final Logger LOGGER = Logger.getLogger(ResourceBufferManager.class.getName());

    private Resource resource;

    private ArrayList<Data> buffer;

    private RMLClient rmlClient;

    public ResourceBufferManager(Resource resource, RMLClient rmlClient) {
        this.resource = resource;
        this.rmlClient = rmlClient;
        this.buffer = (ArrayList<Data>) Collections.synchronizedList(
                new ArrayList<Data>(1000 / resource.getWaitTimeInMillis()));
        startReading();
    }

    private void startReading() {
        new Thread(() -> {
            while (true) {
                final long t1 = System.currentTimeMillis();

                this.buffer.add(
                        new Data(LocalDateTime.now(), this.resource.get_id(), this.rmlClient.getValue(this.resource)));

                final long duration = System.currentTimeMillis() - t1;
                try {
                    Thread.sleep(duration < this.resource.getWaitTimeInMillis() ?
                                 this.resource.getWaitTimeInMillis() - duration : 0);
                } catch (InterruptedException e) {
                    LOGGER.severe(e.getMessage());
                }
            }
        }).start();
    }

    /**
     * @return {@link #buffer}
     */
    public ArrayList<Data> getBuffer() {
        ArrayList<Data> temp = this.buffer;
        this.buffer = (ArrayList<Data>) Collections.synchronizedList(
                new ArrayList<Data>(1000 / resource.getWaitTimeInMillis()));
        return temp;
    }
}
