/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.activiti.examples.runtime;

import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.test.PluggableActivitiTestCase;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.Deployment;
import org.junit.Ignore;

import java.util.HashMap;
import java.util.List;

/**
 * This test shows that bpmn endless loop with activiti6 is not only fiction
 */
@Ignore
public class StandardAgendaFailingTest extends PluggableActivitiTestCase {

    /*@Ignore("Endless loop with the standard agenda implementation can run 'forever'.")
    @Deployment(resources = "org/activiti/examples/runtime/WatchDogAgendaTest-endlessloop.bpmn20.xml")
    public void ignoreStandardAgendaWithEndLessLoop() {
        this.runtimeService.startProcessInstanceByKey("endlessloop");
    }*/

    /**
     * 开启工作流
     * 注意：流程定义和部署时act_re_procdef和act_re_deployment中未存入tenantId系统标识字段，需要排查
     */
    public void testStartFlowAndExecAndSaveRel(){
        // String processDefinitionKey, String businessKey, Map<String, Object> variables, String tenantId
        String processDefinitionKey = "credit_two_v1";
        String businessKey = "C2019092811160001";
        HashMap<String,Object> variables = new HashMap<>();
        String tenantId = "creditA";//systemId
        String userId = "2";
        ProcessInstance processInstance = this.runtimeService.startProcessInstanceByKeyAndTenantId(processDefinitionKey, businessKey, variables, tenantId);
        // 直线型流程
        Task task = this.taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
        // 签收
        this.taskService.claim(task.getId(),userId);
        //岗位意见
        this.taskService.addComment(task.getId(), processInstance.getId(), "1", "通过");
        //完成
        this.taskService.complete(task.getId());
        //数据封装
        HistoricTaskInstance dealHisTask = historyService.createHistoricTaskInstanceQuery().taskId(task.getId()).singleResult();
        List<Task> actTaskList = taskService.createTaskQuery().processInstanceId(processInstance.getId()).list();
        System.out.println();
    }

    /**
     * 执行流程
     */
    public void testTaskTruning(){
        String processInstanceId = "12501";// 在开启流程后需要保存
        String userId = "1";
        HashMap<String,Object> variableMap = new HashMap<>();
        Task task = this.taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
        // 判断是否结束
        if (task != null){
            String processInstanceId2 = taskService.createTaskQuery().taskId(task.getId()).singleResult().getProcessInstanceId();
            taskService.claim(task.getId(), userId);
            taskService.addComment(task.getId(), processInstanceId, "1", "通过");
            taskService.complete(task.getId(), variableMap);
            //数据封装
            HistoricTaskInstance dealHisTask = historyService.createHistoricTaskInstanceQuery().taskId(task.getId()).singleResult();
            List<Task> actTaskList = taskService.createTaskQuery().processInstanceId(processInstanceId).list();
            System.out.println();
        }
        System.out.println();
    }
}
