package com.sequenceiq.lastfm.etl;

import java.io.IOException;
import java.io.OutputStream;

import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.serialization.EventSerializer;
import org.apache.flume.serialization.HeaderAndBodyTextEventSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomLastfmHeaderAndBodyTextEventSerializer implements EventSerializer {

    private static final Logger LOGGER = LoggerFactory.getLogger(HeaderAndBodyTextEventSerializer.class);

    // for legacy reasons, by default, append a newline to each event written out
    private static final String APPEND_NEWLINE = "appendNewline";
    private static final boolean APPEND_NEWLINE_DFLT = true;

    private final OutputStream out;
    private final boolean appendNewline;

    private CustomLastfmHeaderAndBodyTextEventSerializer(OutputStream out, Context ctx) {
        this.appendNewline = ctx.getBoolean(APPEND_NEWLINE, APPEND_NEWLINE_DFLT);
        this.out = out;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public boolean supportsReopen() {
        return true;
    }

    @Override
    public void afterCreate() {
        // noop
    }

    @Override
    public void afterReopen() {
        // noop
    }

    @Override
    public void beforeClose() {
        // noop
    }

    @Override
    public void write(Event e) throws IOException {
        out.write(e.getBody());
        if (appendNewline) {
            out.write('\n');
        }
    }

    @Override
    public void flush() throws IOException {
        // noop
    }

    public static class Builder implements EventSerializer.Builder {

        @Override
        public EventSerializer build(Context context, OutputStream out) {
            CustomLastfmHeaderAndBodyTextEventSerializer customLastfmHeaderAndBodyTextEventSerializer = new CustomLastfmHeaderAndBodyTextEventSerializer(out, context);
            return customLastfmHeaderAndBodyTextEventSerializer;
        }

    }
}
