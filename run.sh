#!/bin/sh

if [ ! -d "bin" ];
then
	mkdir bin
fi

javac -d bin -sourcepath src src/talo/DynamicTalo.java src/talo/bots/*
jar cfm talo.jar MANIFEST.mf -C bin/ .
java -jar talo.jar -classpath bin/talo/bots
