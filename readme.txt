How to run graph labeler with labelg.exe
1. Build labelg.exe: If you are on windows machine, would require cygwin with
gcc. Go to src\main\nauty\nauty25r9 and run '.\configure' and
then 'make all'. On success, labelg.exe will be present under the same folder.
2. Make sure network-motif java project is build successfully. To ensure this,
check the class files are present under target\classes and
target\test-classes.
3. Copy labelg.exe (created in step 1) to a working directory (say current
folder) and junit-4.10.jar (maven project should have pulled it on disk) as well to the working directory.
4. There are two tests for graph labelling. They can be run (both should pass) as follows:
	java -cp '.\target\classes;.\target\test-classes;.\junit-4.10.jar' org.junit.runner.JUnitCore edu.uw.nemo.labeler.GraphFormatTest
	java -cp '.\target\classes;.\target\test-classes;.\junit-4.10.jar' org.junit.runner.JUnitCore edu.uw.nemo.labeler.GraphLabelTest

Overall build:
1. Ensure that java 7 (jdk 1.7) or higher is installed on target machine.
2. Install apache maven from http://maven.apache.org/
3. Go to project root directory. on most systems this should be 'network-motif' folder pulled from google code.
4. Run 'mvn -U clean install' (without the single quotes).
5. This will pull in dependencies as configured in pom, compile, run tests and genrated deployable jar.
6. On success, nemo-1.0-SNAPSHOT.jar will be available under target folder.
7. Test output is also available surefire folder under target.

Running end to end functionality:
1. Fire up your favorite IDE (eclispe / netbeans / idea / ...)
2. Load nemo project.
3. Load and run NemoControllerTest.

This test does following:
1. Loads a sample data file included in nemo.
2. Pulls out the mappings.
3. Generates motifs using ESU algorithm.
4. Generates canonical labelling for motifs (pending integration).
5. Generates standard concentrations per canonical label (pending integration).

Converting PPI files to standard input format:
1. Nemo uses a simple csv file input. The file should contain two strings (from vertex, to vertex) separated by a tab per line.
2. DIPConverterTest can be used for converting a PPI file to this format. A working example is included in the test.

