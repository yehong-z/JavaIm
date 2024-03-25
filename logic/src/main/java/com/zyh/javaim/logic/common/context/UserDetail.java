package com.zyh.javaim.logic.common.context;

import com.zyh.javaim.logic.dao.entity.User;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserDetail {
    User user;
}
