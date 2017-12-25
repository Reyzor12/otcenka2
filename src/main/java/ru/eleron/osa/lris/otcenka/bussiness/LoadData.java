package ru.eleron.osa.lris.otcenka.bussiness;

import javafx.concurrent.Task;

public class LoadData extends Task<String> {
    @Override
    protected String call() throws Exception {
        for(float i = 0; i < 1; i += 0.01){
            this.updateProgress(i,1);
            Thread.sleep(100);
        }
        return null;
    }
}
