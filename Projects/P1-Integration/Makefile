runApplication:
	javac -cp .:../junit5.jar *.java
	java App

runAllTests:
	javac -cp .:../junit5.jar *.java
	java -cp .:../junit5.jar org.junit.platform.console.ConsoleLauncher --scan-classpath

clean:
	rm *.class
