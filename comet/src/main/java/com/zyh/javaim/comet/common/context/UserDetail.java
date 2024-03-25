package com.zyh.javaim.comet.common.context;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserDetail {
    User user;
}
