JFLAGS = -cp lib/weka.jar:bin -d bin -sourcepath src
JAVAC = javac

.PHONY: directories
.SUFFIXES: .java .class

.java.class:
	$(JAVAC) $(JFLAGS) $*.java

CLASSES = src/EmotivCSV2Arff.java

all: directories classes

directories: 
	mkdir -p bin

classes: $(CLASSES:.java=.class)

clean:
	$(RM) -r bin

