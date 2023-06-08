# DeadlockDetection
The simulator receives the names and instructions of tasks from the standard input input (see below), and then simulates the allocation of resources by running them. The output of the program is the data of the avoided dead spots (in chronological order).  

The simulation is performed in steps. At each step, each task executes one instruction (as long as it is not waiting for a resource). The instruction can be a resource request, release, and "other" operation. Within a step, the instructions are executed according to the order of the tasks defined in the input. If a task has no more operations to perform in a step, it ends and its resources remaining in the reserved state are freed in the order they were reserved.  

The simulator builds a resource allocation graph based on the operations of the tasks, and uses it to check whether it leads to a deadlock at each reservation. If so, the reservation will be rejected. The reservation operation rejected in this way is not repeated by the task later, nor is it blocked because of it; in the next step it will execute its next statement. If a task frees a resource it has not reserved (e.g. due to a previously refused reservation), then nothing happens (as if it had "0" in its input).  

Tasks can occupy single-copy resources during the task, so the deadlock can be detected by analyzing the resource allocation graph. Before serving each booking request, it must be checked whether that one step causes a deadlock (i.e. whether a circle forms in the graph after the step). A task's resource request can result in three types of output: the resource is free and there is no deadlock (normal return); the resource is free, but a deadlock would occur (the simulator rejects the request); and the resource is busy (the simulator puts the task in a waiting state).  

When releasing a resource, the simulator chooses among the tasks waiting for it according to the FIFO principle, i.e. the task that has been waiting the longest gets the resource, and it will execute its next instruction the next time (when the simulator runs this task).  

## Input (standard input, stdin)
Each line contains the name of a task and its step-by-step instructions as follows:  

Names of tasks T1, T2, T3, ... (a character string without spaces)  
R1, R2, R3, ... resource names (a string without spaces)  
"+R1": resource reservation request, "-R1": resource release instruction, "0" other operation  
e.g.: interpretation of the sequence "T1,+R1,0,0,+R2,-R1,-R2": the task T1 wants to reserve R1 first, then performs another activity in two steps, and then issues a reservation request to R2- re, then releases R1 and finally R2.  

A complete input example:  
T1,+R1,0,0,+R2,-R1,-R2  
T2,+R2,+R1,-R1,-R2  
T3,0,0,0,+R3,+R3,-R3,-R3  
At the end of the input, there can be a line feed or an empty line.  

## Output (standard output, stdout)  
In the output, the details of one avoided deadlock per line must be entered in the following format:  
task name (which would cause the deadlock), how many operations of the task were rejected by the simulator, resource name (the reservation of which would cause a deadlock). For example:  

T1,4,R2  
T3,5,R3  
Interpretation of the above example: the simulator detected a deadlock at two instructions: during the 4th instruction of T1 (Request for reservation of resource R2) and the 5th instruction of T3 (request for reservation of R3).  

## Test data  
Before the first submission, it is worth trying the following tests (input - output pairs).  

T1,+R1,+R1  
T1,2,R1  
Explanation: T1 requests another copy from R1 in step 2, but only one copy  
