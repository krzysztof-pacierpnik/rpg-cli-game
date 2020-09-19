package com.perfectsoft.game.dao.serialize;

import com.perfectsoft.game.dao.Loadable;

public class SerializeLoadable<T> implements Loadable<T> {

    @Override
    public T load(String saveName) {
        return null;
    }

    @Override
    public void save(String saveName, T toSave) {

    }
}
