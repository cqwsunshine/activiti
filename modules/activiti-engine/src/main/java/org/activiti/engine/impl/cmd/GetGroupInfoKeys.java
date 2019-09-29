package org.activiti.engine.impl.cmd;

import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;

import java.io.Serializable;
import java.util.List;

public class GetGroupInfoKeys implements Command<List<String>>, Serializable {

    private static final long serialVersionUID = 1L;

    public GetGroupInfoKeys() {
    }

    @Override
    public List<String> execute(CommandContext commandContext) {
        return commandContext.getGroupEntityManager().findGroupInfoKeys();
    }
}
