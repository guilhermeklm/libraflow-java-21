package gkaram.libraflow;

import org.springframework.boot.SpringApplication;

public class TestLibraflowApplication {

	public static void main(String[] args) {
		SpringApplication.from(LibraflowApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
