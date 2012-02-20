Enterprise Architect Java API Readme

The files in this directory can be used to access Enterprise Architect's COM automation API from Java. To use this API, please observe the following set-up procedure.

1. Copy the file SSJavaCOM.dll into any location within the Windows PATH. For example, the windows\system32 directory. 

2. Copy the eaapi.jar file to a location in the Java CLASSPATH or where the Java class loader can find it at run time


Limitations and known issues:

1. You cannot currently use this API to write plug-ins for EA. It is only suitable for accessing the automation server API.

2. Due to the nature of Java interacting with native methods and COM, garbage collection is not be optimal. Native COM classes and memory allocated for these is not seen by the Java garbage collector, so you should explicitly invoke a garbage collection from time to time when working with many objects using the Java API. This will ensure native memory is freed in a timely fashion. 
