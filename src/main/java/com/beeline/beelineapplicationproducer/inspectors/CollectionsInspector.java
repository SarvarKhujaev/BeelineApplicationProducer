package com.beeline.beelineapplicationproducer.inspectors;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class CollectionsInspector {
    protected CollectionsInspector() {}

    protected <T> boolean isCollectionNotEmpty ( final Collection<T> collection ) {
        return collection != null && !collection.isEmpty();
    }
}
