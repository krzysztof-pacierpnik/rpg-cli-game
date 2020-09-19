package com.perfectsoft.game.dao;

import java.io.IOException;

public interface Dao<T> {

    void save(T data, String saveName);

    T load(String saveName);

    T createFromTemplate(String templateName) throws IOException;

}
