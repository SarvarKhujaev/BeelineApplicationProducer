package com.beeline.beelineapplicationproducer.inspectors;


import java.util.Date;

public class TimeInspector extends CollectionsInspector {
    public Date newDate () {
        return new Date();
    }
}
