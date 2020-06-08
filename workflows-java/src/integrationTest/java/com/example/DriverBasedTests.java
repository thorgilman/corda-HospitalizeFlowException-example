package com.example;

import com.example.flow.FlowThatPerformsSuspendableOperation;
import com.google.common.collect.ImmutableList;
import net.corda.core.concurrent.CordaFuture;
import net.corda.core.flows.FlowLogic;
import net.corda.core.flows.HospitalizeFlowException;
import net.corda.core.identity.CordaX500Name;
import net.corda.core.messaging.FlowHandle;
import net.corda.testing.core.TestIdentity;
import net.corda.testing.driver.DriverParameters;
import net.corda.testing.driver.NodeHandle;
import net.corda.testing.driver.NodeParameters;
import org.junit.Test;
import org.junit.jupiter.api.parallel.Execution;

import java.util.List;
import java.util.concurrent.ExecutionException;

import static net.corda.testing.driver.Driver.driver;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class DriverBasedTests {
    private final TestIdentity partyA = new TestIdentity(new CordaX500Name("PartyA", "", "GB"));

    /*
    This test simply runs FlowThatPerformsSuspendableOperation.
    To test the new HospitalizeFlowException you can disconnect your machine from the internet before running this test.
    You will see a HospitalizeFlowException be thrown and the flow move to the flow hospital.
     */
    @Test
    public void testFlowThatPerformsSuspendableOperation() {
        driver(new DriverParameters().withIsDebug(true).withStartNodesInProcess(true), dsl -> {
            List<CordaFuture<NodeHandle>> handleFutures = ImmutableList.of(dsl.startNode(new NodeParameters().withProvidedName(partyA.getName())));
            try {
                NodeHandle partyAHandle = handleFutures.get(0).get();
                FlowHandle<String> flowHandle = partyAHandle.getRpc().startFlowDynamic(FlowThatPerformsSuspendableOperation.class);
                String bitcoinReadmeString = flowHandle.getReturnValue().get();
                assert (!bitcoinReadmeString.isEmpty());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        });
    }
}
