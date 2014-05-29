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
