# Affinity Core


Affinity core is a 2 word name. "Affinity" because of what it does and "Core" because it will be at the core of all the amazing things one day!! 

> Affinity core process a input CSV file comprising of lines where each line has 
> ENTITY,TERM,WEIGHT 
> This input is then processed by Affinity Core to spit out various files as output. Of those
> files, "AFFINITY_CALCULATION.txt" and "TERM_BOND_CALCULATION.txt" provides plausible and meaningful insights of the input file data. 
>




#### AFFINITY_CALCULATION.txt


This file has the list of all the TERMS present in the input file, along with the list of all the ENTITIES sorted with respect to their affinity score towards each TERM. Higher affinity score denotes deeper plausible relationship between an ENTITY and a TERM.



#### TERM_BOND_CALCULATION.txt


This file has the list of all TERMS present in the input file, along with the list of all the TERMS in unsorted order with their bonding score. Higher bonding score denotes deeper plausible relationship between those two TERMS in the context of the data present in the input file.



### Fat JAR
##### target/affinity-core-jar-with-dependencies.jar

`
`
### Usage
>
> String inputFileLocation = "path-to-file";
> String sessionName = "MySession";
> String sessionId = "123";
> // create a session
> Session session = new Session(sessionName, sessionId);
> // start the process by providing the valid path to your correctly formatted input file
> session.startProcess("Y:\\New Folder\\input.txt");


