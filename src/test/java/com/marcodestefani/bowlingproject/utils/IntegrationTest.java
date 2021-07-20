package com.marcodestefani.bowlingproject.utils;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;


@DataJpaTest
@Sql(value = "/deleteAllData.sql",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class IntegrationTest {
}
