package org.example.meine_process_app;

import org.apache.ibatis.logging.LogFactory;
import org.camunda.bpm.BpmPlatform;
import org.camunda.bpm.ProcessEngineService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.impl.util.LogUtil;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.model.bpmn.instance.Task;
import org.camunda.bpm.engine.test.Deployment;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.*;
import static org.junit.Assert.*;

import java.util.logging.Logger;

/**
 * Test case starting an in-memory database-backed Process Engine.
 */
public class InMemoryH2Test {

  @Rule
  public ProcessEngineRule rule = new ProcessEngineRule();

  private static final String PROCESS_DEFINITION_KEY = "meine-process-app";

  private final Logger LOGGER = Logger.getLogger(InMemoryH2Test.class.getName());

  
  // enable more detailed logging
  static {
//    LogUtil.readJavaUtilLoggingConfigFromClasspath(); // process engine
//    LogFactory.useJdkLogging(); // MyBatis
  }

  @Before
  public void setup() {
    init(rule.getProcessEngine());
  }

  /**
   * Just tests if the process definition is deployable.
   */
  @Test
  @Deployment(resources = "process.bpmn")
  public void testParsingAndDeployment() {

		RuntimeService meinRuntimeService = rule.getRuntimeService();
		ProcessInstance instance = meinRuntimeService.startProcessInstanceByKey(PROCESS_DEFINITION_KEY);  
		
		TaskService taskService = rule.getTaskService();
	
	    org.camunda.bpm.engine.task.Task task = taskService.createTaskQuery().initializeFormKeys().singleResult();
	    assertEquals("User Workflow", task.getName());

	    taskService.complete(task.getId());
	    
	    LOGGER.info("Ausgabe des Loggers: " + task.getFormKey());
  }
  
  @Test
  public void testComplete() {
	  RuntimeService meinRuntimeService = rule.getRuntimeService();
	  assertEquals(0, meinRuntimeService.createProcessInstanceQuery().count());
}


}
