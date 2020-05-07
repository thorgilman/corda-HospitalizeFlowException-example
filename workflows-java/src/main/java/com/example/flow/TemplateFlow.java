package com.example.flow;

import co.paralleluniverse.fibers.Suspendable;
import net.corda.core.flows.*;
import net.corda.core.utilities.ProgressTracker;

public class TemplateFlow {

    @InitiatingFlow
    @StartableByRPC
    public class Initiator extends FlowLogic<Void> {

        private final ProgressTracker progressTracker = new ProgressTracker();

        @Override
        public ProgressTracker getProgressTracker() {
            return progressTracker;
        }

        @Suspendable
        @Override
        public Void call() throws FlowException {
            // Initiator flow logic goes here.

            return null;
        }
    }

    @InitiatedBy(Initiator.class)
    public class Responder extends FlowLogic<Void> {
        private FlowSession counterpartySession;

        public Responder(FlowSession counterpartySession) {
            this.counterpartySession = counterpartySession;
        }

        @Suspendable
        @Override
        public Void call() throws FlowException {
            // Responder flow logic goes here.

            return null;
        }
    }
}

