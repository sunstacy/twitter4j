package gh.polyu.za;

import org.kohsuke.args4j.*;

public class CmdOption {
 
 @Option(name="-fromID", usage="Specify directory")
 public int fromID;
 
 @Option(name="-toID", usage="Specify data file")
 public int toID ;
 
 @Option(name="-program", usage="Specify program no")
 public int program ;
 
 
 }

