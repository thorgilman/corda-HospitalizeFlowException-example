package com.example.test;

import com.example.flow.FlowThatGetsHospitalized;
import com.example.flow.FlowThatPerformsSuspendableOperation;
import com.google.common.collect.ImmutableList;
import net.corda.core.concurrent.CordaFuture;
import net.corda.core.flows.HospitalizeFlowException;
import net.corda.core.identity.CordaX500Name;
import net.corda.core.transactions.SignedTransaction;
import net.corda.testing.node.MockNetwork;
import net.corda.testing.node.MockNetworkParameters;
import net.corda.testing.node.StartedMockNode;
import net.corda.testing.node.TestCordapp;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ExecutionException;

public class UnitTesting {

    private MockNetwork network;
    private StartedMockNode node;

    @Before
    public void setup() {
        network = new MockNetwork(new MockNetworkParameters().withCordappsForAllNodes(ImmutableList.of(
                TestCordapp.findCordapp("com.example.flow"))));
        node = network.createPartyNode(new CordaX500Name("Node", "New York", "US"));
        network.runNetwork();
    }

//    @Test//(expected = HospitalizeFlowException.class)
//    public void test() throws ExecutionException, InterruptedException {
//        try {
//            CordaFuture<String> future = node.startFlow(new FlowThatPerformsSuspendableOperation());
//            future.get();
//        }
//        catch (Exception e) {
//            node.stop();
//        }
//
//    }


}
