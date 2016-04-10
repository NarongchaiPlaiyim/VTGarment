package com.vtgarment.beans;

import com.vtgarment.model.db.UserModel;
import com.vtgarment.service.LoginService;
import com.vtgarment.service.security.SimpleAuthenticationManager;
import com.vtgarment.service.security.UserDetail;
import com.vtgarment.utils.AttributeName;
import com.vtgarment.utils.FacesUtil;
import com.vtgarment.utils.MessageDialog;
import com.vtgarment.utils.Utils;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.session.CompositeSessionAuthenticationStrategy;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Getter
@Setter
@ViewScoped
@ManagedBean(name = "loginBean")
public class LoginBean extends Bean {
    private static final long serialVersionUID = 4112578634029374840L;
    @ManagedProperty("#{loginService}") private LoginService loginService;
    @ManagedProperty("#{sessionRegistry}") private SessionRegistry sessionRegistry;
    @ManagedProperty("#{sas}") private CompositeSessionAuthenticationStrategy compositeSessionAuthenticationStrategy;

    private String userName = "";
    private String password = "";
    private UserDetail userDetail;
    private Map map;

    private boolean flagLogin = Boolean.TRUE;

    @PostConstruct
    private void init(){
//        map = new HashMap();
//        if(!Utils.isNull(SecurityContextHolder.getContext().getAuthentication())){
//            userDetail = (UserDetail) SecurityContextHolder.getContext()
//                                                           .getAuthentication()
//                                                           .getPrincipal();
//            map = (Map) FacesUtil.getSession()
//                                 .getAttribute(AttributeName.AUTHORIZE.getName());
//        }
    }

    public String login(){
        log.info("-- SessionRegistry principle size: {}", sessionRegistry.getAllPrincipals().size());
        if(!Utils.isZero(userName.length()) &&
           !Utils.isZero(password.length())) {

            if(loginService.isUserExist(getUserName(), getPassword())){

                UserModel userModel = loginService.getUserModel();
                userDetail = new UserDetail(userModel.getId(), userModel.getCode(), userModel.getName(), userModel.getUsername(), userModel.getFactoryId(), loginService.getLineId(userModel.getId()));
                log.debug("-- User Detail : {}", userDetail.toString());
                HttpServletRequest httpServletRequest = FacesUtil.getRequest();
                HttpServletResponse httpServletResponse = FacesUtil.getResponse();
                UsernamePasswordAuthenticationToken request = new UsernamePasswordAuthenticationToken(getUserDetail(), getPassword());
                request.setDetails(new WebAuthenticationDetails(httpServletRequest));
                SimpleAuthenticationManager simpleAuthenticationManager = new SimpleAuthenticationManager();
                Authentication result = simpleAuthenticationManager.authenticate(request);
                log.debug("-- authentication result: {}", result.toString());
                SecurityContextHolder.getContext().setAuthentication(result);
                compositeSessionAuthenticationStrategy.onAuthentication(request, httpServletRequest, httpServletResponse);
                HttpSession httpSession = FacesUtil.getSession();
                httpSession.setAttribute(AttributeName.USER_DETAIL.getName(), getUserDetail());
//                httpSession.setAttribute(AttributeName.AUTHORIZE.getName(), loginService.getAuthorize());
                log.debug("-- userDetail[{}]", userDetail.toString());
                return "PASS";
            }
        }

        flagLogin = Boolean.FALSE;
        log.debug("----------------------------- {}", flagLogin);

        showDialog(MessageDialog.WARNING.getMessageHeader(), "Invalid username or password.");
        return "loggedOut";
    }

    public boolean isRendered(String key){
        try {
            return map.containsKey(key);
        } catch (Exception e) {
            log.error("Exception : {}", e);
            return false;
        }
    }
}
