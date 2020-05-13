package com.example.flow

import co.paralleluniverse.fibers.Suspendable
import net.corda.core.flows.FlowLogic
import net.corda.core.flows.HospitalizeFlowException
import net.corda.core.flows.InitiatingFlow
import net.corda.core.flows.StartableByRPC
import net.corda.core.utilities.ProgressTracker

@InitiatingFlow
@StartableByRPC
class FlowThatGetsHospitalized : FlowLogic<Void?>() {
    override val progressTracker = ProgressTracker()

    @Suspendable
    override fun call(): Void {
        throw HospitalizeFlowException("Ouch!")
    }
}