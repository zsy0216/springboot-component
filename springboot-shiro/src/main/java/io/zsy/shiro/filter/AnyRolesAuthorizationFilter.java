package io.zsy.shiro.filter;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.util.Set;

/**
 * 自定义角色过滤器 roles
 * <p>
 * Shiro 默认角色过滤器 RolesAuthorizationFilter 要求所有角色都要满足才通过；
 * <p>
 * 为了能够使设置的角色满足任意一个就可通过鉴权，所以自定义该过滤器。
 * <p>
 * 自定义过滤器可模仿该类，定义满足实际业务需求的过滤器
 *
 * @author: zhangshuaiyin
 * @date: 2021/8/4 20:23
 */
public class AnyRolesAuthorizationFilter extends AuthorizationFilter {
    /**
     *
     * @param request 请求
     * @param response 响应
     * @param mappedValue 过滤器链中定义的角色  filterMap.put("/user/**", "roles[admin, root]");
     * @return 是否通过鉴权
     * @throws Exception e
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {

        Subject subject = this.getSubject(request, response);
        String[] rolesArray = (String[]) mappedValue;

        if (rolesArray == null || rolesArray.length == 0) {
            // no roles specified, so nothing to check - allow access.
            // 没有指定权限，通过鉴权
            return true;
        }

        Set<String> roles = CollectionUtils.asSet(rolesArray);
        // 循环roles判断只要有角色则返回true
        for (String role : roles) {
            if(subject.hasRole(role)){
                return true;
            }
        }
        return false;
    }
}
