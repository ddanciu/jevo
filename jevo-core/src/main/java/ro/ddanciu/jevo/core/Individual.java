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
            return new Individual<D>(UUID.randomUUID(), data);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (! (obj instanceof Individual<?>)) {
            return false;
        }
        final Individual<?> other = (Individual<?>) obj;
        return id == other.id;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

	@Override
	public String toString() {
		return "Individual[" + data + "]";
	}
    
      


}
