# Rowan-Memory-Buddy-System
##Authors
- John Bucknam
- Albert Rynkiewicz 
- Alexander Greco
- Aaron Rudolph

##Binary Buddy MMU Viewer
This project consists of 3 major parts, separated into 3 packages:
  1. **Binary Buddy Memory Management Unit**
    - Used to create a data structure based on [Binary Memory Allocation](https://en.wikipedia.org/wiki/Buddy_memory_allocation).
  2. **MMU Viewer**
    - Used to create a GUI view of the Binary Buddy MMU.
    - Has 2 modes, manual mode and automatic mode when being used by the AllocatorDeallocator.
  3. **AllocatorDeallocator**
    1. Consists of an Allocator, Deallocator, and a monitor.
    2. The **Allocator** is a thread that creates random processes to be allocated to the MMU.
      - If unsuccessful, then the random process couldn't fit into a memory chunk.
    3. The **Deallocator** is a thread that chooses to deallocate a random process from the MMU.
    4. The **monitor** holds the critical sections used by the Allocator and Deallocator to keep synchronization.

###Command Line Arguments

The command line has several arguments that can be used.

`java -jar BinaryBuddy.jar`

When used in default, the .jar file will create a MMU Viewer with an MMU size of 1024 bytes and 64 bytes and the viewer will be in manual mode.

`java -jar BinaryBuddy.jar a`

When the first argument is `a` or `auto`, the MMU Viewer will be set in auto mode. The MMU Viewer can be set to manual mode as well using `m` or `manual`.

`java -jar BinaryBuddy.jar <a\m> <MMUSize> <ChunkSize>`

The next 2 arguments are `MMUSize` and `ChunkSize`, which will set the MMU's maximum size and the size of the small chunk.
**Note: No argument needs <> and will in fact break if you try**

`java -jar BinaryBuddy.jar m <MMUSize> <ChunkSize> <VisualChunkSize>`

The `VisualChunkSize` argument will change the size of the Chunks that appear in the MMUViewer with default being 1.
**Note: The chunk sizes will expand to default if they are not large enough to cover the whole viewer, so the chunkSize you are viewing may not be the exact size**

`java -jar BinaryBuddy.jar a <MMUSize> <ChunkSize> <animTime> <VisualChunkSize>`

The `animTime` is the time(msec) between each allocation, deallocation, and possible sleep cycles in between during auto mode only. By default the animTime is 500.

###Manual Mode
Manual mode is where the user is given the capability to add any process as well as remove any process they added.
- To add a process, the **process name** must be unique and must have a **process size**.
- To remove a process, the **process name** must exist and **no process size** is needed.

##Auto Mode
Auto mode is where the program automatically allocates and deallocates randomly-created processes and the user is given no control other than to what arguments are given and to exit from the program.
