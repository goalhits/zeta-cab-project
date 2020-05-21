After a late night stand-up comedy at Shephard Audi Bangalore, all the Males and Females are
tryig to leave the venue and booking their cabs via SafeCabs at the same time. However, to
make sure all the males and females are safe, the software developers at SafeCabs came up 
with an algorithm whereby either a SafeCab can have all Females or all Males or two Females
and two Males. All other combinations may lead to an unsafe passenger.

Your task as the SafeCabs developer is to solve this problem with proper modularization and
making it thread safe at the same time. Once an accetable combination of riders is possible,
ride should start

- Register Cab Requestors
- Allow only Registered Cab requestors to request and board the cab.
- Consider the all the ride requestors as threads when requesting a cab.
- The Cab which is available first should move first.
- Cab Driver will not start ride until it has all 4 passengers
- All the cab requests should wait until a new cab appears.
- Consider Safe cabs is only operational near Shephard Audi Bangalore, so cab distance 
  can be ignored. All the availble cabs can be used to fulfill the cab requests.
- Write Unit Test Cases for as many critical sections of your code as possible.

You can use below concepts -
- Barrier
- Lock
- Semaphore
- Queues