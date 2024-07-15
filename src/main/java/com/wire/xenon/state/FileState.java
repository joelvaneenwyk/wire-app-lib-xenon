package com.wire.xenon.state;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wire.xenon.backend.models.NewBot;
import com.wire.xenon.exceptions.MissingStateException;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class FileState implements State {

    private static final String STATE_FILENAME = "state.json";
    private final String path;
    private final UUID botId;

    public FileState(String path, UUID botId) {
        this.path = path;
        this.botId = botId;
        File dir = new File(String.format("%s/%s", path, botId));
        dir.mkdirs();
    }

    @Override
    public boolean saveState(NewBot newBot) throws IOException {
        File file = getStateFile();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(file, newBot);
        return true;
    }

    @Override
    public NewBot getState() throws IOException {
        File file = getStateFile();
        if (!file.exists()) throw new MissingStateException(botId);

        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(file, NewBot.class);
    }

    @Override
    public boolean removeState() {
        File file = getStateFile();
        return file.delete();
    }

    public boolean hasState() {
        File stateFile = getStateFile();
        return stateFile.exists();
    }

    public boolean hasFile(String filename) {
        File stateFile = getFile(filename);
        return stateFile.exists();
    }

    private File getStateFile() {
        return getFile(STATE_FILENAME);
    }

    private File getFile(String filename) {
        return new File(String.format("%s/%s/%s", path, botId, filename));
    }
}
