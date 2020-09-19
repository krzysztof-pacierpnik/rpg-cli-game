package com.perfectsoft.game.dao;

import java.io.Serializable;

public interface Loadable<T> extends Serializable {

    T load(String saveName);

    void save(String saveName, T toSave);
}
