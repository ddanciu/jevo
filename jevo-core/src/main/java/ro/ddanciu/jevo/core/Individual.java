/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ro.ddanciu.jevo.core;

import java.util.UUID;

/**
 *
 * @author dan
 */
public final class Individual<T> {

    private final UUID id;

    private T data;

    private Individual(UUID id, T data) {
        this.id = id;
        this.data = data;
    }

    public UUID getId() {
        return id;
    }

    public T getData() {
        return data;
    }

    public static  final class Factory {
        public static <D> Individual<D> newInstance(D data) {
            return new Individual(UUID.randomUUID(), data);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Individual<T> other = (Individual<T>) obj;
        return id == other.id;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }


}
