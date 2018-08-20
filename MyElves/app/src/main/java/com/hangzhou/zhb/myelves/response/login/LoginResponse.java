package com.hangzhou.zhb.myelves.response.login;

import com.hangzhou.zhb.myelves.response.BaseResponse;

/**
 * Created by Unborn on 2018/3/5.
 *
 */

public class LoginResponse extends BaseResponse {
    public Login content;

    public class Login {
        public String deptID;
        public String deptName;
        public String deptType;
        public String groupID;
        public String groupName;
        public String photo;
        public String phone;
        public String realName;
        public String sessionKey;
        public int type;
        public String typeName;
        public int role;
        public String userName;
    }
}
