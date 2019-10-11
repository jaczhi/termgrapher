# termgrapher

Basic terminal mathematical grapher written entirely in Java.

https://i.imgur.com/sFLSbCj.png

## Features (0.1.0 beta)

* Run without GUI / windowserver
* Light on memory
* Find zeroes, see table of values
* Trace your graph
* Find the derivative and integral
* Quickly save your graph and reopen once you need it

## Dependencies:

* ```groovy-3.0.0-beta-3```
* ```lanterna-3.0.1```

You will only need to download these libraries if you are compiling from source. The .jar includes both.

## Usage

Download from the "releases" tab, and run:

```java -jar /path/to/termgrapher.jar```

You may be able to double-click the .jar to run (OS and file-manager dependent).

## Savefiles

Termgrapher uses a proprietary file extension .tgs; however, it is a human-readable UTF-8 text file. If you know what you are doing, it is possible to manually edit a savefile. Unless in the same directory, and absolute path must be provided to the "save" and "open" dialog boxes. 
