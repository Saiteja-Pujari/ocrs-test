package d3e.core;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

@Component
public class TransactionWrapper {
	public static interface ToRun {
		void run() throws ServletException, IOException;
	}

	@Transactional
	public void doInTransaction(ToRun run) throws ServletException, IOException {
		run.run();
	}
}
