FLAGS=-classpath ../bin/ -d ../bin/
COMMAND=javac
PACKAGE=binarybuddysystem

ALL: Process MMU Driver

Process:
	$(COMMAND) $(FLAGS) $(PACKAGE)/Process.java
MMU:
	$(COMMAND) $(FLAGS) $(PACKAGE)/MMU.java
Driver:
	$(COMMAND) $(FLAGS) $(PACKAGE)/Driver.java