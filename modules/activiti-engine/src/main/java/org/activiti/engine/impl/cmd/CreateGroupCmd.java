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

package org.activiti.engine.impl.cmd;

import java.io.Serializable;

import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.identity.Group;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;

/**
 * @author Tom Baeyens
 */
public class CreateGroupCmd implements Command<Group>, Serializable {

  private static final long serialVersionUID = 1L;

  protected String groupId;
  protected String systemId;
  protected String roleId;

  public CreateGroupCmd(String groupId) {
    if (groupId == null) {
      throw new ActivitiIllegalArgumentException("groupId is null");
    }
    this.groupId = groupId;
  }

  public CreateGroupCmd(String groupId, String systemId){
      if (groupId == null) {
          throw new ActivitiIllegalArgumentException("groupId is null");
      }
      if (systemId == null){
          throw new ActivitiIllegalArgumentException("groupId is null");
      }
      this.groupId = groupId;
      this.systemId = systemId;
  }

    public CreateGroupCmd(String groupId, String systemId, String roleId){
        if (groupId == null) {
            throw new ActivitiIllegalArgumentException("groupId is null");
        }
        if (systemId == null){
            throw new ActivitiIllegalArgumentException("groupId is null");
        }
        if (roleId == null){
            throw new ActivitiIllegalArgumentException("roleId is null");
        }
        this.groupId = groupId;
        this.systemId = systemId;
        this.roleId = roleId;
    }

  @Override
  public Group execute(CommandContext commandContext) {
      if (groupId != null && systemId != null && roleId != null){
          return commandContext.getGroupEntityManager().createNewGroup(groupId,systemId,roleId);
      }else {
          return commandContext.getGroupEntityManager().createNewGroup(groupId);
      }
  }

}
