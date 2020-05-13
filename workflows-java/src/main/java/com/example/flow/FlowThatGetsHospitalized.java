package com.example.flow;

import co.paralleluniverse.fibers.Suspendable;
import net.corda.core.flows.*;
import net.corda.core.utilities.ProgressTracker;

@InitiatingFlow
@StartableByRPC
public class FlowThatGetsHospitalized extends FlowLogic<Void> {
    private final ProgressTracker progressTracker = new ProgressTracker();

    @Override
    public ProgressTracker getProgressTracker() {
        return progressTracker;
    }

    @Suspendable
    @Override
    public Void call() {
        throw new HospitalizeFlowException("Ouch!");
    }
}

