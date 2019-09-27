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

package org.activiti.engine.impl.persistence.entity;

import java.util.List;
import java.util.Map;

import org.activiti.engine.ActivitiObjectNotFoundException;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.Picture;
import org.activiti.engine.identity.User;
import org.activiti.engine.identity.UserQuery;
import org.activiti.engine.impl.Page;
import org.activiti.engine.impl.UserQueryImpl;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.persistence.entity.data.DataManager;
import org.activiti.engine.impl.persistence.entity.data.UserDataManager;

/**
 * @author Tom Baeyens
 * @author Saeid Mirzaei
 * @author Joram Barrez
 */
public class UserEntityManagerImpl extends AbstractEntityManager<UserEntity> implements UserEntityManager {
  
  protected UserDataManager userDataManager;
  
  public UserEntityManagerImpl(ProcessEngineConfigurationImpl processEngineConfiguration, UserDataManager userDataManager) {
    super(processEngineConfiguration);
    this.userDataManager = userDataManager;
  }
  
  @Override
  protected DataManager<UserEntity> getDataManager() {
    return userDataManager;
  }
  
  @Override
  public UserEntity findById(String entityId) {
    return userDataManager.findById(entityId);
  }

  @Override
  public User createNewUser(String id) {
    UserEntity userEntity = create();
    userEntity.setId(id);
    userEntity.setRevision(0); // needed as users can be transient
    return userEntity;
  }

    @Override
    public User createNewUser(String id, String userId, String systemId) {
        UserEntity userEntity = create();
        userEntity.setId(id);
        userEntity.setUserId(userId);
        userEntity.setSystemId(systemId);
        userEntity.setRevision(0); // needed as users can be transient
        return userEntity;
    }

    @Override
  public void updateUser(User updatedUser) {
    super.update((UserEntity) updatedUser);
  }

  @Override
  public void delete(UserEntity userEntity) {
    super.delete(userEntity);
    deletePicture(userEntity);
  }
  
  @Override
  public void deletePicture(User user) {
    UserEntity userEntity = (UserEntity) user;
    if (userEntity.getPictureByteArrayRef() != null) {
      userEntity.getPictureByteArrayRef().delete();
    }
  }

  @Override
  public void delete(String id) {
    UserEntity user = findById(id);
    if (user != null) {
      List<IdentityInfoEntity> identityInfos = getIdentityInfoEntityManager().findIdentityInfoByUserId(id);
      for (IdentityInfoEntity identityInfo : identityInfos) {
        getIdentityInfoEntityManager().delete(identityInfo);
      }
      getMembershipEntityManager().deleteMembershipByUserId(id);
      delete(user);
    }
  }

  @Override
  public List<User> findUserByQueryCriteria(UserQueryImpl query, Page page) {
    return userDataManager.findUserByQueryCriteria(query, page);
  }

  @Override
  public long findUserCountByQueryCriteria(UserQueryImpl query) {
    return userDataManager.findUserCountByQueryCriteria(query);
  }

  @Override
  public List<Group> findGroupsByUser(String userId) {
    return userDataManager.findGroupsByUser(userId);
  }

  @Override
  public UserQuery createNewUserQuery() {
    return new UserQueryImpl(getCommandExecutor());
  }

  @Override
  public Boolean checkPassword(String userId, String password) {
    User user = null;
    
    if (userId != null) {
      user = findById(userId);
    }
    
    if ((user != null) && (password != null) && (password.equals(user.getPassword()))) {
      return true;
    }
    return false;
  }

  @Override
  public List<User> findUsersByNativeQuery(Map<String, Object> parameterMap, int firstResult, int maxResults) {
    return userDataManager.findUsersByNativeQuery(parameterMap, firstResult, maxResults);
  }

  @Override
  public long findUserCountByNativeQuery(Map<String, Object> parameterMap) {
    return userDataManager.findUserCountByNativeQuery(parameterMap);
  }

  @Override
  public boolean isNewUser(User user) {
    return ((UserEntity) user).getRevision() == 0;
  }

  @Override
  public Picture getUserPicture(String userId) {
    UserEntity user = findById(userId);
    return user.getPicture();
  }

  @Override
  public void setUserPicture(String userId, Picture picture) {
    UserEntity user = findById(userId);
    if (user == null) {
      throw new ActivitiObjectNotFoundException("user " + userId + " doesn't exist", User.class);
    }

    user.setPicture(picture);
  }

  public UserDataManager getUserDataManager() {
    return userDataManager;
  }

  public void setUserDataManager(UserDataManager userDataManager) {
    this.userDataManager = userDataManager;
  }
  
}
