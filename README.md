<p align="center">
  <img src="https://www.corda.net/wp-content/uploads/2016/11/fg005_corda_b.png" alt="Corda" width="500">
</p>

# HospitalizeFlowException CorDapp
This CorDapp contains information and examples on how to utilize HospitalizeFlowException.
A video explaining the flows seen in this repository can be found here: https://www.youtube.com/watch?v=RDQ4Pdb0RLs&feature=youtu.be.

# Using this CorDapp
This CorDapp contains workflows in both java & kotlin that utilize HospitalizeFlowException.
1) FlowThatGetsHospitalized
    This flow will always throw a HospitalizeFlowException, sending this flow to the flow hospital.
2) FlowThatPerformsSuspendableOperation
    This flow works in tandum with the new Corda await() feature. It will throw an exception if the external resource (the Bitcoin README) is unavailable.
    If you'd like to force it to throw a flow exception you can test this by disconnecting your machine from the internet.

To deploy this CorDapp you can first run `./gradlew deployNodes` and then `./workflows-java/build/nodes/runnodes` or `./workflows-kotlin/build/nodes/runnodes`

# What is the flow hospital?
The flow hospital is a node service that manages flows that have encountered certain errors.
It will work to determine whether errored flows should be retried or if their errors should propagate.
In many cases, these flows are recoverable in certain scenarios.
Ex: If a node is missing a required CorDapp JAR, the flow will be send to the hospital.
If one installed the necessary CorDapp JAR and restarted the node, that node would be able pick up the flow were it left off.
Previously, when flows were sent to the flow hospital was managed entirely by the node itself.
Now in Corda 4.4, developers can choose to send their own flows to the hospital with HospitalizeFlowException.
For additional details on the flow hospital please see: https://docs.corda.net/docs/corda-os/4.4/node-flow-hospital.html

# What is HospitalizeFlowException?
HospitalizeFlowException is a special exception type that when thrown allows a developer to programmatically send a flow to the flow hospital.
This means that while the flow cannot complete successfully now, the flow could be retried at some point in the future and complete successfully.

# When to use HospitalizeFlowException?
A HospitalizeFlowException should only be thrown when an error is encountered that is deemed "recoverable".
The most relevant time to throw a HospitalizeFlowException is when trying to access an external service which may be down.
If that service is unavailable, a HospitalizeFlowException can be thrown and the flow will be sent to the flow hospital.
In the flow hospital it will wait until node restart at which point the flow will be retried from its last checkpoint.

Developers should put thought into whether HospitalizeFlowException makes sense in the context of their CorDapp.
If an external service returns a result that indicates we do not want to complete the flow, then a simple FlowException should be thrown instead to permanently terminate the flow.
However, if that external service itself is currently unavailable, then it may be the case that we want to throw a HospitalizeFlowException instead to ensure this flow will be able to finish at some point in the future.

# What to look out for when using HospitalizeFlowException
It's important to note that in order to retry a flow that has been hospitalized one must manually restart a Corda node.
If the node is not restarted, the flow will remain incomplete within the flow hospital. A developer may want to consider whether it is worthwhile to throw a HospitalizeFlowException as it's possible that it makes more sense to simply throw a FlowException and manually rerun the flow. This should be carefully considered and will depend on the overall use case and network.
