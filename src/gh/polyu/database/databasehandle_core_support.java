package gh.polyu.database;

import java.util.Properties;

public interface databasehandle_core_support {

	int initial_databasehandle(Properties prop);
	int database_connection();
	void close_databasehandle();
}
