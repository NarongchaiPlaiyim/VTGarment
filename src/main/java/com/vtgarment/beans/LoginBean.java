package com.vtgarment.beans;

import com.vtgarment.service.LoginService;
import com.vtgarment.service.security.UserDetail;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.authentication.session.CompositeSessionAuthenticationStrategy;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
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

    @PostConstruct
    private void init(){
        log.debug("[NEW] CODE MAP");
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
        return "PASS";
    }
//    public String login(){
//        log.info("-- SessionRegistry principle size: {}", sessionRegistry.getAllPrincipals().size());
//        if(!Utils.isZero(userName.length()) && !Utils.isZero(password.length())) {
//            setPassword(EncryptionService.encryption(password));
//            if(loginService.isUserExist(getUserName(), getPassword())){
////                StaffModel staffModel = loginService.getStaffModel();
////                userDetail = new UserDetail(staffModel.getUsername(),
////                                            staffModel.getPassword(),
////                                            "USER",
////                                            staffModel.getMsTitleModel().getName(),
////                                            staffModel.getName());
////                userDetail.setId(Utils.parseInt(staffModel.getId(), 0));
//                HttpServletRequest httpServletRequest = FacesUtil.getRequest();
//                HttpServletResponse httpServletResponse = FacesUtil.getResponse();
//                UsernamePasswordAuthenticationToken request = new UsernamePasswordAuthenticationToken(getUserDetail(), getPassword());
//                request.setDetails(new WebAuthenticationDetails(httpServletRequest));
//                SimpleAuthenticationManager simpleAuthenticationManager = new SimpleAuthenticationManager();
//                Authentication result = simpleAuthenticationManager.authenticate(request);
//                log.debug("-- authentication result: {}", result.toString());
//                SecurityContextHolder.getContext().setAuthentication(result);
//                compositeSessionAuthenticationStrategy.onAuthentication(request, httpServletRequest, httpServletResponse);
//                HttpSession httpSession = FacesUtil.getSession();
//                httpSession.setAttribute(AttributeName.USER_DETAIL.getName(), getUserDetail());
////                httpSession.setAttribute(AttributeName.AUTHORIZE.getName(), loginService.getAuthorize());
////                httpSession.setAttribute(AttributeName.STAFF.getName(), staffModel.getId());
//                log.debug("-- userDetail[{}]", userDetail.toString());
//                return "PASS";
//            }
//        }
//        showDialog(MessageDialog.WARNING.getMessageHeader(), "Invalid username or password.");
//        return "loggedOut";
//    }

    public boolean isRendered(String key){
        try {
            return map.containsKey(key);
        } catch (Exception e) {
            log.error("Exception : {}", e);
            return false;
        }
    }
}
