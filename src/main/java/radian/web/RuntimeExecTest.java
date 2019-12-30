package radian.web;

import java.io.*;

public class RuntimeExecTest {

	public static void main(String[] args) {
		Runtime rt = Runtime.getRuntime();

		/*
		This corresponds to what you type in DOS prompt
		add another String like "C:\\Test\\Test.txt" as
		a parameter to the program you want to start.
		In this case you want to start notepad - with no
		arguments.
		*/
		//String[] callAndArgs = { "Notepad.exe","C:\\Test.txt" };

		// We can start another JVM using the following arguments
		//String[] callAndArgs = { "cmd", "/c start java Test" };
		String[] callAndArgs = { "cmd", "/c start telnet" };

		try {

			/*
			Call the runtime environment and ask it to execute the
			contents in callAndArgs.
			*/
			Process child = rt.exec(callAndArgs);
			child.waitFor();
			/*
			Return an error code.
			If it is 0 - the command was executed properly
			If it is 1 or more an error occured.
			*/
			System.out.println("Process exit code is:"
								+ child.exitValue());
		}
		catch (IOException e) {
			System.err.println("IOException starting process!");
		}
		catch (InterruptedException e) {
			System.err.println("Interrupted waiting for process");
		}
	}
}
