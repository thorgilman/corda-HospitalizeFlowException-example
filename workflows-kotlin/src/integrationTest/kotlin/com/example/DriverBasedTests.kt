package com.example

import com.example.flow.FlowThatPerformsSuspendableOperation
import net.corda.core.identity.CordaX500Name
import net.corda.core.utilities.getOrThrow
import net.corda.testing.core.TestIdentity
import net.corda.testing.driver.DriverParameters
import net.corda.testing.driver.driver
import org.junit.Test
import kotlin.test.assertEquals

class DriverBasedTests {
    val partyA = TestIdentity(CordaX500Name("PartyA", "", "GB"))

    /*
    This test simply runs FlowThatPerformsSuspendableOperation.
    To test the new HospitalizeFlowException you can disconnect your machine from the internet before running this test.
    You will see a HospitalizeFlowException be thrown and the flow move to the flow hospital.
     */
    @Test
    fun testFlowThatPerformsSuspendableOperation() {
        driver(DriverParameters(isDebug = true, startNodesInProcess = true)) {
            val partyAHandle = startNode(providedName = partyA.name).get()
            val flowHandle = partyAHandle.rpc.startFlowDynamic(FlowThatPerformsSuspendableOperation::class.java)
            val bitcoinReadmeString = flowHandle.returnValue.get()
            assert(bitcoinReadmeString.isNotEmpty())
        }
    }
}
