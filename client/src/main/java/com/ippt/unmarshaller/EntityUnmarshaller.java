package com.ippt.unmarshaller;

import java.util.List;

public interface EntityUnmarshaller<E> {
    E unmarshallEntity(String responseEntry);

    List<E> unmarshall(String response);
}
