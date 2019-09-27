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
package org.activiti.engine.impl;

import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.GroupQuery;
import org.activiti.engine.identity.NativeGroupQuery;
import org.activiti.engine.identity.NativeUserQuery;
import org.activiti.engine.identity.Picture;
import org.activiti.engine.identity.User;
import org.activiti.engine.identity.UserQuery;
import org.activiti.engine.impl.cmd.CheckPassword;
import org.activiti.engine.impl.cmd.CreateGroupCmd;
import org.activiti.engine.impl.cmd.CreateGroupQueryCmd;
import org.activiti.engine.impl.cmd.CreateMembershipCmd;
import org.activiti.engine.impl.cmd.CreateUserCmd;
import org.activiti.engine.impl.cmd.CreateUserQueryCmd;
import org.activiti.engine.impl.cmd.DeleteGroupCmd;
import org.activiti.engine.impl.cmd.DeleteMembershipCmd;
import org.activiti.engine.impl.cmd.DeleteUserCmd;
import org.activiti.engine.impl.cmd.DeleteUserInfoCmd;
import org.activiti.engine.impl.cmd.GetUserInfoCmd;
import org.activiti.engine.impl.cmd.GetUserInfoKeysCmd;
import org.activiti.engine.impl.cmd.GetUserPictureCmd;
import org.activiti.engine.impl.cmd.SaveGroupCmd;
import org.activiti.engine.impl.cmd.SaveUserCmd;
import org.activiti.engine.impl.cmd.SetUserInfoCmd;
import org.activiti.engine.impl.cmd.SetUserPictureCmd;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.impl.persistence.entity.IdentityInfoEntity;

import java.util.List;

/**
 * @author Tom Baeyens
 */
public class IdentityServiceImpl extends ServiceImpl implements IdentityService {

    @Override
  public Group newGroup(String groupId) {
    return commandExecutor.execute(new CreateGroupCmd(groupId));
  }

  @Override
  public Group newGroup(String groupId,String systemId){
        return commandExecutor.execute(new CreateGroupCmd(groupId,systemId));
    }

    @Override
    public Group newGroup(String groupId, String systemId, String roleId) {
        return commandExecutor.execute(new CreateGroupCmd(groupId,systemId,roleId));
    }

    @Override
  public User newUser(String userId) {
    return commandExecutor.execute(new CreateUserCmd(userId));
  }

    @Override
  public void saveGroup(Group group) {
    commandExecutor.execute(new SaveGroupCmd(group));
  }

    @Override
  public void saveUser(User user) {
    commandExecutor.execute(new SaveUserCmd(user));
  }

    @Override
  public UserQuery createUserQuery() {
    return commandExecutor.execute(new CreateUserQueryCmd());
  }

  @Override
  public NativeUserQuery createNativeUserQuery() {
    return new NativeUserQueryImpl(commandExecutor);
  }

    @Override
  public GroupQuery createGroupQuery() {
    return commandExecutor.execute(new CreateGroupQueryCmd());
  }

  @Override
  public NativeGroupQuery createNativeGroupQuery() {
    return new NativeGroupQueryImpl(commandExecutor);
  }

    @Override
  public void createMembership(String userId, String groupId) {
    commandExecutor.execute(new CreateMembershipCmd(userId, groupId));
  }

    @Override
  public void deleteGroup(String groupId) {
    commandExecutor.execute(new DeleteGroupCmd(groupId));
  }

    @Override
    public void deleteGroupByGroupIdAndSystemId(String groupId, String systemId) {
        commandExecutor.execute(new DeleteGroupCmd(groupId,systemId));
    }

    @Override
    public void deleteMembership(String userId, String groupId) {
    commandExecutor.execute(new DeleteMembershipCmd(userId, groupId));
  }

    @Override
  public boolean checkPassword(String userId, String password) {
    return commandExecutor.execute(new CheckPassword(userId, password));
  }

    @Override
  public void deleteUser(String userId) {
    commandExecutor.execute(new DeleteUserCmd(userId));
  }

    @Override
  public void setUserPicture(String userId, Picture picture) {
    commandExecutor.execute(new SetUserPictureCmd(userId, picture));
  }

    @Override
  public Picture getUserPicture(String userId) {
    return commandExecutor.execute(new GetUserPictureCmd(userId));
  }

    @Override
  public void setAuthenticatedUserId(String authenticatedUserId) {
    Authentication.setAuthenticatedUserId(authenticatedUserId);
  }

    @Override
  public String getUserInfo(String userId, String key) {
    return commandExecutor.execute(new GetUserInfoCmd(userId, key));
  }

    @Override
  public List<String> getUserInfoKeys(String userId) {
    return commandExecutor.execute(new GetUserInfoKeysCmd(userId, IdentityInfoEntity.TYPE_USERINFO));
  }

    @Override
  public void setUserInfo(String userId, String key, String value) {
    commandExecutor.execute(new SetUserInfoCmd(userId, key, value));
  }

    @Override
  public void deleteUserInfo(String userId, String key) {
    commandExecutor.execute(new DeleteUserInfoCmd(userId, key));
  }
}
