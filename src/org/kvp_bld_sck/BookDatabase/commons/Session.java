package org.kvp_bld_sck.BookDatabase.commons;

public abstract class Session<R> implements AutoCloseable {

    protected R resource;

    public Session(R resource) {
        this.resource = resource;
    }

    public R getResource() {
        return resource;
    }

    @Override
    public abstract void close() throws Exception;
}
