package com.xuan.vueblog.util;

import com.xuan.vueblog.shiro.AccountProfile;
import org.apache.shiro.SecurityUtils;

/**
 * @author yuan
 * @date 3/8/22
 */
public class ShiroUtils {

    public static AccountProfile getProfile(){
        return (AccountProfile) SecurityUtils.getSubject().getPrincipal();
    }
}
